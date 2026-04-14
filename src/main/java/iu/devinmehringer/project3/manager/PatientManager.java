package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.AssociativeFunctionAccess;
import iu.devinmehringer.project3.access.PatientAccess;
import iu.devinmehringer.project3.command.CreatePatientCommand;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreatePatientRequestException;
import iu.devinmehringer.project3.controller.exception.PatientNotFoundException;
import iu.devinmehringer.project3.engine.DiagnosisEngine;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientManager {
    private final PatientAccess patientAccess;
    private final CommandRunner commandRunner;
    private final DiagnosisEngine diagnosisEngine;
    private final AssociativeFunctionAccess associativeFunctionAccess;

    public PatientManager(PatientAccess patientAccess,
                          CommandRunner commandRunner,
                          DiagnosisEngine diagnosisEngine,
                          AssociativeFunctionAccess associativeFunctionAccess) {
        this.patientAccess = patientAccess;
        this.commandRunner = commandRunner;
        this.diagnosisEngine = diagnosisEngine;
        this.associativeFunctionAccess = associativeFunctionAccess;
    }

    public void createPatient(PatientRequest request) {
        validate(request);
        commandRunner.execute(new CreatePatientCommand(request, patientAccess));
    }

    public List<Patient> getPatients() {
        return patientAccess.getPatients();
    }

    private void validate(PatientRequest request) {
        if (request.getFullName() == null || request.getDateOfBirth() == null) {
            throw new InvalidCreatePatientRequestException("Request missing name or dob");
        }
    }

    public List<Observation> getObservations(Long patientId) {
        Patient patient = patientAccess.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Id not found: " + patientId));
            return patient.getObservations()
                    .stream()
                    .sorted(Comparator.comparing(Observation::getRecordedAt).reversed())
                    .collect(Collectors.toList());
    }

    public List<Phenomenon> evaluate(Long patientId) {
        Patient patient = patientAccess.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Id not found: " + patientId));

        return diagnosisEngine.evaluate(
                associativeFunctionAccess.getAll(),
                patient.getObservations()
        );
    }
}
