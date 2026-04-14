package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.AssociativeFunctionRepository;
import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import org.springframework.stereotype.Service;

import java.util.List;

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
}