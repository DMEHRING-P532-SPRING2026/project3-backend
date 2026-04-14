package iu.devinmehringer.project3.controller.dto;

import java.time.LocalDateTime;

public class ObservationRequest {
    private Long patientId;
    private Long protocolId;
    private LocalDateTime applicableAt;

    public Long getPatientId() { return patientId; }

    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getProtocolId() { return protocolId; }

    public void setProtocolId(Long protocolId) { this.protocolId = protocolId; }

    public LocalDateTime getApplicableAt() { return applicableAt; }

    public void setApplicableAt(LocalDateTime applicableAt) { this.applicableAt = applicableAt; }
}