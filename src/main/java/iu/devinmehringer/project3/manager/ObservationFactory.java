package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.controller.dto.CategoryObservationRequest;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.exception.InvalidObservationException;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;

import java.time.LocalDateTime;

public class ObservationFactory {
    public static Observation create(ObservationRequest request,
                                     Patient patient,
                                     Protocol protocol,
                                     PhenomenonType phenomenonType,
                                     Phenomenon phenomenon) {

        if (request.getPatientId() == null) {
            throw new InvalidObservationException("Patient id is required");
        }
        if (request.getApplicableAt() == null) {
            throw new InvalidObservationException("Applicable time is required");
        }

        if (request instanceof MeasurementRequest m) {
            if (m.getAmount() == null) {
                throw new InvalidObservationException("Amount is required");
            }
            if (m.getUnit() == null || m.getUnit().isBlank()) {
                throw new InvalidObservationException("Unit is required");
            }
            if (!phenomenonType.getKind().equals(Kind.QUANTITATIVE)) {
                throw new InvalidObservationException("Phenomenon type must be quantitative");
            }
            if (!phenomenonType.getAllowedUnits().contains(m.getUnit())) {
                throw new InvalidObservationException("Unit not allowed for this phenomenon type");
            }

            Measurement measurement = new Measurement();
            measurement.setPatient(patient);
            measurement.setProtocol(protocol);
            measurement.setRecordedAt(LocalDateTime.now());
            measurement.setApplicableAt(request.getApplicableAt());
            measurement.setStatus(Status.ACTIVE);
            measurement.setAmount(m.getAmount());
            measurement.setUnit(m.getUnit());
            measurement.setPhenomenonType(phenomenonType);
            return measurement;

        } else if (request instanceof CategoryObservationRequest c) {
            if (c.getPresence() == null) {
                throw new InvalidObservationException("Presence status is required");
            }
            if (!phenomenon.getPhenomenonType().getKind().equals(Kind.QUALITATIVE)) {
                throw new InvalidObservationException("Phenomenon type must be qualitative");
            }

            CategoryObservation category = new CategoryObservation();
            category.setPatient(patient);
            category.setProtocol(protocol);
            category.setRecordedAt(LocalDateTime.now());
            category.setApplicableAt(request.getApplicableAt());
            category.setStatus(Status.ACTIVE);
            category.setPresence(c.getPresence());
            category.setPhenomenon(phenomenon);
            return category;
        }

        throw new InvalidObservationException("Unknown observation request type");
    }
}