package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.PhenomenonTypeRepository;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhenomenonTypeAccess {
    private final PhenomenonTypeRepository phenomenonTypeRepository;

    public PhenomenonTypeAccess(PhenomenonTypeRepository phenomenonTypeRepository) {
        this.phenomenonTypeRepository = phenomenonTypeRepository;
    }

    public void save(PhenomenonType phenomenonType) {
        this.phenomenonTypeRepository.save(phenomenonType);
    }

    public List<PhenomenonType> getPhenomenonTypes() {
        return this.phenomenonTypeRepository.findAll();
    }
    
    public Optional<PhenomenonType> getById(Long id) {
        return this.phenomenonTypeRepository.findById(id);
    }

    public void delete(PhenomenonType phenomenonType) {
        this.phenomenonTypeRepository.delete(phenomenonType);
    }
}
