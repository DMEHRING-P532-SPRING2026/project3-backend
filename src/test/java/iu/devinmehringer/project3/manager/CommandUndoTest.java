package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.ObservationAccess;
import iu.devinmehringer.project3.controller.exception.ObservationNotFoundException;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandUndoTest {

    @Mock
    private ObservationAccess observationAccess;

    private Measurement measurement;
    private CategoryObservation categoryObservation;

    @BeforeEach
    void setUp() {
        Patient patient = new Patient();
        patient.setId(1L);

        measurement = new Measurement();
        measurement.setId(1L);
        measurement.setPatient(patient);
        measurement.setStatus(Status.ACTIVE);

        categoryObservation = new CategoryObservation();
        categoryObservation.setId(2L);
        categoryObservation.setPatient(patient);
        categoryObservation.setStatus(Status.REJECTED);
        categoryObservation.setRejectionReason("Wrong entry");
    }

    // --- CreateObservationCommand.undo() ---

    @Test
    void createObservationCommand_undo_setsStatusToRejected() {
        // Arrange
        when(observationAccess.findByID(1L)).thenReturn(Optional.of(measurement));
        CreateObservationCommand command = new CreateObservationCommand(observationAccess, 1L);

        // Act
        command.undo();

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertEquals(Status.REJECTED, captor.getValue().getStatus());
    }

    @Test
    void createObservationCommand_undo_setsRejectionReason() {
        // Arrange
        when(observationAccess.findByID(1L)).thenReturn(Optional.of(measurement));
        CreateObservationCommand command = new CreateObservationCommand(observationAccess, 1L);

        // Act
        command.undo();

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertEquals("Undone by user", captor.getValue().getRejectionReason());
    }

    @Test
    void createObservationCommand_undo_observationNotFound_throwsObservationNotFoundException() {
        // Arrange
        when(observationAccess.findByID(99L)).thenReturn(Optional.empty());
        CreateObservationCommand command = new CreateObservationCommand(observationAccess, 99L);

        // Act / Assert
        assertThrows(ObservationNotFoundException.class, command::undo);
    }

    // --- RejectObservationCommand.undo() ---

    @Test
    void rejectObservationCommand_undo_setsStatusToActive() {
        // Arrange
        when(observationAccess.findByID(2L)).thenReturn(Optional.of(categoryObservation));
        RejectObservationCommand command = new RejectObservationCommand(observationAccess, 2L);

        // Act
        command.undo();

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertEquals(Status.ACTIVE, captor.getValue().getStatus());
    }

    @Test
    void rejectObservationCommand_undo_clearsRejectionReason() {
        // Arrange
        when(observationAccess.findByID(2L)).thenReturn(Optional.of(categoryObservation));
        RejectObservationCommand command = new RejectObservationCommand(observationAccess, 2L);

        // Act
        command.undo();

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertNull(captor.getValue().getRejectionReason());
    }

    @Test
    void rejectObservationCommand_undo_clearsRejectedBy() {
        // Arrange
        Measurement rejectedBy = new Measurement();
        rejectedBy.setId(3L);
        categoryObservation.setRejectedBy(rejectedBy);
        when(observationAccess.findByID(2L)).thenReturn(Optional.of(categoryObservation));
        RejectObservationCommand command = new RejectObservationCommand(observationAccess, 2L);

        // Act
        command.undo();

        // Assert
        ArgumentCaptor<Observation> captor = ArgumentCaptor.forClass(Observation.class);
        verify(observationAccess).save(captor.capture());
        assertNull(captor.getValue().getRejectedBy());
    }

    @Test
    void rejectObservationCommand_undo_observationNotFound_throwsObservationNotFoundException() {
        // Arrange
        when(observationAccess.findByID(99L)).thenReturn(Optional.empty());
        RejectObservationCommand command = new RejectObservationCommand(observationAccess, 99L);

        // Act / Assert
        assertThrows(ObservationNotFoundException.class, command::undo);
    }

    // --- CreatePatientCommand.undo() ---

    @Test
    void createPatientCommand_undo_throwsUnsupportedOperationException() {
        // Arrange
        CreatePatientCommand command = new CreatePatientCommand(null, null);

        // Act / Assert
        assertThrows(UnsupportedOperationException.class, command::undo);
    }
}