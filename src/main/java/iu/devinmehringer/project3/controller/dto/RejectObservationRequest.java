package iu.devinmehringer.project3.controller.dto;

public class RejectObservationRequest extends BaseRequest {
    private String reason;
    private Long rejectedById;

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Long getRejectedById() { return rejectedById; }
    public void setRejectedById(Long rejectedById) { this.rejectedById = rejectedById; }
}