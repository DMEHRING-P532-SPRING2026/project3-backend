package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.PhenomenonAccess;
import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.PhenomenonRequest;
import iu.devinmehringer.project3.controller.exception.PhenomenonNotFoundException;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.PhenomenonType;

public class CreatePhenomenonCommand implements Command {
    private final PhenomenonRequest request;
    private final PhenomenonAccess phenomenonAccess;
    private final PhenomenonTypeAccess phenomenonTypeAccess;

    public CreatePhenomenonCommand(PhenomenonRequest request,
                                   PhenomenonAccess phenomenonAccess,
                                   PhenomenonTypeAccess phenomenonTypeAccess) {
        this.request = request;
        this.phenomenonAccess = phenomenonAccess;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
    }

    @Override
    public void execute() {
        PhenomenonType phenomenonType = phenomenonTypeAccess.getById(request.getPhenomenonTypeId())
                .orElseThrow(() -> new PhenomenonTypeNotFoundException("PhenomenonType not found: " + request.getPhenomenonTypeId()));

        Phenomenon parentConcept = null;
        if (request.getParentConceptId() != null) {
            parentConcept = phenomenonAccess.getById(request.getParentConceptId())
                    .orElseThrow(() -> new PhenomenonNotFoundException("Parent concept not found: " + request.getParentConceptId()));
        }

        Phenomenon phenomenon = new Phenomenon(request.getName(), phenomenonType);
        phenomenon.setParentConcept(parentConcept);
        phenomenonAccess.save(phenomenon);
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for CREATE_PHENOMENON");
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE_PHENOMENON;
    }

    @Override
    public Object getPayload() {
        return request;
    }
}