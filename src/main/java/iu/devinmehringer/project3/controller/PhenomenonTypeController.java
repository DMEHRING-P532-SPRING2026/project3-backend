package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.PhenomenonResponse;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeRequest;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeResponse;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.UserManager;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phenomenon-types")
public class PhenomenonTypeController extends BaseController {

    private final ObservationManager observationManager;

    private static class PhenomenonTypeMapper {
        public static PhenomenonTypeResponse toDTO(PhenomenonType phenomenonType) {
            return new PhenomenonTypeResponse(
                    phenomenonType.getId(),
                    phenomenonType.getName(),
                    phenomenonType.getKind(),
                    phenomenonType.getAllowedUnits(),
                    phenomenonType.getPhenomena()
                            .stream()
                            .map(PhenomenonTypeMapper::toPhenomenonDTO)
                            .collect(Collectors.toList())
            );
        }

        public static PhenomenonResponse toPhenomenonDTO(Phenomenon phenomenon) {
            return new PhenomenonResponse(
                    phenomenon.getId(),
                    phenomenon.getName(),
                    phenomenon.getPhenomenonType().getId()
            );
        }
    }

    public PhenomenonTypeController(ObservationManager observationManager, UserManager userManager) {
        super(userManager);
        this.observationManager = observationManager;
    }

    @PostMapping
    public ResponseEntity<Void> createPhenomenonType(@RequestBody PhenomenonTypeRequest request,
                                                     HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        observationManager.createPhenomenonType(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PhenomenonTypeResponse>> getPhenomenonTypes() {
        return ResponseEntity.ok(observationManager.getPhenomenonTypes()
                .stream()
                .map(PhenomenonTypeMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePhenomenonType(@PathVariable Long id,
                                                     @RequestBody PhenomenonTypeRequest request,
                                                     HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        observationManager.updatePhenomenonType(id, request);
        return ResponseEntity.ok().build();
    }
}