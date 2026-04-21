package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.AssociativeFunctionRepository;
import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import iu.devinmehringer.project3.model.observation.DiagnosisStrategyType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociativeFunctionAccess {

    private final AssociativeFunctionRepository associativeFunctionRepository;

    public AssociativeFunctionAccess(AssociativeFunctionRepository associativeFunctionRepository) {
        this.associativeFunctionRepository = associativeFunctionRepository;
    }

    public List<AssociativeFunction> getAll() {
        return associativeFunctionRepository.findAll();
    }

    public void save(AssociativeFunction associativeFunction) {
        associativeFunctionRepository.save(associativeFunction);
    }
    
    public Optional<AssociativeFunction> getById(Long id) {
        return associativeFunctionRepository.findById(id);
    }
}