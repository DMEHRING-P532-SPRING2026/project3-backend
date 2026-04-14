package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.Kind;

import java.util.List;

public class PhenomenonTypeRequest {
    private String name;
    private Kind kind;
    private List<String> allowedUnits;
    private List<String> phenomena;

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
}
