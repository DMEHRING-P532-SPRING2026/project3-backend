package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreatePhenomenonRequestException;
import iu.devinmehringer.project3.controller.exception.PhenomenonTypeNotFoundException;
import iu.devinmehringer.project3.model.observation.Kind;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhenomenonTypeTest {

    @Mock
    private PhenomenonTypeAccess phenomenonTypeAccess;

    @Mock
    private CommandRunner commandRunner;

    @InjectMocks
    private ObservationManager observationManager;

    private PhenomenonTypeRequest validQuantitativeRequest;
    private PhenomenonTypeRequest validQualitativeRequest;
    private PhenomenonType existingQuantitative;
    private PhenomenonType existingQualitative;

    @BeforeEach
    void setUp() {
        validQuantitativeRequest = new PhenomenonTypeRequest();
        validQuantitativeRequest.setName("Body Temperature");
        validQuantitativeRequest.setKind(Kind.QUANTITATIVE);
        validQuantitativeRequest.setAllowedUnits(List.of("celsius", "fahrenheit"));

        validQualitativeRequest = new PhenomenonTypeRequest();
        validQualitativeRequest.setName("Blood Type");
        validQualitativeRequest.setKind(Kind.QUALITATIVE);
        validQualitativeRequest.setPhenomena(List.of("A", "B", "AB", "O"));

        existingQuantitative = new PhenomenonType();
        existingQuantitative.setId(1L);
        existingQuantitative.setName("Body Temperature");
        existingQuantitative.setKind(Kind.QUANTITATIVE);
        existingQuantitative.setAllowedUnits(new ArrayList<>(List.of("celsius", "fahrenheit")));

        existingQualitative = new PhenomenonType();
        existingQualitative.setId(2L);
        existingQualitative.setName("Blood Type");
        existingQualitative.setKind(Kind.QUALITATIVE);
    }


    @Test
    void createPhenomenonType_nullName_throwsInvalidCreatePhenomenonRequestException() {
        // Arrange
        validQuantitativeRequest.setName(null);

        // Act / Assert
        assertThrows(InvalidCreatePhenomenonRequestException.class, () ->
                observationManager.createPhenomenonType(validQuantitativeRequest)
        );
    }

    @Test
    void createPhenomenonType_nullKind_throwsInvalidCreatePhenomenonRequestException() {
        // Arrange
        validQuantitativeRequest.setKind(null);

        // Act / Assert
        assertThrows(InvalidCreatePhenomenonRequestException.class, () ->
                observationManager.createPhenomenonType(validQuantitativeRequest)
        );
    }

    @Test
    void createPhenomenonType_quantitativeNoAllowedUnits_throwsInvalidCreatePhenomenonRequestException() {
        // Arrange
        validQuantitativeRequest.setAllowedUnits(List.of());

        // Act / Assert
        assertThrows(InvalidCreatePhenomenonRequestException.class, () ->
                observationManager.createPhenomenonType(validQuantitativeRequest)
        );
    }

    @Test
    void createPhenomenonType_qualitativeNoPhenomena_throwsInvalidCreatePhenomenonRequestException() {
        // Arrange
        validQualitativeRequest.setPhenomena(List.of());

        // Act / Assert
        assertThrows(InvalidCreatePhenomenonRequestException.class, () ->
                observationManager.createPhenomenonType(validQualitativeRequest)
        );
    }

    @Test
    void createPhenomenonType_validQuantitative_executesCommand() {
        // Arrange / Act
        observationManager.createPhenomenonType(validQuantitativeRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }

    @Test
    void createPhenomenonType_validQualitative_executesCommand() {
        // Arrange / Act
        observationManager.createPhenomenonType(validQualitativeRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }

    @Test
    void getPhenomenonTypes_noTypes_returnsEmptyList() {
        // Arrange
        when(phenomenonTypeAccess.getPhenomenonTypes()).thenReturn(List.of());

        // Act
        List<PhenomenonType> result = observationManager.getPhenomenonTypes();

        // Assert
        assertTrue(result.isEmpty());
        verify(phenomenonTypeAccess, times(1)).getPhenomenonTypes();
    }

    @Test
    void getPhenomenonTypes_existingTypes_returnsList() {
        // Arrange
        when(phenomenonTypeAccess.getPhenomenonTypes())
                .thenReturn(List.of(existingQuantitative, existingQualitative));

        // Act
        List<PhenomenonType> result = observationManager.getPhenomenonTypes();

        // Assert
        assertEquals(2, result.size());
        verify(phenomenonTypeAccess, times(1)).getPhenomenonTypes();
    }


    @Test
    void updatePhenomenonType_validQuantitative_executesCommand() {
        // Arrange / Act
        observationManager.updatePhenomenonType(1L, validQuantitativeRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }

    @Test
    void updatePhenomenonType_validQualitative_executesCommand() {
        // Arrange / Act
        observationManager.updatePhenomenonType(2L, validQualitativeRequest);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }


    @Test
    void deletePhenomenonType_existingId_executesCommand() {
        // Arrange / Act
        observationManager.deletePhenomenonType(1L);

        // Assert
        verify(commandRunner, times(1)).execute(any());
    }

    @Test
    void deletePhenomenonType_notFound_throwsPhenomenonTypeNotFoundException() {
        // Arrange
        doThrow(new PhenomenonTypeNotFoundException("Id not found:" + 99L))
                .when(commandRunner).execute(any());

        // Act / Assert
        assertThrows(PhenomenonTypeNotFoundException.class, () ->
                observationManager.deletePhenomenonType(99L)
        );
    }
}