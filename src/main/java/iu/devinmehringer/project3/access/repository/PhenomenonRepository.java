package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.Phenomenon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhenomenonRepository extends JpaRepository<Phenomenon, Long> {
}
