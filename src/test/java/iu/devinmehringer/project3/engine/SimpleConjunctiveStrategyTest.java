package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleConjunctiveStrategyTest {

    private SimpleConjunctiveStrategy strategy;
    private PhenomenonType bodyTempType;
    private PhenomenonType bloodPressureType;
    private PhenomenonType bloodTypeType;
    private Phenomenon bloodTypeA;
    private AssociativeFunction rule;

    @BeforeEach
    void setUp() {
        strategy = new SimpleConjunctiveStrategy();

        bodyTempType = new PhenomenonType();
        bodyTempType.setId(1L);
        bodyTempType.setName("Body Temperature");
        bodyTempType.setKind(Kind.QUANTITATIVE);
        bodyTempType.setAllowedUnits(List.of("celsius"));

        bloodPressureType = new PhenomenonType();
        bloodPressureType.setId(2L);
        bloodPressureType.setName("Blood Pressure");
        bloodPressureType.setKind(Kind.QUANTITATIVE);
        bloodPressureType.setAllowedUnits(List.of("mmhg"));

        bloodTypeType = new PhenomenonType();
        bloodTypeType.setId(3L);
        bloodTypeType.setName("Blood Type");
        bloodTypeType.setKind(Kind.QUALITATIVE);

        bloodTypeA = new Phenomenon();
        bloodTypeA.setId(1L);
        bloodTypeA.setName("A");
        bloodTypeA.setPhenomenonType(bloodTypeType);

        rule = new AssociativeFunction();
        rule.setId(1L);
        rule.setName("Test rule");
    }

    @Test
    void evaluate_allArgumentsPresent_returnsTrue() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType));

        Measurement measurement = activeMeasurement(bodyTempType);

        // Act
        boolean result = strategy.evaluate(rule, List.of(measurement));

        // Assert
        assertTrue(result);
    }

    @Test
    void evaluate_noObservations_returnsFalse() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType));

        // Act
        boolean result = strategy.evaluate(rule, List.of());

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_someArgumentsMissing_returnsFalse() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType, bloodPressureType));

        Measurement measurement = activeMeasurement(bodyTempType);

        // Act
        boolean result = strategy.evaluate(rule, List.of(measurement));

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_allArgumentsPresentMultipleRules_returnsTrue() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType, bloodPressureType));

        Measurement temp = activeMeasurement(bodyTempType);
        Measurement pressure = activeMeasurement(bloodPressureType);

        // Act
        boolean result = strategy.evaluate(rule, List.of(temp, pressure));

        // Assert
        assertTrue(result);
    }

    @Test
    void evaluate_rejectedObservationOnly_returnsFalse() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType));

        Measurement measurement = activeMeasurement(bodyTempType);
        measurement.setStatus(Status.REJECTED);

        // Act
        boolean result = strategy.evaluate(rule, List.of(measurement));

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_categoryObservationArgumentPresent_returnsTrue() {
        // Arrange
        rule.setArgumentConcepts(List.of(bloodTypeType));

        CategoryObservation category = new CategoryObservation();
        category.setStatus(Status.ACTIVE);
        category.setPresence(Presence.PRESENT);
        category.setPhenomenon(bloodTypeA);

        // Act
        boolean result = strategy.evaluate(rule, List.of(category));

        // Assert
        assertTrue(result);
    }

    @Test
    void evaluate_mixedActiveAndRejected_onlyCountsActive() {
        // Arrange
        rule.setArgumentConcepts(List.of(bodyTempType, bloodPressureType));

        Measurement active = activeMeasurement(bodyTempType);
        Measurement rejected = activeMeasurement(bloodPressureType);
        rejected.setStatus(Status.REJECTED);

        // Act
        boolean result = strategy.evaluate(rule, List.of(active, rejected));

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_emptyArgumentConcepts_returnsTrue() {
        // Arrange
        rule.setArgumentConcepts(List.of());

        // Act
        boolean result = strategy.evaluate(rule, List.of());

        // Assert
        assertTrue(result);
    }

    // --- helpers ---

    private Measurement activeMeasurement(PhenomenonType phenomenonType) {
        Measurement measurement = new Measurement();
        measurement.setStatus(Status.ACTIVE);
        measurement.setPhenomenonType(phenomenonType);
        measurement.setAmount(new BigDecimal("37.5"));
        measurement.setUnit("celsius");
        measurement.setRecordedAt(LocalDateTime.now());
        measurement.setApplicableAt(LocalDateTime.now());
        return measurement;
    }
}