package iu.devinmehringer.project3.controller.dto;

public class PhenomenonRequest extends BaseRequest {
    private String name;
    private Long phenomenonTypeId;
    private Long parentConceptId; // nullable

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getPhenomenonTypeId() { return phenomenonTypeId; }

    public void setPhenomenonTypeId(Long phenomenonTypeId) { this.phenomenonTypeId = phenomenonTypeId; }

    public Long getParentConceptId() { return parentConceptId; }

    public void setParentConceptId(Long parentConceptId) { this.parentConceptId = parentConceptId; }
}