package iu.devinmehringer.project3.controller.dto;

public class PhenomenonResponse {
    private Long id;
    private String name;
    private Long phenomenonTypeId;
    private String phenomenonTypeName;

    public PhenomenonResponse(Long id, String name, Long phenomenonTypeId) {
        this.id = id;
        this.name = name;
        this.phenomenonTypeId = phenomenonTypeId;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getPhenomenonTypeId() { return phenomenonTypeId; }

    public void setPhenomenonTypeId(Long phenomenonTypeId) { this.phenomenonTypeId = phenomenonTypeId; }

}