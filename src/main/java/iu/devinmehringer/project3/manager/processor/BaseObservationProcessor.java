package iu.devinmehringer.project3.manager.processor;

import iu.devinmehringer.project3.controller.dto.ObservationRequest;

public class BaseObservationProcessor implements ObservationProcessor {
    @Override
    public ObservationRequest process(ObservationRequest request) {
        return request;
    }
}
