package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.CategoryObservation;
import iu.devinmehringer.project3.model.observation.Phenomenon;
import iu.devinmehringer.project3.model.observation.Status;
import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryObservationRepository extends JpaRepository<CategoryObservation, Long> {
    List<CategoryObservation> findByPatientAndStatusAndPhenomenon(Patient patient, Status status, Phenomenon phenomenon);
}
