package iu.devinmehringer.project3;

import iu.devinmehringer.project3.access.AssociativeFunctionAccess;
import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.access.UserAccess;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.user.Role;
import iu.devinmehringer.project3.model.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class DataSeeder implements CommandLineRunner {

    private final PhenomenonTypeAccess phenomenonTypeAccess;
    private final AssociativeFunctionAccess associativeFunctionAccess;
    private final UserAccess userAccess;

    public DataSeeder(PhenomenonTypeAccess phenomenonTypeAccess,
                      AssociativeFunctionAccess associativeFunctionAccess,
                      UserAccess userAccess) {
        this.phenomenonTypeAccess = phenomenonTypeAccess;
        this.associativeFunctionAccess = associativeFunctionAccess;
        this.userAccess = userAccess;
    }
    @Override
    public void run(String... args) {

        userAccess.save(new User("Adam Smith", Role.CLINICIAN));
        userAccess.save(new User("Jim Jones", Role.CLINICIAN));
        userAccess.save(new User("Admin", Role.ADMIN));

        // --- Body Temperature (quantitative) ---
        PhenomenonType bodyTemp = new PhenomenonType();
        bodyTemp.setName("Body Temperature");
        bodyTemp.setKind(Kind.QUANTITATIVE);
        bodyTemp.setAllowedUnits(List.of("celsius", "fahrenheit"));
        bodyTemp.setNormalMin(new BigDecimal("36.1"));
        bodyTemp.setNormalMax(new BigDecimal("37.2"));
        phenomenonTypeAccess.save(bodyTemp);

        // --- Blood Pressure (quantitative) ---
        PhenomenonType bloodPressure = new PhenomenonType();
        bloodPressure.setName("Blood Pressure");
        bloodPressure.setKind(Kind.QUANTITATIVE);
        bloodPressure.setAllowedUnits(List.of("mmhg"));
        bloodPressure.setNormalMin(new BigDecimal("60.0"));
        bloodPressure.setNormalMax(new BigDecimal("120.0"));
        phenomenonTypeAccess.save(bloodPressure);

        // --- Blood Glucose (quantitative) ---
        PhenomenonType bloodGlucose = new PhenomenonType();
        bloodGlucose.setName("Blood Glucose");
        bloodGlucose.setKind(Kind.QUANTITATIVE);
        bloodGlucose.setAllowedUnits(List.of("mmol/L"));
        bloodGlucose.setNormalMin(new BigDecimal("4.0"));
        bloodGlucose.setNormalMax(new BigDecimal("7.8"));
        phenomenonTypeAccess.save(bloodGlucose);

        // --- Fever Diagnosis (qualitative) ---
        PhenomenonType feverDiagnosis = new PhenomenonType();
        feverDiagnosis.setName("Fever Diagnosis");
        feverDiagnosis.setKind(Kind.QUALITATIVE);

        Phenomenon feverPresent = new Phenomenon();
        feverPresent.setName("Fever Present");
        feverPresent.setPhenomenonType(feverDiagnosis);
        feverDiagnosis.addPhenomenon(feverPresent);

        Phenomenon feverAbsent = new Phenomenon();
        feverAbsent.setName("Fever Absent");
        feverAbsent.setPhenomenonType(feverDiagnosis);
        feverDiagnosis.addPhenomenon(feverAbsent);
        phenomenonTypeAccess.save(feverDiagnosis);

        // --- Diabetes Risk (qualitative) ---
        PhenomenonType diabetesRisk = new PhenomenonType();
        diabetesRisk.setName("Diabetes Risk");
        diabetesRisk.setKind(Kind.QUALITATIVE);

        Phenomenon diabetesHighRisk = new Phenomenon();
        diabetesHighRisk.setName("High Risk");
        diabetesHighRisk.setPhenomenonType(diabetesRisk);
        diabetesRisk.addPhenomenon(diabetesHighRisk);

        Phenomenon diabetesLowRisk = new Phenomenon();
        diabetesLowRisk.setName("Low Risk");
        diabetesLowRisk.setPhenomenonType(diabetesRisk);
        diabetesRisk.addPhenomenon(diabetesLowRisk);
        phenomenonTypeAccess.save(diabetesRisk);

// --- Rule 1: CONJUNCTIVE — body temp + blood pressure -> fever present ---
        AssociativeFunction feverRule = new AssociativeFunction();
        feverRule.setName("High temp and blood pressure indicates fever");
        feverRule.setArgumentConcepts(List.of(bodyTemp, bloodPressure));
        feverRule.setArgumentWeights(List.of(0.5, 0.5));
        feverRule.setThreshold(1.0);
        feverRule.setProductConcept(feverPresent);
        feverRule.setDiagnosisStrategyType(DiagnosisStrategyType.CONJUNCTIVE);
        associativeFunctionAccess.save(feverRule);

// --- Rule 2: WEIGHTED — blood glucose + blood pressure -> diabetes high risk ---
        AssociativeFunction diabetesRule = new AssociativeFunction();
        diabetesRule.setName("Weighted diabetes risk assessment");
        diabetesRule.setArgumentConcepts(List.of(bloodGlucose, bloodPressure));
        diabetesRule.setArgumentWeights(List.of(0.7, 0.3));
        diabetesRule.setThreshold(0.7);
        diabetesRule.setProductConcept(diabetesHighRisk);
        diabetesRule.setDiagnosisStrategyType(DiagnosisStrategyType.WEIGHTED);
        associativeFunctionAccess.save(diabetesRule);

        // --- Symptom Hierarchy (qualitative) for propagation testing ---
        PhenomenonType symptomType = new PhenomenonType();
        symptomType.setName("Symptom Hierarchy");
        symptomType.setKind(Kind.QUALITATIVE);

        Phenomenon illness = new Phenomenon();
        illness.setName("Illness");
        illness.setPhenomenonType(symptomType);
        symptomType.addPhenomenon(illness);

        Phenomenon infection = new Phenomenon();
        infection.setName("Infection");
        infection.setPhenomenonType(symptomType);
        infection.setParentConcept(illness); // infection -> illness
        symptomType.addPhenomenon(infection);

        Phenomenon bacterialInfection = new Phenomenon();
        bacterialInfection.setName("Bacterial Infection");
        bacterialInfection.setPhenomenonType(symptomType);
        bacterialInfection.setParentConcept(infection); // bacterial -> infection -> illness
        symptomType.addPhenomenon(bacterialInfection);

        phenomenonTypeAccess.save(symptomType);
    }
}