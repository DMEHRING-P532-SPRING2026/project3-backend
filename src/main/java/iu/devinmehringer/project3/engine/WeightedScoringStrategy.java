package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("WEIGHTED")
public class WeightedScoringStrategy implements DiagnosisStrategy {

    @Override
    public boolean evaluate(AssociativeFunction rule, List<Observation> patientObservations) {
        if (rule.getArgumentWeights() == null || rule.getArgumentWeights().isEmpty()) {
            return false;
        }
        if (rule.getThreshold() == null) {
            return false;
        }

        Set<Long> presentPhenomenonTypeIds = getPresentPhenomenonTypeIds(patientObservations);
        Set<Long> presentCategoryPhenomenonTypeIds = getPresentCategoryPhenomenonTypeIds(patientObservations);

        double total = 0.0;
        List<PhenomenonType> arguments = rule.getArgumentConcepts();
        List<Double> weights = rule.getArgumentWeights();

        for (int i = 0; i < arguments.size(); i++) {
            if (i >= weights.size()) break;
            Long ptId = arguments.get(i).getId();
            if (presentPhenomenonTypeIds.contains(ptId)
                    || presentCategoryPhenomenonTypeIds.contains(ptId)) {
                total += weights.get(i);
            }
        }

        return total >= rule.getThreshold();
    }
}