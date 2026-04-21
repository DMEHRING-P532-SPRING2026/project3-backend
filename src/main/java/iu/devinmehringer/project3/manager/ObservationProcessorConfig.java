package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.PhenomenonTypeAccess;
import iu.devinmehringer.project3.manager.processor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationProcessorConfig {

    @Bean
    public ObservationProcessor observationProcessor(PhenomenonTypeAccess phenomenonTypeAccess) {
        return new AuditStampingDecorator(
                new AnomalyFlaggingDecorator(
                        new UnitValidationDecorator(
                                new BaseObservationProcessor(),
                                phenomenonTypeAccess
                        ),
                        phenomenonTypeAccess
                )
        );
    }
}