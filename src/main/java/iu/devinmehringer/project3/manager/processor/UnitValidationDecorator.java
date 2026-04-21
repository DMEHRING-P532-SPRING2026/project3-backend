package iu.devinmehringer.project3.manager.processor;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.exception.InvalidObservationException;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.observation.PhenomenonType;


public class UnitValidationDecorator implements ObservationProcessor {
    private final ObservationProcessor observationProcessor;
    private final PhenomenonTypeAccess phenomenonTypeAccess;

    public UnitValidationDecorator(ObservationProcessor observationProcessor, PhenomenonTypeAccess phenomenonTypeAccess) {
        this.observationProcessor = observationProcessor;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
    }

    @Override
    public ObservationRequest process(ObservationRequest request) {
        if (request instanceof MeasurementRequest measurementRequest) {
            PhenomenonType phenomenonType = phenomenonTypeAccess
                    .getById(measurementRequest.getPhenomenonTypeId())
                    .orElseThrow(() -> new PhenomenonTypeNotFoundException("PhenomenonType not found for id: " +
                            measurementRequest.getPhenomenonTypeId()));
            if (!phenomenonType.getAllowedUnits().contains(measurementRequest.getUnit())) {
                throw new InvalidObservationException("Not in allowed units for PhenomenonType");
            }
        }
        return observationProcessor.process(request);
    }
}
