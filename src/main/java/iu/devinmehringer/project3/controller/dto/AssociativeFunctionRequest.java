package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.DiagnosisStrategyType;

public class AssociativeFunctionRequest {
    private DiagnosisStrategyType diagnosisStrategyType;

    public DiagnosisStrategyType getDiagnosisStrategyType() { return diagnosisStrategyType; }

    public void setDiagnosisStrategyType(DiagnosisStrategyType diagnosisStrategyType) { this.diagnosisStrategyType = diagnosisStrategyType; }
}