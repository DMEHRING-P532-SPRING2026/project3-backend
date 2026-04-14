package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.log.CommandLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandLogEntryRepository extends JpaRepository<CommandLogEntry, Long> {
}
