package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.DiagnosisStrategyType;

import java.util.List;

public class AssociativeFunctionResponse {
    private Long id;
    private String name;
    private List<String> argumentConceptNames;
    private String productConceptName;
    private DiagnosisStrategyType diagnosisStrategyType;

    public AssociativeFunctionResponse(Long id, String name, List<String> argumentConceptNames,
                                       String productConceptName, DiagnosisStrategyType diagnosisStrategyType) {
        this.id = id;
        this.name = name;
        this.argumentConceptNames = argumentConceptNames;
        this.productConceptName = productConceptName;
        this.diagnosisStrategyType = diagnosisStrategyType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getArgumentConceptNames() { return argumentConceptNames; }
    public void setArgumentConceptNames(List<String> argumentConceptNames) { this.argumentConceptNames = argumentConceptNames; }

    public String getProductConceptName() { return productConceptName; }
    public void setProductConceptName(String productConceptName) { this.productConceptName = productConceptName; }

    public DiagnosisStrategyType getDiagnosisStrategyType() { return diagnosisStrategyType; }
    public void setDiagnosisStrategyType(DiagnosisStrategyType diagnosisStrategyType) { this.diagnosisStrategyType = diagnosisStrategyType; }
}