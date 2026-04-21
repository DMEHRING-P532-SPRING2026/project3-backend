package iu.devinmehringer.project3.engine;

import iu.devinmehringer.project3.model.observation.DiagnosisStrategyType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StrategyConfig {

    @Bean
    public Map<DiagnosisStrategyType, DiagnosisStrategy> strategies(
            SimpleConjunctiveStrategy conjunctive,
            WeightedScoringStrategy weighted) {
        Map<DiagnosisStrategyType, DiagnosisStrategy> map = new HashMap<>();
        map.put(DiagnosisStrategyType.CONJUNCTIVE, conjunctive);
        map.put(DiagnosisStrategyType.WEIGHTED, weighted);
        return map;
    }
}