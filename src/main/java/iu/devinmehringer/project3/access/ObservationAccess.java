package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.CategoryObservationRepository;
import iu.devinmehringer.project3.access.repository.ObservationRepository;
import iu.devinmehringer.project3.model.observation.CategoryObservation;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.Status;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationAccess {
    private final ObservationRepository observationRepository;
    private final CategoryObservationRepository categoryObservationRepository;

    public ObservationAccess(ObservationRepository observationRepository,
                             CategoryObservationRepository categoryObservationRepository) {
        this.observationRepository = observationRepository;
        this.categoryObservationRepository = categoryObservationRepository;
    }

    public Observation save(Observation observation) {
        return observationRepository.save(observation);
    }

    public Optional<Observation> findByID(Long id) {
        return observationRepository.findById(id);
    }

    public List<CategoryObservation> findActiveByPatientAndPhenomenon(Patient patient, Phenomenon phenomenon) {
        return categoryObservationRepository.findByPatientAndStatusAndPhenomenon(patient, Status.ACTIVE, phenomenon);
    }
}
