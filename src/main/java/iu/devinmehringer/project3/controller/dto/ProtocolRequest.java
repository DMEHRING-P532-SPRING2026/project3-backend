package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.AccuracyRating;

public class ProtocolRequest extends BaseRequest {
    private String name;
    private String description;
    private AccuracyRating accuracyRating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccuracyRating getAccuracyRating() {
        return accuracyRating;
    }

    public void setAccuracyRating(AccuracyRating accuracyRating) {
        this.accuracyRating = accuracyRating;
    }
}
