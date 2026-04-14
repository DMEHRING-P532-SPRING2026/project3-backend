package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.log.AuditLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogEntryRepository extends JpaRepository<AuditLogEntry, Long> {
}
