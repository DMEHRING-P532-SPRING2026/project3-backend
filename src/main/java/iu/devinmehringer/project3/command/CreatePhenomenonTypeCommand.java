package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeRequest;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Kind;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.PhenomenonType;

import java.util.ArrayList;

public class CreatePhenomenonTypeCommand implements Command {
    private final PhenomenonTypeRequest request;
    private final PhenomenonTypeAccess phenomenonTypeAccess;

    public CreatePhenomenonTypeCommand(PhenomenonTypeRequest request, PhenomenonTypeAccess phenomenonTypeAccess) {
        this.request = request;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
    }

    @Override
    public void execute() {
        PhenomenonType phenomenonType;
        if (request.getKind().equals(Kind.QUANTITATIVE)) {
            phenomenonType = new PhenomenonType(
                    request.getName(), request.getKind(), request.getAllowedUnits(), null
            );
        } else {
            phenomenonType = new PhenomenonType(
                    request.getName(), request.getKind(), null, new ArrayList<>()
            );
            for (String phenomenonName : request.getPhenomena()) {
                phenomenonType.addPhenomenon(new Phenomenon(phenomenonName, phenomenonType));
            }
        }
        phenomenonTypeAccess.save(phenomenonType);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE_PHENOMENON_TYPE;
    }

    @Override
    public Object getPayload() {
        return request;
    }
}
