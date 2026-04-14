package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.access.AssociativeFunctionAccess;
import iu.devinmehringer.project3.access.PatientAccess;
import iu.devinmehringer.project3.engine.DiagnosisEngine;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RuleEvaluationListener {

    private final DiagnosisEngine diagnosisEngine;
    private final AssociativeFunctionAccess associativeFunctionAccess;
    private final PatientAccess patientAccess;

    public RuleEvaluationListener(DiagnosisEngine diagnosisEngine,
                                  AssociativeFunctionAccess associativeFunctionAccess,
                                  PatientAccess patientAccess) {
        this.diagnosisEngine = diagnosisEngine;
        this.associativeFunctionAccess = associativeFunctionAccess;
        this.patientAccess = patientAccess;
    }

    @EventListener
    public void onObservationEvent(ObservationEvent event) {
        Long patientId = event.observation().getPatient().getId();

        diagnosisEngine.evaluate(
                associativeFunctionAccess.getAll(),
                patientAccess.findById(patientId)
                        .orElseThrow()
                        .getObservations()
        );
    }
}