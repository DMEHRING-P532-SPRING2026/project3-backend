package iu.devinmehringer.project3.manager.processor;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.observation.Flag;
import iu.devinmehringer.project3.model.observation.PhenomenonType;

public class AnomalyFlaggingDecorator implements ObservationProcessor {
    private final ObservationProcessor observationProcessor;
    private final PhenomenonTypeAccess phenomenonTypeAccess;

    public AnomalyFlaggingDecorator(ObservationProcessor observationProcessor, PhenomenonTypeAccess phenomenonTypeAccess) {
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

            if (phenomenonType.getNormalMin() != null && phenomenonType.getNormalMax() != null) {
                if (measurementRequest.getAmount().compareTo(phenomenonType.getNormalMax()) > 0
                        || measurementRequest.getAmount().compareTo(phenomenonType.getNormalMin()) < 0) {
                    measurementRequest.flag = Flag.ANOMALY;
                }
            }
        }
        return observationProcessor.process(request);
    }
}
