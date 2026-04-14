package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.PatientAccess;
import iu.devinmehringer.project3.command.CreatePatientCommand;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreatePatientRequestException;
import iu.devinmehringer.project3.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientManagerTest {

    @Mock
    private PatientAccess patientAccess;

    @Mock
    private CommandRunner commandRunner;

    @InjectMocks
    private PatientManager patientManager;

    @BeforeEach
    void setUp() {
        // runs before each test — set up any shared state here
    }

    @Test
    void createPatient_nullNameAndDob_throwsInvalidCreatePatientRequestException() {
        // Arrange
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFullName(null);
        patientRequest.setDateOfBirth(null);

        // Act / Assert
        assertThrows(InvalidCreatePatientRequestException.class, () -> {
            patientManager.createPatient(patientRequest);
        });
    }

    @Test
    void createPatient_validFields_executesCommand() {
        // Arrange
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFullName("John Smith");
        patientRequest.setDateOfBirth(LocalDate.of(1990, 5, 14));
        patientRequest.setNote("Referred by GP");

        // Act
        patientManager.createPatient(patientRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any(CreatePatientCommand.class));
    }

    @Test
    void getPatients_noPatients_returnsEmptyList() {
        // Arrange
        when(patientAccess.getPatients()).thenReturn(List.of());

        // Act
        List<Patient> result = patientManager.getPatients();

        // Assert
        assertTrue(result.isEmpty());
        verify(patientAccess, times(1)).getPatients();
    }
}