package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.user.User;

import java.time.LocalDateTime;

public class ObservationRequest extends BaseRequest {
    private Long patientId;
    private Long protocolId;
    private LocalDateTime applicableAt;
    public LocalDateTime recordedAt;

    public Long getPatientId() { return patientId; }

    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getProtocolId() { return protocolId; }

    public void setProtocolId(Long protocolId) { this.protocolId = protocolId; }

    public LocalDateTime getApplicableAt() { return applicableAt; }

    public void setApplicableAt(LocalDateTime applicableAt) { this.applicableAt = applicableAt; }
}