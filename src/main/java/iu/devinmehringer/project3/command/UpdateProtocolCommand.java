package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.ProtocolAccess;
import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.exception.ProtocolNotFoundException;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Protocol;

import java.util.Map;

public class UpdateProtocolCommand implements Command {
    private final Long id;
    private final ProtocolRequest request;
    private final ProtocolAccess protocolAccess;

    public UpdateProtocolCommand(Long id, ProtocolRequest request, ProtocolAccess protocolAccess) {
        this.id = id;
        this.request = request;
        this.protocolAccess = protocolAccess;
    }

    @Override
    public void execute() {
        Protocol existing = protocolAccess.getById(id)
                .orElseThrow(() -> new ProtocolNotFoundException("Id not found: " +  id));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setAccuracyRating(request.getAccuracyRating());

        protocolAccess.save(existing);
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for UPDATE_PROTOCOL");
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UPDATE_PROTOCOL;
    }

    @Override
    public Object getPayload() {
        Protocol existing = protocolAccess.getById(id)
                .orElseThrow(() -> new ProtocolNotFoundException("Id not found: " +  id));

        return Map.of(
                "id", id,
                "oldName", existing.getName(),
                "oldDescription", existing.getDescription(),
                "oldAccuracyRating", existing.getAccuracyRating(),
                "newName", request.getName(),
                "newDescription", request.getDescription(),
                "newAccuracyRating", request.getAccuracyRating()
        );
    }
}
