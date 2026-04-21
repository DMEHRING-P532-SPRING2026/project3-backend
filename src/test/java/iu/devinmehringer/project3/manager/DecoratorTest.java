package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.exception.InvalidObservationException;
import iu.devinmehringer.project3.manager.processor.AnomalyFlaggingDecorator;
import iu.devinmehringer.project3.manager.processor.AuditStampingDecorator;
import iu.devinmehringer.project3.manager.processor.ObservationProcessor;
import iu.devinmehringer.project3.manager.processor.UnitValidationDecorator;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.user.Role;
import iu.devinmehringer.project3.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DecoratorTest {

    @Mock
    private PhenomenonTypeAccess phenomenonTypeAccess;

    private ObservationProcessor baseProcessor;
    private PhenomenonType phenomenonType;

    @BeforeEach
    void setUp() {
        baseProcessor = request -> request;

        phenomenonType = new PhenomenonType();
        phenomenonType.setId(1L);
        phenomenonType.setKind(Kind.QUANTITATIVE);
        phenomenonType.setAllowedUnits(List.of("celsius", "fahrenheit"));
        phenomenonType.setNormalMin(new BigDecimal("36.0"));
        phenomenonType.setNormalMax(new BigDecimal("37.5"));
    }

    private MeasurementRequest measurementRequest(String unit, BigDecimal amount) {
        MeasurementRequest request = new MeasurementRequest();
        request.setPatientId(1L);
        request.setPhenomenonTypeId(1L);
        request.setUnit(unit);
        request.setAmount(amount);
        request.setApplicableAt(LocalDateTime.now());
        return request;
    }

    // --- UnitValidationDecorator ---

    @Test
    void unitValidation_allowedUnit_doesNotThrow() {
        // Arrange
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        ObservationProcessor processor = new UnitValidationDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("37.0"));

        // Act / Assert
        assertDoesNotThrow(() -> processor.process(request));
    }

    @Test
    void unitValidation_disallowedUnit_throwsInvalidObservationException() {
        // Arrange
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        ObservationProcessor processor = new UnitValidationDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("kelvin", new BigDecimal("310.0"));

        // Act / Assert
        assertThrows(InvalidObservationException.class, () -> processor.process(request));
    }

    @Test
    void unitValidation_nonMeasurementRequest_passesThrough() {
        // Arrange
        ObservationProcessor processor = new UnitValidationDecorator(baseProcessor, phenomenonTypeAccess);
        ObservationRequest request = new ObservationRequest() {};

        // Act / Assert
        assertDoesNotThrow(() -> processor.process(request));
        verifyNoInteractions(phenomenonTypeAccess);
    }

    // --- AnomalyFlaggingDecorator ---

    @Test
    void anomalyFlagging_valueWithinRange_noFlagSet() {
        // Arrange
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        ObservationProcessor processor = new AnomalyFlaggingDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("37.0"));

        // Act
        ObservationRequest result = processor.process(request);

        // Assert
        assertNull(((MeasurementRequest) result).getFlag());
    }

    @Test
    void anomalyFlagging_valueAboveMax_flagsAnomaly() {
        // Arrange
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        ObservationProcessor processor = new AnomalyFlaggingDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("39.0"));

        // Act
        ObservationRequest result = processor.process(request);

        // Assert
        assertEquals(Flag.ANOMALY, ((MeasurementRequest) result).getFlag());
    }

    @Test
    void anomalyFlagging_valueBelowMin_flagsAnomaly() {
        // Arrange
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        ObservationProcessor processor = new AnomalyFlaggingDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("35.0"));

        // Act
        ObservationRequest result = processor.process(request);

        // Assert
        assertEquals(Flag.ANOMALY, ((MeasurementRequest) result).getFlag());
    }

    @Test
    void anomalyFlagging_noRangeConfigured_noFlagSet() {
        // Arrange
        PhenomenonType noRangeType = new PhenomenonType();
        noRangeType.setId(1L);
        noRangeType.setKind(Kind.QUANTITATIVE);
        noRangeType.setAllowedUnits(List.of("celsius"));
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(noRangeType));
        ObservationProcessor processor = new AnomalyFlaggingDecorator(baseProcessor, phenomenonTypeAccess);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("999.0"));

        // Act
        ObservationRequest result = processor.process(request);

        // Assert
        assertNull(((MeasurementRequest) result).getFlag());
    }

    // --- AuditStampingDecorator ---

    @Test
    void auditStamping_setsRecordedAt() {
        // Arrange
        ObservationProcessor processor = new AuditStampingDecorator(baseProcessor);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("37.0"));

        // Act
        processor.process(request);

        // Assert
        assertNotNull(request.recordedAt);
    }

    @Test
    void auditStamping_recordedAtIsApproximatelyNow() {
        // Arrange
        ObservationProcessor processor = new AuditStampingDecorator(baseProcessor);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("37.0"));
        LocalDateTime before = LocalDateTime.now();

        // Act
        processor.process(request);
        LocalDateTime after = LocalDateTime.now();

        // Assert
        assertFalse(request.recordedAt.isBefore(before));
        assertFalse(request.recordedAt.isAfter(after));
    }

    @Test
    void auditStamping_performedByPreserved() {
        // Arrange
        User user = new User("test", Role.CLINICIAN);
        ObservationProcessor processor = new AuditStampingDecorator(baseProcessor);
        MeasurementRequest request = measurementRequest("celsius", new BigDecimal("37.0"));
        request.setPerformedBy(user);

        // Act
        ObservationRequest result = processor.process(request);

        // Assert
        assertEquals(user, result.getPerformedBy());
    }
}