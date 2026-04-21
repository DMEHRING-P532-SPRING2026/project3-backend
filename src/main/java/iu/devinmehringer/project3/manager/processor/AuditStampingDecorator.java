package iu.devinmehringer.project3.manager.processor;

import iu.devinmehringer.project3.controller.dto.ObservationRequest;

import java.time.LocalDateTime;

public class AuditStampingDecorator implements ObservationProcessor{
    private final ObservationProcessor observationProcessor;

    public AuditStampingDecorator(ObservationProcessor observationProcessor) {
        this.observationProcessor = observationProcessor;
    }

    @Override
    public ObservationRequest process(ObservationRequest request) {
        request.recordedAt = LocalDateTime.now();
        // No need to stamp user because it needed for all requests
        return observationProcessor.process(request);
    }
}
