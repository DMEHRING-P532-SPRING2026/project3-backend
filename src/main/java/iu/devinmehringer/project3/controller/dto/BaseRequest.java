package iu.devinmehringer.project3.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iu.devinmehringer.project3.model.user.User;

public abstract class BaseRequest {
    private User performedBy;

    public User getPerformedBy() { return performedBy; }

    @JsonIgnore
    public void setPerformedBy(User performedBy) { this.performedBy = performedBy; }
}