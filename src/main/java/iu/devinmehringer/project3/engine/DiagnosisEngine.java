package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiagnosisEngine {

    private final DiagnosisStrategy strategy;

    public DiagnosisEngine(DiagnosisStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Phenomenon> evaluate(List<AssociativeFunction> rules,
                                     List<Observation> observations) {
        return rules.stream()
                .filter(rule -> strategy.evaluate(rule, observations))
                .map(AssociativeFunction::getProductConcept)
                .collect(Collectors.toList());
    }
}