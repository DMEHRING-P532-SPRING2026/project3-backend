package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import iu.devinmehringer.project3.model.observation.Observation;

import java.util.List;

public interface DiagnosisStrategy {
    boolean evaluate(AssociativeFunction rule, List<Observation> patientObservations);
}