package iu.devinmehringer.project3.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import iu.devinmehringer.project3.access.CommandLogAccess;
import iu.devinmehringer.project3.access.ObservationAccess;
import iu.devinmehringer.project3.command.Command;
import iu.devinmehringer.project3.command.CreateObservationCommand;
import iu.devinmehringer.project3.command.RejectObservationCommand;
import iu.devinmehringer.project3.controller.exception.CommandLogNotFoundException;
import iu.devinmehringer.project3.controller.exception.InvalidOperationException;
import iu.devinmehringer.project3.controller.exception.UnauthorizedException;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import iu.devinmehringer.project3.model.log.CommandType;
import com.fasterxml.jackson.databind.ObjectMapper;
import iu.devinmehringer.project3.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommandRunner {
    private final ObjectMapper objectMapper;
    private final CommandLogAccess commandLogAccess;
    private final ObservationAccess observationAccess;

    public CommandRunner(ObjectMapper objectMapper,
                         CommandLogAccess commandLogAccess,
                         ObservationAccess observationAccess) {
        this.objectMapper = objectMapper;
        this.commandLogAccess = commandLogAccess;
        this.observationAccess = observationAccess;
    }

    @Transactional
    public void execute(Command command, User user) {
        if (user == null) {
            throw new UnauthorizedException("No user present on request");
        }

        Object payload = command.getPayload();
        command.execute();

        try {
            CommandLogEntry entry = new CommandLogEntry();
            entry.setCommandType(command.getCommandType());
            entry.setPayload(objectMapper.writeValueAsString(payload));
            entry.setExecutedAt(LocalDateTime.now());
            entry.setUser(user);

            if (command instanceof CreateObservationCommand c && c.getObservation() != null) {
                entry.setObservationId(c.getObservation().getId());
            } else if (command instanceof RejectObservationCommand c && c.getObservation() != null) {
                entry.setObservationId(c.getObservation().getId());
            }

            commandLogAccess.save(entry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize command payload", e);
        }
    }

    @Transactional
    public void undo(Long commandLogId, User requestingUser) {
        CommandLogEntry entry = commandLogAccess.findById(commandLogId)
                .orElseThrow(() -> new CommandLogNotFoundException("Command log entry not found: " + commandLogId));

        if (entry.isUndone()) {
            throw new InvalidOperationException("Command has already been undone");
        }
        if (!entry.getUser().getId().equals(requestingUser.getId())) {
            throw new UnauthorizedException("Only the original user may undo this command");
        }

        Command command = reconstructCommand(entry);
        command.undo();

        entry.setUndone(true);
        commandLogAccess.save(entry);
    }

    private Command reconstructCommand(CommandLogEntry entry) {
        return switch (entry.getCommandType()) {
            case CREATE_OBSERVATION -> new CreateObservationCommand(
                    observationAccess, entry.getObservationId()
            );
            case REJECT_OBSERVATION -> new RejectObservationCommand(
                    observationAccess, entry.getObservationId()
            );
            case CREATE_PATIENT,
                 CREATE_PHENOMENON_TYPE, UPDATE_PHENOMENON_TYPE,
                 CREATE_PROTOCOL, UPDATE_PROTOCOL,
                 CREATE_PHENOMENON ->
                    throw new UnsupportedOperationException(
                            "Undo not supported for: " + entry.getCommandType()
                    );
        };
    }
}