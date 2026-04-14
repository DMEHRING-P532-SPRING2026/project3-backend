package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
