package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.ObservationResponse;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.controller.dto.PatientResponse;
import iu.devinmehringer.project3.manager.PatientManager;
import iu.devinmehringer.project3.model.observation.CategoryObservation;
import iu.devinmehringer.project3.model.observation.Measurement;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientManager patientManager;

    private static class ObservationMapper {
        public static ObservationResponse toDTO(Observation observation) {
            String type;
            String phenomenonTypeName;
            String value;
            String unit;
            String phenomenonName;

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
            } else {
                throw new IllegalArgumentException("Unknown observation type");
            }

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
                    observation.getStatus().toString()
            );
        }
    }

    private static class PatientMapper {
        public static PatientResponse toDTO(Patient patient) {
            return new PatientResponse(patient.getId(),
                    patient.getName(), patient.getDob(), patient.getNote());
        }
    }

    public PatientController(PatientManager patientManager) {
        this.patientManager = patientManager;
    }

    @PostMapping
    public ResponseEntity<Void> createPatient(@RequestBody PatientRequest request) {
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
    public ResponseEntity<List<String>> evaluate(@PathVariable Long id) {
        List<Phenomenon> inferences = patientManager.evaluate(id);
        return ResponseEntity.ok(
                inferences.stream()
                        .map(Phenomenon::getName)
                        .collect(Collectors.toList())
        );
    }

}