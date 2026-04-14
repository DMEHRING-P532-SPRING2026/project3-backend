package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.ObservationRepository;
import iu.devinmehringer.project3.model.observation.Observation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObservationAccess {
    private final ObservationRepository observationRepository;

    public ObservationAccess(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    public Observation save(Observation observation) {
        return observationRepository.save(observation);
    }

    public Optional<Observation> findByID(Long id) {
        return observationRepository.findById(id);
    }
}
