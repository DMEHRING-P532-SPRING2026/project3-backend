package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.dto.ProtocolResponse;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.model.observation.Protocol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/protocols")
public class ProtocolController {
    private final ObservationManager observationManager;

    private static class ProtocolMapper {
        public static ProtocolResponse toDTO(Protocol protocol) {
            return new ProtocolResponse(
                    protocol.getId(),
                    protocol.getName(),
                    protocol.getDescription(),
                    protocol.getAccuracyRating()
            );
        }
    }

    public ProtocolController(ObservationManager observationManager) {
        this.observationManager = observationManager;
    }

    @PostMapping
    public ResponseEntity<Void> createProtocol(@RequestBody ProtocolRequest request) {
        observationManager.createProtocol(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProtocolResponse>> getProtocols() {
        return ResponseEntity.ok(observationManager.getProtocols()
                .stream()
                .map(ProtocolMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProtocol(@PathVariable Long id, @RequestBody ProtocolRequest request) {
        observationManager.updateProtocol(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProtocol(@PathVariable Long id) {
        observationManager.deleteProtocol(id);
        return ResponseEntity.ok().build();
    }
}
