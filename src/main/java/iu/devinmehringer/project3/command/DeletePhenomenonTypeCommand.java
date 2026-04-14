package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.PhenomenonType;

import java.util.Map;
import java.util.stream.Collectors;

public class DeletePhenomenonTypeCommand implements Command {
    private final Long id;
    private final PhenomenonTypeAccess phenomenonTypeAccess;

    public DeletePhenomenonTypeCommand(Long id, PhenomenonTypeAccess phenomenonTypeAccess) {
        this.id = id;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
    }

    @Override
    public void execute() {
        PhenomenonType existing = phenomenonTypeAccess.getById(id)
                .orElseThrow(() -> new PhenomenonTypeNotFoundException("Id not found:" + id));
        phenomenonTypeAccess.delete(existing);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE_PHENOMENON_TYPE;
    }

    @Override
    public Object getPayload() {
        PhenomenonType existing = phenomenonTypeAccess.getById(id)
                .orElseThrow(() -> new PhenomenonTypeNotFoundException("Id not found:" + id));

        return Map.of(
                "id", id,
                "name", existing.getName(),
                "kind", existing.getKind(),
                "allowedUnits", existing.getAllowedUnits(),
                "phenomena", existing.getPhenomena().stream()
                        .map(Phenomenon::getName)
                        .collect(Collectors.toList())
        );
    }
}
