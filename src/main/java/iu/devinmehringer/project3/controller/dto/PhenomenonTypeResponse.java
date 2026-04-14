package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.Kind;

import java.util.ArrayList;
import java.util.List;

public class PhenomenonTypeResponse {
    private Long id;
    private String name;
    private Kind kind;
    private List<String> allowedUnits = new ArrayList<>();
    private List<PhenomenonResponse> phenomena = new ArrayList<>();

    public PhenomenonTypeResponse(Long id, String name, Kind kind, List<String> allowedUnits, List<PhenomenonResponse> phenomena) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.allowedUnits = allowedUnits;
        this.phenomena = phenomena;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Kind getKind() { return kind; }

    public void setKind(Kind kind) { this.kind = kind; }

    public List<String> getAllowedUnits() { return allowedUnits; }

    public void setAllowedUnits(List<String> allowedUnits) { this.allowedUnits = allowedUnits; }

    public List<PhenomenonResponse> getPhenomena() { return phenomena; }

    public void setPhenomena(List<PhenomenonResponse> phenomena) { this.phenomena = phenomena; }
}