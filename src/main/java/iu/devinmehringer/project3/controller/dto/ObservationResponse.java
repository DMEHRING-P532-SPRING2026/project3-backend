package iu.devinmehringer.project3.controller.dto;

import java.time.LocalDateTime;

public class ObservationResponse {
    private Long id;
    private String type;
    private String phenomenonTypeName;
    private String value;
    private String unit;
    private String phenomenonName;
    private LocalDateTime recordedAt;
    private LocalDateTime applicableAt;
    private String protocolName;
    private String status;
    private String performedBy;
    private String flag;
    private String rejectionReason;
    private Long rejectedById;
    private String source;

    public ObservationResponse(Long id, String type, String phenomenonTypeName,
                               String value, String unit, String phenomenonName,
                               LocalDateTime recordedAt, LocalDateTime applicableAt,
                               String protocolName, String status,
                               String performedBy, String flag,
                               String rejectionReason, Long rejectedById,
                               String source) {
        this.id = id;
        this.type = type;
        this.phenomenonTypeName = phenomenonTypeName;
        this.value = value;
        this.unit = unit;
        this.phenomenonName = phenomenonName;
        this.recordedAt = recordedAt;
        this.applicableAt = applicableAt;
        this.protocolName = protocolName;
        this.status = status;
        this.performedBy = performedBy;
        this.flag = flag;
        this.rejectionReason = rejectionReason;
        this.rejectedById = rejectedById;
        this.source = source;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhenomenonTypeName() {
        return phenomenonTypeName;
    }

    public void setPhenomenonTypeName(String phenomenonTypeName) {
        this.phenomenonTypeName = phenomenonTypeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPhenomenonName() {
        return phenomenonName;
    }

    public void setPhenomenonName(String phenomenonName) {
        this.phenomenonName = phenomenonName;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public LocalDateTime getApplicableAt() {
        return applicableAt;
    }

    public void setApplicableAt(LocalDateTime applicableAt) {
        this.applicableAt = applicableAt;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Long getRejectedById() {
        return rejectedById;
    }

    public void setRejectedById(Long rejectedById) {
        this.rejectedById = rejectedById;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}