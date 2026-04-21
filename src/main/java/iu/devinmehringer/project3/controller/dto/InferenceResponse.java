package iu.devinmehringer.project3.controller.dto;

import iu.devinmehringer.project3.model.observation.DiagnosisStrategyType;

import java.util.List;

public class InferenceResponse {
    private String inferredConcept;
    private DiagnosisStrategyType strategyUsed;
    private List<Long> evidenceObservationIds;

    public InferenceResponse(String inferredConcept, DiagnosisStrategyType strategyUsed,
                             List<Long> evidenceObservationIds) {
        this.inferredConcept = inferredConcept;
        this.strategyUsed = strategyUsed;
        this.evidenceObservationIds = evidenceObservationIds;
    }

    public String getInferredConcept() { return inferredConcept; }

    public void setInferredConcept(String inferredConcept) { this.inferredConcept = inferredConcept; }

    public DiagnosisStrategyType getStrategyUsed() { return strategyUsed; }

    public void setStrategyUsed(DiagnosisStrategyType strategyUsed) { this.strategyUsed = strategyUsed; }

    public List<Long> getEvidenceObservationIds() { return evidenceObservationIds; }

    public void setEvidenceObservationIds(List<Long> evidenceObservationIds) { this.evidenceObservationIds = evidenceObservationIds; }
}