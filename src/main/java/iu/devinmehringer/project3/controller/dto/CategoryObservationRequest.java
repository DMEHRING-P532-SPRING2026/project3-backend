package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.Presence;

public class CategoryObservationRequest extends ObservationRequest {
    private Long phenomenonId;
    private Presence presence;

    public Long getPhenomenonId() { return phenomenonId; }

    public void setPhenomenonId(Long phenomenonId) { this.phenomenonId = phenomenonId; }

    public Presence getPresence() { return presence; }

    public void setPresence(Presence presence) { this.presence = presence; }
}