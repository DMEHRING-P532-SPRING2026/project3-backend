package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
