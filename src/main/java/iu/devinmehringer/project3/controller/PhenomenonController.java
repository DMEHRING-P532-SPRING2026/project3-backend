package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.PhenomenonRequest;
import iu.devinmehringer.project3.controller.dto.PhenomenonResponse;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phenomena")
public class PhenomenonController extends BaseController {

    private final ObservationManager observationManager;

    public PhenomenonController(ObservationManager observationManager, UserManager userManager) {
        super(userManager);
        this.observationManager = observationManager;
    }

    @PostMapping
    public ResponseEntity<Void> createPhenomenon(@RequestBody PhenomenonRequest request,
                                                 HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        observationManager.createPhenomenon(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PhenomenonResponse>> getPhenomena() {
        return ResponseEntity.ok(observationManager.getPhenomena());
    }
}