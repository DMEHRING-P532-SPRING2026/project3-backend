package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.ProtocolAccess;
import iu.devinmehringer.project3.controller.exception.ProtocolNotFoundException;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Protocol;

import java.util.Map;

public class DeleteProtocolCommand implements Command {
    private final Long id;
    private final ProtocolAccess protocolAccess;

    public DeleteProtocolCommand(Long id, ProtocolAccess protocolAccess) {
        this.id = id;
        this.protocolAccess = protocolAccess;
    }

    @Override
    public void execute() {
        Protocol existing = protocolAccess.getById(id)
                .orElseThrow(() -> new ProtocolNotFoundException("Id not found: " +  id));
        protocolAccess.delete(existing);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE_PROTOCOL;
    }

    @Override
    public Object getPayload() {
        Protocol existing = protocolAccess.getById(id)
                .orElseThrow(() -> new ProtocolNotFoundException("Id not found: " +  id));

        return Map.of(
                "id", id,
                "name", existing.getName(),
                "description", existing.getDescription(),
                "accuracyRating", existing.getAccuracyRating()
        );
    }
}
