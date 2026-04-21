package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.Phenomenon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhenomenonRepository extends JpaRepository<Phenomenon, Long> {
    List<Phenomenon> findByParentConcept(Phenomenon parentConcept);
}
