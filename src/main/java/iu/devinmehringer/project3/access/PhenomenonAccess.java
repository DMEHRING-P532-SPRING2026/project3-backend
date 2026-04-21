package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.PhenomenonRepository;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void save(Phenomenon phenomenon) {
        this.phenomenonRepository.save(phenomenon);
    }

    public List<Phenomenon> getAll() {
        return phenomenonRepository.findAll();
    }

    public List<Phenomenon> findByParentConcept(Phenomenon parentConcept) {
        return phenomenonRepository.findByParentConcept(parentConcept);
    }
}
