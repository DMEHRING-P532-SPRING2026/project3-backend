package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeRequest;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.PhenomenonType;

import java.util.Map;
import java.util.stream.Collectors;

public class UpdatePhenomenonTypeCommand implements Command {
    private final PhenomenonTypeRequest request;
    private final PhenomenonTypeAccess phenomenonTypeAccess;
    private final Long id;

    public UpdatePhenomenonTypeCommand(Long id, PhenomenonTypeRequest request, PhenomenonTypeAccess phenomenonTypeAccess) {
        this.id = id;
        this.request = request;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
    }

    @Override
    public void execute() {
        PhenomenonType existing = phenomenonTypeAccess.getById(id)
                .orElseThrow(() -> new PhenomenonTypeNotFoundException("Id not found: " + id));

        existing.setName(request.getName());
        existing.setKind(request.getKind());
        existing.setAllowedUnits(request.getAllowedUnits());

        existing.getPhenomena().clear();
        for (String name : request.getPhenomena()) {
            existing.addPhenomenon(new Phenomenon(name, existing));
        }

        phenomenonTypeAccess.save(existing);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UPDATE_PHENOMENON_TYPE;
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for UPDATE_PHENOMENON_TYPE");
    }

    @Override
    public Object getPayload() {
        PhenomenonType existing = phenomenonTypeAccess.getById(id)
                .orElseThrow(() -> new PhenomenonTypeNotFoundException("Id not found: " + id));

        return Map.of(
                "id", id,
                "oldName", existing.getName(),
                "oldKind", existing.getKind(),
                "oldAllowedUnits", existing.getAllowedUnits(),
                "oldPhenomena", existing.getPhenomena().stream()
                        .map(Phenomenon::getName)
                        .collect(Collectors.toList()),
                "newName", request.getName(),
                "newKind", request.getKind(),
                "newAllowedUnits", request.getAllowedUnits(),
                "newPhenomena", request.getPhenomena()
        );
    }
}
