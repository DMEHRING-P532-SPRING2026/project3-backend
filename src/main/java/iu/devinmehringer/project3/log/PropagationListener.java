package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.access.ObservationAccess;
import iu.devinmehringer.project3.access.PhenomenonAccess;
import iu.devinmehringer.project3.controller.dto.CategoryObservationRequest;
import iu.devinmehringer.project3.manager.ObservationFactory;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PropagationListener {

    private final ObservationAccess observationAccess;
    private final PhenomenonAccess phenomenonAccess;

    public PropagationListener(ObservationAccess observationAccess, PhenomenonAccess phenomenonAccess) {
        this.observationAccess = observationAccess;
        this.phenomenonAccess = phenomenonAccess;
    }

    @EventListener
    public void onObservationEvent(ObservationEvent event) {
        Observation observation = event.observation();

        if (!(observation instanceof CategoryObservation obs)) return;
        if (obs.getSource() == ObservationSource.INFERRED) return;

        if (obs.getPresence() == Presence.PRESENT) {
            propagateUpward(obs);
        } else if (obs.getPresence() == Presence.ABSENT) {
            propagateDownward(obs);
        }
    }

    private void propagateUpward(CategoryObservation obs) {
        Phenomenon current = obs.getPhenomenon().getParentConcept();
        while (current != null) {
            if (!alreadyHasPresent(obs.getPatient(), current)) {
                saveInferred(obs, current, Presence.PRESENT);
            }
            current = current.getParentConcept();
        }
    }

    private void propagateDownward(CategoryObservation obs) {
        List<Phenomenon> children = phenomenonAccess.findByParentConcept(obs.getPhenomenon());
        for (Phenomenon child : children) {
            if (!alreadyHasAbsent(obs.getPatient(), child)) {
                saveInferred(obs, child, Presence.ABSENT);
                CategoryObservation synthetic = buildSynthetic(obs, child);
                propagateDownward(synthetic);
            }
        }
    }

    private boolean alreadyHasPresent(Patient patient, Phenomenon phenomenon) {
        return observationAccess.findActiveByPatientAndPhenomenon(patient, phenomenon)
                .stream()
                .anyMatch(o -> o.getPresence() == Presence.PRESENT);
    }

    private boolean alreadyHasAbsent(Patient patient, Phenomenon phenomenon) {
        return observationAccess.findActiveByPatientAndPhenomenon(patient, phenomenon)
                .stream()
                .anyMatch(o -> o.getPresence() == Presence.ABSENT);
    }

    private void saveInferred(CategoryObservation source, Phenomenon phenomenon, Presence presence) {
        CategoryObservationRequest request = new CategoryObservationRequest();
        request.setPatientId(source.getPatient().getId());
        request.setPhenomenonId(phenomenon.getId());
        request.setPresence(presence);
        request.setApplicableAt(source.getApplicableAt());
        request.recordedAt = LocalDateTime.now();
        request.setPerformedBy(source.getPerformedBy());

        Observation inferred = ObservationFactory.create(
                request, source.getPatient(), null, null, phenomenon,
                ObservationSource.INFERRED
        );
        observationAccess.save(inferred);
    }

    private CategoryObservation buildSynthetic(CategoryObservation source, Phenomenon phenomenon) {
        CategoryObservation synthetic = new CategoryObservation();
        synthetic.setPatient(source.getPatient());
        synthetic.setPhenomenon(phenomenon);
        synthetic.setPresence(source.getPresence());
        synthetic.setSource(ObservationSource.INFERRED);
        synthetic.setPerformedBy(source.getPerformedBy());
        synthetic.setApplicableAt(source.getApplicableAt());
        return synthetic;
    }
}