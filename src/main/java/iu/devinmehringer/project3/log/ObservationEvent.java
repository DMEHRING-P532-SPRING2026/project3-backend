package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.model.log.AuditType;
import iu.devinmehringer.project3.model.observation.Observation;

public record ObservationEvent(Observation observation, AuditType eventType) {
}