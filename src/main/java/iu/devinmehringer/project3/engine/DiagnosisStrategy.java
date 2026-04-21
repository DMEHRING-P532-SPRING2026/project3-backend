package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DiagnosisStrategy {
    boolean evaluate(AssociativeFunction rule, List<Observation> patientObservations);

    default Set<Long> getPresentPhenomenonTypeIds(List<Observation> observations) {
        return observations.stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o instanceof Measurement)
                .map(o -> ((Measurement) o).getPhenomenonType().getId())
                .collect(Collectors.toSet());
    }

    default Set<Long> getPresentCategoryPhenomenonTypeIds(List<Observation> observations) {
        return observations.stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o instanceof CategoryObservation)
                .map(o -> ((CategoryObservation) o).getPhenomenon().getPhenomenonType().getId())
                .collect(Collectors.toSet());
    }
}