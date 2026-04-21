package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.*;
import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreateProtocolException;
import iu.devinmehringer.project3.manager.processor.ObservationProcessor;
import iu.devinmehringer.project3.model.observation.AccuracyRating;
import iu.devinmehringer.project3.model.observation.Protocol;
import iu.devinmehringer.project3.model.user.Role;
import iu.devinmehringer.project3.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProtocolTest {

    @Mock private PhenomenonTypeAccess phenomenonTypeAccess;
    @Mock private ProtocolAccess protocolAccess;
    @Mock private ObservationAccess observationAccess;
    @Mock private PhenomenonAccess phenomenonAccess;
    @Mock private PatientAccess patientAccess;
    @Mock private AssociativeFunctionAccess associativeFunctionAccess;
    @Mock private CommandRunner commandRunner;
    @Mock private ApplicationEventPublisher applicationEventPublisher;
    @Mock private ObservationProcessor observationProcessor;
    @Mock private CommandLogAccess commandLogAccess;

    @InjectMocks
    private ObservationManager observationManager;

    private ProtocolRequest validRequest;
    private Protocol existingProtocol;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test", Role.CLINICIAN);

        validRequest = new ProtocolRequest();
        validRequest.setName("Oral Thermometer");
        validRequest.setDescription("Temperature taken orally");
        validRequest.setAccuracyRating(AccuracyRating.HIGH);
        validRequest.setPerformedBy(testUser);

        existingProtocol = new Protocol();
        existingProtocol.setId(1L);
        existingProtocol.setName("Oral Thermometer");
        existingProtocol.setDescription("Temperature taken orally");
        existingProtocol.setAccuracyRating(AccuracyRating.HIGH);
    }

    @Test
    void createProtocol_nullName_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setName(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.createProtocol(validRequest)
        );
    }

    @Test
    void createProtocol_nullDescription_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setDescription(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.createProtocol(validRequest)
        );
    }

    @Test
    void createProtocol_nullAccuracyRating_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setAccuracyRating(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.createProtocol(validRequest)
        );
    }

    @Test
    void createProtocol_validRequest_executesCommand() {
        // Arrange — validRequest already set up in setUp()

        // Act
        observationManager.createProtocol(validRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any(), eq(testUser));
    }

    @Test
    void getProtocols_noProtocols_returnsEmptyList() {
        // Arrange
        when(protocolAccess.getProtocols()).thenReturn(List.of());

        // Act
        List<Protocol> result = observationManager.getProtocols();

        // Assert
        assertTrue(result.isEmpty());
        verify(protocolAccess, times(1)).getProtocols();
    }

    @Test
    void getProtocols_existingProtocols_returnsList() {
        // Arrange
        when(protocolAccess.getProtocols()).thenReturn(List.of(existingProtocol));

        // Act
        List<Protocol> result = observationManager.getProtocols();

        // Assert
        assertEquals(1, result.size());
        verify(protocolAccess, times(1)).getProtocols();
    }

    @Test
    void updateProtocol_nullName_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setName(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.updateProtocol(1L, validRequest)
        );
    }

    @Test
    void updateProtocol_nullDescription_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setDescription(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.updateProtocol(1L, validRequest)
        );
    }

    @Test
    void updateProtocol_nullAccuracyRating_throwsInvalidCreateProtocolException() {
        // Arrange
        validRequest.setAccuracyRating(null);

        // Act / Assert
        assertThrows(InvalidCreateProtocolException.class, () ->
                observationManager.updateProtocol(1L, validRequest)
        );
    }

    @Test
    void updateProtocol_validRequest_executesCommand() {
        // Arrange — validRequest already set up in setUp()

        // Act
        observationManager.updateProtocol(1L, validRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any(), eq(testUser));
    }
}