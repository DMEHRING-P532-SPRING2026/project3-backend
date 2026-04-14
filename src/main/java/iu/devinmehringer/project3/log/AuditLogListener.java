package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.access.AuditLogAccess;
import iu.devinmehringer.project3.model.log.AuditLogEntry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditLogListener {
    private final AuditLogAccess auditLogAccess;

    public AuditLogListener(AuditLogAccess auditLogAccess) {
        this.auditLogAccess = auditLogAccess;
    }

    @EventListener
    public void onObservationEvent(ObservationEvent event) {
        AuditLogEntry entry = new AuditLogEntry();
        entry.setAuditType(event.eventType());
        entry.setObservationId(event.observation().getId());
        entry.setPatientId(event.observation().getPatient().getId());
        entry.setTimestamp(LocalDateTime.now());
        auditLogAccess.save(entry);
    }
}