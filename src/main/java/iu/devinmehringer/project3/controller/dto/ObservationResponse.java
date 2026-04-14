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

    public ObservationResponse(Long id, String type, String phenomenonTypeName,
                               String value, String unit, String phenomenonName,
                               LocalDateTime recordedAt, LocalDateTime applicableAt,
                               String protocolName, String status) {
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
}