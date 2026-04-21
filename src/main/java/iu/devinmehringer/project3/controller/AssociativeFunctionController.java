package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.AssociativeFunctionRequest;
import iu.devinmehringer.project3.controller.dto.AssociativeFunctionResponse;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/associative-function")
public class AssociativeFunctionController {
    public final ObservationManager observationManager;

    private static class AssociativeFunctionMapper {
        public static AssociativeFunctionResponse toDTO(AssociativeFunction function) {
            return new AssociativeFunctionResponse(
                    function.getId(),
                    function.getName(),
                    function.getArgumentConcepts().stream()
                            .map(PhenomenonType::getName)
                            .collect(Collectors.toList()),
                    function.getProductConcept().getName(),
                    function.getDiagnosisStrategyType()
            );
        }
    }

    public AssociativeFunctionController(ObservationManager observationManager) {
        this.observationManager = observationManager;
    }

    @GetMapping
    public ResponseEntity<List<AssociativeFunctionResponse>> getAssociativeFunctions() {
        return ResponseEntity.ok(observationManager.getAssociativeFunctions()
                .stream()
                .map(AssociativeFunctionMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/strategy")
    public ResponseEntity<Void> setStrategyAssociativeFunction(@PathVariable Long id, @RequestBody AssociativeFunctionRequest request) {
        observationManager.setStrategyAssociativeFunction(id, request);
        return ResponseEntity.ok().build();
    }
}
