package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.CategoryObservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryObservationRepository extends JpaRepository<CategoryObservation, Long> {
}
