package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.access.AuditLogAccess;
import iu.devinmehringer.project3.model.log.AuditLogEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit-log")
public class AuditLogController {

    private final AuditLogAccess auditLogAccess;

    public AuditLogController(AuditLogAccess auditLogAccess) {
        this.auditLogAccess = auditLogAccess;
    }

    @GetMapping
    public ResponseEntity<List<AuditLogEntry>> getAuditLog() {
        return ResponseEntity.ok(auditLogAccess.getAll());
    }
}