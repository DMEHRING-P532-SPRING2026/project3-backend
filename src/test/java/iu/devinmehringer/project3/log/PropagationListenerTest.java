package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.access.ObservationAccess;
import iu.devinmehringer.project3.access.PhenomenonAccess;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropagationListenerTest {

    @Mock
    private ObservationAccess observationAccess;

    @Mock
    private PhenomenonAccess phenomenonAccess;

    private PropagationListener listener;
    private Patient patient;
    private PhenomenonType qualType;
    private Phenomenon child;
    private Phenomenon parent;
    private Phenomenon grandparent;

    @BeforeEach
    void setUp() {
        listener = new PropagationListener(observationAccess, phenomenonAccess);

        patient = new Patient();
        patient.setId(1L);

        qualType = new PhenomenonType();
        qualType.setId(1L);
        qualType.setKind(Kind.QUALITATIVE);

        grandparent = new Phenomenon();
        grandparent.setId(3L);
        grandparent.setName("Illness");
        grandparent.setPhenomenonType(qualType);
        grandparent.setParentConcept(null);

        parent = new Phenomenon();
        parent.setId(2L);
        parent.setName("Infection");
        parent.setPhenomenonType(qualType);
        parent.setParentConcept(grandparent);

        child = new Phenomenon();
        child.setId(1L);
        child.setName("Bacterial Infection");
        child.setPhenomenonType(qualType);
        child.setParentConcept(parent);
    }

    private CategoryObservation categoryObs(Phenomenon phenomenon, Presence presence, ObservationSource source) {
        CategoryObservation obs = new CategoryObservation();
        obs.setPatient(patient);
        obs.setPhenomenon(phenomenon);
        obs.setPresence(presence);
        obs.setSource(source);
        obs.setStatus(Status.ACTIVE);
        obs.setApplicableAt(LocalDateTime.now());
        return obs;
    }

    @Test
    void onObservationEvent_presentObservation_propagatesUpwardToParent() {
        // Arrange
        CategoryObservation obs = categoryObs(child, Presence.PRESENT, ObservationSource.MANUAL);
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, parent)).thenReturn(List.of());
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, grandparent)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        verify(observationAccess, atLeastOnce()).save(any());
    }

    @Test
    void onObservationEvent_presentObservation_inferredObservationHasCorrectPresence() {
        // Arrange
        CategoryObservation obs = categoryObs(child, Presence.PRESENT, ObservationSource.MANUAL);
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, parent)).thenReturn(List.of());
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, grandparent)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess, atLeastOnce()).save(captor.capture());
        captor.getAllValues().forEach(saved -> {
            assertInstanceOf(CategoryObservation.class, saved);
            assertEquals(Presence.PRESENT, ((CategoryObservation) saved).getPresence());
        });
    }

    @Test
    void onObservationEvent_presentObservation_inferredObservationSourceIsInferred() {
        // Arrange
        CategoryObservation obs = categoryObs(child, Presence.PRESENT, ObservationSource.MANUAL);
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, parent)).thenReturn(List.of());
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, grandparent)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess, atLeastOnce()).save(captor.capture());
        captor.getAllValues().forEach(saved -> {
            assertInstanceOf(CategoryObservation.class, saved);
            assertEquals(ObservationSource.INFERRED, ((CategoryObservation) saved).getSource());
        });
    }

    @Test
    void onObservationEvent_presentObservation_parentAlreadyPresent_doesNotDuplicate() {
        // Arrange
        CategoryObservation obs = categoryObs(child, Presence.PRESENT, ObservationSource.MANUAL);
        CategoryObservation existingParent = categoryObs(parent, Presence.PRESENT, ObservationSource.MANUAL);
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, parent)).thenReturn(List.of(existingParent));
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, grandparent)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess, atLeastOnce()).save(captor.capture());
        captor.getAllValues().forEach(saved ->
                assertNotEquals(parent, ((CategoryObservation) saved).getPhenomenon())
        );
    }

    @Test
    void onObservationEvent_inferredObservation_doesNotPropagate() {
        // Arrange
        CategoryObservation obs = categoryObs(child, Presence.PRESENT, ObservationSource.INFERRED);

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        verifyNoInteractions(phenomenonAccess);
        verify(observationAccess, never()).save(any());
    }

    @Test
    void onObservationEvent_absentObservation_propagatesDownward() {
        // Arrange
        CategoryObservation obs = categoryObs(parent, Presence.ABSENT, ObservationSource.MANUAL);
        when(phenomenonAccess.findByParentConcept(parent)).thenReturn(List.of(child));
        when(phenomenonAccess.findByParentConcept(child)).thenReturn(List.of());
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, child)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        verify(observationAccess, atLeastOnce()).save(any());
    }

    @Test
    void onObservationEvent_absentObservation_inferredObservationHasAbsentPresence() {
        // Arrange
        CategoryObservation obs = categoryObs(parent, Presence.ABSENT, ObservationSource.MANUAL);
        when(phenomenonAccess.findByParentConcept(parent)).thenReturn(List.of(child));
        when(phenomenonAccess.findByParentConcept(child)).thenReturn(List.of());
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, child)).thenReturn(List.of());

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertEquals(Presence.ABSENT, ((CategoryObservation) captor.getValue()).getPresence());
    }

    @Test
    void onObservationEvent_measurementObservation_doesNotPropagate() {
        // Arrange
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setPatient(patient);
        measurement.setStatus(Status.ACTIVE);

        // Act
        listener.onObservationEvent(new ObservationEvent(measurement, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        verifyNoInteractions(phenomenonAccess);
        verify(observationAccess, never()).save(any());
    }

    @Test
    void onObservationEvent_absentObservation_childAlreadyAbsent_doesNotDuplicate() {
        // Arrange
        CategoryObservation obs = categoryObs(parent, Presence.ABSENT, ObservationSource.MANUAL);
        CategoryObservation existingChild = categoryObs(child, Presence.ABSENT, ObservationSource.MANUAL);
        when(phenomenonAccess.findByParentConcept(parent)).thenReturn(List.of(child));
        when(observationAccess.findActiveByPatientAndPhenomenon(patient, child)).thenReturn(List.of(existingChild));

        // Act
        listener.onObservationEvent(new ObservationEvent(obs, iu.devinmehringer.project3.model.log.AuditType.CREATE));

        // Assert
        verify(observationAccess, never()).save(any());
    }
}