package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.ProtocolAccess;
import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreateProtocolException;
import iu.devinmehringer.project3.controller.exception.ProtocolNotFoundException;
import iu.devinmehringer.project3.model.observation.AccuracyRating;
import iu.devinmehringer.project3.model.observation.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProtocolTest {

    @Mock
    private ProtocolAccess protocolAccess;

    @Mock
    private CommandRunner commandRunner;

    @InjectMocks
    private ObservationManager observationManager;

    private ProtocolRequest validRequest;
    private Protocol existingProtocol;

    @BeforeEach
    void setUp() {
        validRequest = new ProtocolRequest();
        validRequest.setName("Oral Thermometer");
        validRequest.setDescription("Temperature taken orally");
        validRequest.setAccuracyRating(AccuracyRating.HIGH);

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
        // Arrange / Act
        observationManager.createProtocol(validRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
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
        // Arrange / Act
        observationManager.updateProtocol(1L, validRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }


    @Test
    void deleteProtocol_existingId_executesCommand() {
        // Arrange / Act
        observationManager.deleteProtocol(1L);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }

    @Test
    void deleteProtocol_notFound_throwsProtocolNotFoundException() {
        // Arrange
        doThrow(new ProtocolNotFoundException("Id not found: " + 99L))
                .when(commandRunner).execute(any());

        // Act / Assert
        assertThrows(ProtocolNotFoundException.class, () ->
                observationManager.deleteProtocol(99L)
        );
    }
}