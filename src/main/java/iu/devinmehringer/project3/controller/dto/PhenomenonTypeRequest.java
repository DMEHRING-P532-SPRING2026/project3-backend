package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.Kind;

import java.math.BigDecimal;
import java.util.List;

public class PhenomenonTypeRequest extends BaseRequest {
    private String name;
    private Kind kind;
    private List<String> allowedUnits;
    private List<String> phenomena;
    private BigDecimal normalMin;
    private BigDecimal normalMax;

    public List<String> getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(List<String> phenomena) {
        this.phenomena = phenomena;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public List<String> getAllowedUnits() {
        return allowedUnits;
    }

    public void setAllowedUnits(List<String> allowedUnits) {
        this.allowedUnits = allowedUnits;
    }

    public BigDecimal getNormalMin() {
        return normalMin;
    }

    public void setNormalMin(BigDecimal normalMin) {
        this.normalMin = normalMin;
    }

    public BigDecimal getNormalMax() {
        return normalMax;
    }

    public void setNormalMax(BigDecimal normalMax) {
        this.normalMax = normalMax;
    }
}
