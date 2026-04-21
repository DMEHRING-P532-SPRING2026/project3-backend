package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.InferenceResponse;
import iu.devinmehringer.project3.controller.dto.ObservationResponse;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.controller.dto.PatientResponse;
import iu.devinmehringer.project3.manager.PatientManager;
import iu.devinmehringer.project3.manager.UserManager;
import iu.devinmehringer.project3.model.observation.CategoryObservation;
import iu.devinmehringer.project3.model.observation.Measurement;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.patient.Patient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController extends BaseController {

    private final PatientManager patientManager;

    private static class ObservationMapper {
        public static ObservationResponse toDTO(Observation observation) {
            String type = null;
            String phenomenonTypeName = null;
            String value = null;
            String unit = null;
            String phenomenonName = null;

            if (observation instanceof Measurement m) {
                type = "measurement";
                phenomenonTypeName = m.getPhenomenonType().getName();
                value = m.getAmount().toString();
                unit = m.getUnit();
                phenomenonName = null;
            } else if (observation instanceof CategoryObservation c) {
                type = "category";
                phenomenonTypeName = c.getPhenomenon().getPhenomenonType().getName();
                value = c.getPresence().toString();
                unit = null;
                phenomenonName = c.getPhenomenon().getName();
            }

            String source = observation instanceof CategoryObservation c
                    ? c.getSource().toString()
                    : null;

            return new ObservationResponse(
                    observation.getId(),
                    type,
                    phenomenonTypeName,
                    value,
                    unit,
                    phenomenonName,
                    observation.getRecordedAt(),
                    observation.getApplicableAt(),
                    observation.getProtocol() != null ? observation.getProtocol().getName() : null,
                    observation.getStatus().toString(),
                    observation.getPerformedBy() != null ? observation.getPerformedBy().getUsername() : null,
                    observation.getFlag() != null ? observation.getFlag().toString() : null,
                    observation.getRejectionReason(),
                    observation.getRejectedBy() != null ? observation.getRejectedBy().getId() : null,
                    source
            );
        }
    }

    private static class PatientMapper {
        public static PatientResponse toDTO(Patient patient) {
            return new PatientResponse(patient.getId(),
                    patient.getName(), patient.getDob(), patient.getNote());
        }
    }

    public PatientController(PatientManager patientManager, UserManager userManager) {
        super(userManager);
        this.patientManager = patientManager;
    }

    @PostMapping
    public ResponseEntity<Void> createPatient(@RequestBody PatientRequest request,
                                              HttpServletRequest httpRequest) {
        stampUser(request, httpRequest);
        patientManager.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getPatients() {
        return ResponseEntity.ok(patientManager.getPatients()
                .stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}/observations")
    public ResponseEntity<List<ObservationResponse>> getObservations(@PathVariable Long id) {
        return ResponseEntity.ok(
                patientManager.getObservations(id)
                        .stream()
                        .map(ObservationMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/{id}/evaluate")
    public ResponseEntity<List<InferenceResponse>> evaluate(@PathVariable Long id) {
        return ResponseEntity.ok(patientManager.evaluate(id));
    }
}