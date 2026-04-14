package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.observation.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
}
