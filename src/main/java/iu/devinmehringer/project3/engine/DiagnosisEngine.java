package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.controller.dto.InferenceResponse;
import iu.devinmehringer.project3.model.observation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DiagnosisEngine {

    private final Map<DiagnosisStrategyType, DiagnosisStrategy> strategies;

    public DiagnosisEngine(Map<DiagnosisStrategyType, DiagnosisStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<InferenceResponse> evaluate(List<AssociativeFunction> rules,
                                            List<Observation> observations) {
        List<Observation> evidenceSet = observations.stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> !(o instanceof CategoryObservation c) || c.getSource() != ObservationSource.INFERRED)
                .collect(Collectors.toList());

        return rules.stream()
                .filter(rule -> ruleFires(rule, evidenceSet))
                .map(rule -> buildInference(rule, evidenceSet))
                .collect(Collectors.toList());
    }

    private boolean ruleFires(AssociativeFunction rule, List<Observation> observations) {
        DiagnosisStrategy strategy = strategies.get(rule.getDiagnosisStrategyType());
        if (strategy == null) return false;
        return strategy.evaluate(rule, observations);
    }

    private InferenceResponse buildInference(AssociativeFunction rule, List<Observation> observations) {
        return new InferenceResponse(
                rule.getProductConcept().getName(),
                rule.getDiagnosisStrategyType(),
                collectEvidence(rule, observations)
        );
    }

    private List<Long> collectEvidence(AssociativeFunction rule, List<Observation> observations) {
        return observations.stream()
                .filter(o -> matchesArgument(o, rule.getArgumentConcepts()))
                .map(Observation::getId)
                .collect(Collectors.toList());
    }

    private boolean matchesArgument(Observation observation, List<PhenomenonType> argumentConcepts) {
        return argumentConcepts.stream().anyMatch(pt -> {
            if (observation instanceof Measurement m) {
                return m.getPhenomenonType().getId().equals(pt.getId());
            } else if (observation instanceof CategoryObservation c) {
                return c.getPhenomenon().getPhenomenonType().getId().equals(pt.getId());
            }
            return false;
        });
    }
}