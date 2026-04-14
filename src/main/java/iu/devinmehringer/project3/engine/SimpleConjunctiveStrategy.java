package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SimpleConjunctiveStrategy implements DiagnosisStrategy {

    @Override
    public boolean evaluate(AssociativeFunction rule, List<Observation> patientObservations) {
        Set<Long> presentPhenomenonTypeIds = patientObservations.stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o instanceof Measurement)
                .map(o -> ((Measurement) o).getPhenomenonType().getId())
                .collect(Collectors.toSet());

        // for category observations collect the phenomenon TYPE id, not the phenomenon id
        Set<Long> presentCategoryPhenomenonTypeIds = patientObservations.stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o instanceof CategoryObservation)
                .map(o -> ((CategoryObservation) o).getPhenomenon().getPhenomenonType().getId())
                .collect(Collectors.toSet());

        return rule.getArgumentConcepts().stream()
                .allMatch(pt -> presentPhenomenonTypeIds.contains(pt.getId())
                        || presentCategoryPhenomenonTypeIds.contains(pt.getId()));
    }
}