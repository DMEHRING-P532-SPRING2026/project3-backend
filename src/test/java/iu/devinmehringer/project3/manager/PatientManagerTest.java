package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.PatientAccess;
import iu.devinmehringer.project3.command.CreatePatientCommand;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreatePatientRequestException;
import iu.devinmehringer.project3.model.patient.Patient;
import iu.devinmehringer.project3.model.user.Role;
import iu.devinmehringer.project3.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientManagerTest {

    @Mock
    private PatientAccess patientAccess;

    @Mock
    private CommandRunner commandRunner;

    @InjectMocks
    private PatientManager patientManager;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test", Role.CLINICIAN);
    }

    @Test
    void createPatient_nullNameAndDob_throwsInvalidCreatePatientRequestException() {
        // Arrange
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFullName(null);
        patientRequest.setDateOfBirth(null);
        patientRequest.setPerformedBy(testUser);

        // Act / Assert
        assertThrows(InvalidCreatePatientRequestException.class, () ->
                patientManager.createPatient(patientRequest)
        );
    }

    @Test
    void createPatient_validFields_executesCommand() {
        // Arrange
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFullName("John Smith");
        patientRequest.setDateOfBirth(LocalDate.of(1990, 5, 14));
        patientRequest.setNote("Referred by GP");
        patientRequest.setPerformedBy(testUser);

        // Act
        patientManager.createPatient(patientRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any(CreatePatientCommand.class), eq(testUser));
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