package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
}
