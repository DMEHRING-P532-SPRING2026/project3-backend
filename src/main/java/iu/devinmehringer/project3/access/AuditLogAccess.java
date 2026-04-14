package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.AuditLogEntryRepository;
import iu.devinmehringer.project3.model.log.AuditLogEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogAccess {
    private final AuditLogEntryRepository auditLogEntryRepository;

    public AuditLogAccess(AuditLogEntryRepository auditLogEntryRepository) {
        this.auditLogEntryRepository = auditLogEntryRepository;
    }

    public void save(AuditLogEntry auditLogEntry) {
        this.auditLogEntryRepository.save(auditLogEntry);
    }

    public List<AuditLogEntry> getAll() {
        return auditLogEntryRepository.findAll();
    }
}
