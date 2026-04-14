package iu.devinmehringer.project3.model.observation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MEASUREMENT")
public class Measurement extends Observation {
    private BigDecimal amount;
    private String unit;

    @ManyToOne(optional = true)
    @JoinColumn(name="phenomenon_type_id")
    private PhenomenonType phenomenonType;

    public Measurement(BigDecimal amount, String unit, PhenomenonType phenomenonType) {
        this.amount = amount;
        this.unit = unit;
        this.phenomenonType = phenomenonType;
    }

    public Measurement(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public PhenomenonType getPhenomenonType() {
        return phenomenonType;
    }

    public void setPhenomenonType(PhenomenonType phenomenonType) {
        this.phenomenonType = phenomenonType;
    }
}
