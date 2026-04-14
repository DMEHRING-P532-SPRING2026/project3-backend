package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.PatientRepository;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientAccess {
    private final PatientRepository patientRepository;

    public PatientAccess(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }
    
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

}
