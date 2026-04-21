package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.dto.ProtocolResponse;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.UserManager;
import iu.devinmehringer.project3.model.observation.Protocol;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/protocols")
public class ProtocolController extends BaseController {

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

    public ProtocolController(ObservationManager observationManager, UserManager userManager) {
        super(userManager);
        this.observationManager = observationManager;
    }

    @PostMapping
    public ResponseEntity<Void> createProtocol(@RequestBody ProtocolRequest request,
                                               HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
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
    public ResponseEntity<Void> updateProtocol(@PathVariable Long id,
                                               @RequestBody ProtocolRequest request,
                                               HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        observationManager.updateProtocol(id, request);
        return ResponseEntity.ok().build();
    }
}