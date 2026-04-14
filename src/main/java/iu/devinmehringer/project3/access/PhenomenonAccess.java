package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.PhenomenonRepository;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhenomenonAccess {
    PhenomenonRepository phenomenonRepository;

    public PhenomenonAccess(PhenomenonRepository phenomenonRepository) {
        this.phenomenonRepository = phenomenonRepository;
    }

    public Optional<Phenomenon> getById(Long id) {
        return phenomenonRepository.findById(id);
    }
}
