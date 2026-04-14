package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.ProtocolAccess;
import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Protocol;

public class CreateProtocolCommand implements Command {
    private final ProtocolRequest request;
    private final ProtocolAccess protocolAccess;

    public CreateProtocolCommand(ProtocolRequest request, ProtocolAccess protocolAccess) {
        this.request = request;
        this.protocolAccess = protocolAccess;
    }

    @Override
    public void execute() {
        protocolAccess.save(new Protocol(request.getName(), request.getDescription(), request.getAccuracyRating()));
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE_PROTOCOL;
    }

    @Override
    public Object getPayload() {
        return request;
    }
}
