package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedScoringStrategyTest {

    private WeightedScoringStrategy strategy;
    private Patient patient;
    private PhenomenonType typeA;
    private PhenomenonType typeB;

    @BeforeEach
    void setUp() {
        strategy = new WeightedScoringStrategy();

        patient = new Patient();
        patient.setId(1L);

        typeA = new PhenomenonType();
        typeA.setId(1L);
        typeA.setKind(Kind.QUANTITATIVE);

        typeB = new PhenomenonType();
        typeB.setId(2L);
        typeB.setKind(Kind.QUANTITATIVE);
    }

    private Measurement measurement(PhenomenonType type) {
        Measurement m = new Measurement();
        m.setPatient(patient);
        m.setPhenomenonType(type);
        m.setAmount(new BigDecimal("1.0"));
        m.setUnit("unit");
        m.setStatus(Status.ACTIVE);
        m.setApplicableAt(LocalDateTime.now());
        return m;
    }

    private AssociativeFunction rule(List<PhenomenonType> args, List<Double> weights, double threshold) {
        AssociativeFunction rule = new AssociativeFunction();
        rule.setArgumentConcepts(args);
        rule.setArgumentWeights(weights);
        rule.setThreshold(threshold);
        rule.setDiagnosisStrategyType(DiagnosisStrategyType.WEIGHTED);
        return rule;
    }

    @Test
    void evaluate_weightsSumExceedsThreshold_returnsTrue() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(0.6, 0.5), 1.0);
        List<Observation> obs = List.of(measurement(typeA), measurement(typeB));

        // Act
        boolean result = strategy.evaluate(r, obs);

        // Assert
        assertTrue(result);
    }

    @Test
    void evaluate_weightsSumBelowThreshold_returnsFalse() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(0.3, 0.3), 1.0);
        List<Observation> obs = List.of(measurement(typeA), measurement(typeB));

        // Act
        boolean result = strategy.evaluate(r, obs);

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_onlyOneArgumentPresent_partialWeight_belowThreshold_returnsFalse() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(0.4, 0.7), 1.0);
        List<Observation> obs = List.of(measurement(typeA));

        // Act
        boolean result = strategy.evaluate(r, obs);

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_onlyOneArgumentPresent_weightMeetsThreshold_returnsTrue() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(1.5, 0.3), 1.0);
        List<Observation> obs = List.of(measurement(typeA));

        // Act
        boolean result = strategy.evaluate(r, obs);

        // Assert
        assertTrue(result);
    }

    @Test
    void evaluate_noObservations_returnsFalse() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(0.6, 0.5), 1.0);

        // Act
        boolean result = strategy.evaluate(r, List.of());

        // Assert
        assertFalse(result);
    }

    @Test
    void evaluate_weightsSumExactlyAtThreshold_returnsTrue() {
        // Arrange
        AssociativeFunction r = rule(List.of(typeA, typeB), List.of(0.5, 0.5), 1.0);
        List<Observation> obs = List.of(measurement(typeA), measurement(typeB));

        // Act
        boolean result = strategy.evaluate(r, obs);

        // Assert
        assertTrue(result);
    }
}