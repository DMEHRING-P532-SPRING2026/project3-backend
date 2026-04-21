package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.CategoryObservationRequest;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.RejectObservationRequest;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/observations")
public class ObservationController extends BaseController {

    private final ObservationManager observationManager;

    public ObservationController(ObservationManager observationManager, UserManager userManager) {
        super(userManager);
        this.observationManager = observationManager;
    }

    @PostMapping("/measurement")
    public ResponseEntity<Void> createMeasurementObservation(@RequestBody MeasurementRequest measurementRequest,
                                                             HttpServletRequest httpRequest) {
        stampUser(measurementRequest, httpRequest);
        this.observationManager.createObservation(measurementRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/category")
    public ResponseEntity<Void> createCategoryObservation(@RequestBody CategoryObservationRequest categoryObservationRequest,
                                                          HttpServletRequest httpRequest) {
        stampUser(categoryObservationRequest, httpRequest);
        this.observationManager.createObservation(categoryObservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectObservation(@PathVariable Long id,
                                                  @RequestBody RejectObservationRequest request,
                                                  HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        observationManager.rejectObservation(id, request);
        return ResponseEntity.ok().build();
    }
}