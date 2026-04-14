package iu.devinmehringer.project3.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import iu.devinmehringer.project3.access.CommandLogAccess;
import iu.devinmehringer.project3.command.Command;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommandRunner {
    private final ObjectMapper objectMapper;
    private final CommandLogAccess commandLogAccess;


    public CommandRunner(ObjectMapper objectMapper, CommandLogAccess commandLogAccess) {
        this.objectMapper = objectMapper;
        this.commandLogAccess = commandLogAccess;
    }

    @Transactional
    public void execute(Command command) {
        Object payload = command.getPayload();
        command.execute();

        try {
            CommandLogEntry entry = new CommandLogEntry();
            entry.setCommandType(command.getCommandType());
            entry.setPayload(objectMapper.writeValueAsString(payload));
            entry.setExecutedAt(LocalDateTime.now());
            entry.setUser("staff");
            commandLogAccess.save(entry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize command payload", e);
        }
    }
}
