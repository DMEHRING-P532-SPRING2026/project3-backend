package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.AccuracyRating;

public class ProtocolResponse {
    private Long id;
    private String name;
    private String description;
    private AccuracyRating accuracyRating;

    public ProtocolResponse(Long id, String name, String description, AccuracyRating accuracyRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accuracyRating = accuracyRating;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public AccuracyRating getAccuracyRating() { return accuracyRating; }

    public void setAccuracyRating(AccuracyRating accuracyRating) { this.accuracyRating = accuracyRating; }
}