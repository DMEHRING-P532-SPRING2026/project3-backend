package iu.devinmehringer.project3.controller.dto;

import java.math.BigDecimal;

public class MeasurementRequest extends ObservationRequest {
    private Long phenomenonTypeId;
    private BigDecimal amount;
    private String unit;

    public Long getPhenomenonTypeId() { return phenomenonTypeId; }

    public void setPhenomenonTypeId(Long phenomenonTypeId) { this.phenomenonTypeId = phenomenonTypeId; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }
}
