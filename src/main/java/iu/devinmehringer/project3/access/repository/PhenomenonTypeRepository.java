package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.PhenomenonType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhenomenonTypeRepository extends JpaRepository<PhenomenonType, Long> {
}
