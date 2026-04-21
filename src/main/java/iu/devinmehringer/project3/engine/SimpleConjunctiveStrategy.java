package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("CONJUNCTIVE")
public class SimpleConjunctiveStrategy implements DiagnosisStrategy {

    @Override
    public boolean evaluate(AssociativeFunction rule, List<Observation> patientObservations) {
        Set<Long> presentPhenomenonTypeIds = getPresentPhenomenonTypeIds(patientObservations);
        Set<Long> presentCategoryPhenomenonTypeIds = getPresentCategoryPhenomenonTypeIds(patientObservations);

        return rule.getArgumentConcepts().stream()
                .allMatch(pt -> presentPhenomenonTypeIds.contains(pt.getId())
                        || presentCategoryPhenomenonTypeIds.contains(pt.getId()));
    }
}