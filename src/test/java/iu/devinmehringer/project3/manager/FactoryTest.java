package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.controller.dto.CategoryObservationRequest;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.exception.InvalidObservationException;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    private Patient patient;
    private Protocol protocol;
    private PhenomenonType quantitativePhenomenonType;
    private PhenomenonType qualitativePhenomenonType;
    private Phenomenon phenomenon;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Smith");

        protocol = new Protocol();
        protocol.setId(1L);
        protocol.setName("Oral Thermometer");

        quantitativePhenomenonType = new PhenomenonType();
        quantitativePhenomenonType.setId(1L);
        quantitativePhenomenonType.setName("Body Temperature");
        quantitativePhenomenonType.setKind(Kind.QUANTITATIVE);
        quantitativePhenomenonType.setAllowedUnits(List.of("celsius", "fahrenheit"));

        qualitativePhenomenonType = new PhenomenonType();
        qualitativePhenomenonType.setId(2L);
        qualitativePhenomenonType.setName("Blood Type");
        qualitativePhenomenonType.setKind(Kind.QUALITATIVE);

        phenomenon = new Phenomenon();
        phenomenon.setId(1L);
        phenomenon.setName("A");
        phenomenon.setPhenomenonType(qualitativePhenomenonType);
    }

    // --- measurement ---

    @Test
    void createMeasurement_nullPatientId_throwsInvalidObservationException() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();
        request.setPatientId(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, quantitativePhenomenonType, null, ObservationSource.MANUAL)
        );
    }

    @Test
    void createMeasurement_nullApplicableAt_throwsInvalidObservationException() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();
        request.setApplicableAt(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, quantitativePhenomenonType, null, ObservationSource.MANUAL)
        );
    }

    @Test
    void createMeasurement_nullAmount_throwsInvalidObservationException() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();
        request.setAmount(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, quantitativePhenomenonType, null, ObservationSource.MANUAL)
        );
    }

    @Test
    void createMeasurement_nullUnit_throwsInvalidObservationException() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();
        request.setUnit(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, quantitativePhenomenonType, null, ObservationSource.MANUAL)
        );
    }

    @Test
    void createMeasurement_qualitativePhenomenonType_throwsInvalidObservationException() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, qualitativePhenomenonType, null, ObservationSource.MANUAL)
        );
    }

    @Test
    void createMeasurement_noProtocol_returnsMeasurementWithNullProtocol() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();
        request.setProtocolId(null);

        // Act
        Observation result = ObservationFactory.create(request, patient, null, quantitativePhenomenonType, null, ObservationSource.MANUAL);

        // Assert
        assertInstanceOf(Measurement.class, result);
        assertNull(result.getProtocol());
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    void createMeasurement_validRequest_returnsMeasurement() {
        // Arrange
        MeasurementRequest request = validMeasurementRequest();

        // Act
        Observation result = ObservationFactory.create(request, patient, protocol, quantitativePhenomenonType, null, ObservationSource.MANUAL);

        // Assert
        assertInstanceOf(Measurement.class, result);
        Measurement measurement = (Measurement) result;
        assertEquals(new BigDecimal("37.5"), measurement.getAmount());
        assertEquals("celsius", measurement.getUnit());
        assertEquals(Status.ACTIVE, measurement.getStatus());
        assertNull(measurement.getRecordedAt());
        assertEquals(patient, measurement.getPatient());
        assertEquals(protocol, measurement.getProtocol());
    }

    // --- category observation ---

    @Test
    void createCategory_nullPatientId_throwsInvalidObservationException() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();
        request.setPatientId(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.MANUAL)
        );
    }

    @Test
    void createCategory_nullApplicableAt_throwsInvalidObservationException() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();
        request.setApplicableAt(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.MANUAL)
        );
    }

    @Test
    void createCategory_nullPresence_throwsInvalidObservationException() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();
        request.setPresence(null);

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.MANUAL)
        );
    }

    @Test
    void createCategory_quantitativePhenomenonType_throwsInvalidObservationException() {
        // Arrange
        Phenomenon wrongPhenomenon = new Phenomenon();
        wrongPhenomenon.setId(2L);
        wrongPhenomenon.setName("36.5");
        wrongPhenomenon.setPhenomenonType(quantitativePhenomenonType);
        CategoryObservationRequest request = validCategoryRequest();

        // Act / Assert
        assertThrows(InvalidObservationException.class, () ->
                ObservationFactory.create(request, patient, protocol, null, wrongPhenomenon, ObservationSource.MANUAL)
        );
    }

    @Test
    void createCategory_noProtocol_returnsCategoryWithNullProtocol() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();
        request.setProtocolId(null);

        // Act
        Observation result = ObservationFactory.create(request, patient, null, null, phenomenon, ObservationSource.MANUAL);

        // Assert
        assertInstanceOf(CategoryObservation.class, result);
        assertNull(result.getProtocol());
    }

    @Test
    void createCategory_validRequest_returnsCategoryObservation() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();

        // Act
        Observation result = ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.MANUAL);

        // Assert
        assertInstanceOf(CategoryObservation.class, result);
        CategoryObservation category = (CategoryObservation) result;
        assertEquals(Presence.PRESENT, category.getPresence());
        assertEquals(phenomenon, category.getPhenomenon());
        assertEquals(Status.ACTIVE, category.getStatus());
        assertNull(category.getRecordedAt());
        assertEquals(patient, category.getPatient());
        assertEquals(protocol, category.getProtocol());
    }

    @Test
    void createCategory_validRequest_sourceIsManual() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();

        // Act
        Observation result = ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.MANUAL);

        // Assert
        assertInstanceOf(CategoryObservation.class, result);
        assertEquals(ObservationSource.MANUAL, ((CategoryObservation) result).getSource());
    }

    @Test
    void createCategory_inferredSource_sourceIsInferred() {
        // Arrange
        CategoryObservationRequest request = validCategoryRequest();

        // Act
        Observation result = ObservationFactory.create(request, patient, protocol, null, phenomenon, ObservationSource.INFERRED);

        // Assert
        assertInstanceOf(CategoryObservation.class, result);
        assertEquals(ObservationSource.INFERRED, ((CategoryObservation) result).getSource());
    }

    private MeasurementRequest validMeasurementRequest() {
        MeasurementRequest request = new MeasurementRequest();
        request.setPatientId(1L);
        request.setProtocolId(1L);
        request.setPhenomenonTypeId(1L);
        request.setAmount(new BigDecimal("37.5"));
        request.setUnit("celsius");
        request.setApplicableAt(LocalDateTime.now());
        return request;
    }

    private CategoryObservationRequest validCategoryRequest() {
        CategoryObservationRequest request = new CategoryObservationRequest();
        request.setPatientId(1L);
        request.setProtocolId(1L);
        request.setPhenomenonId(1L);
        request.setPresence(Presence.PRESENT);
        request.setApplicableAt(LocalDateTime.now());
        return request;
    }
}