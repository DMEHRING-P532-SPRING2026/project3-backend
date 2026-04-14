package iu.devinmehringer.project3.model.observation;

import iu.devinmehringer.project3.model.patient.Patient;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime recordedAt;
    private LocalDateTime applicableAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "rejected_by_id")
    private Observation rejectedBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "protocol_id", nullable = true)
    private Protocol protocol;

    public Observation(Patient patient, LocalDateTime recordedAt, LocalDateTime applicableAt, Status status, String rejectionReason, Observation rejectedBy, Protocol protocol) {
        this.patient = patient;
        this.recordedAt = recordedAt;
        this.applicableAt = applicableAt;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.rejectedBy = rejectedBy;
        this.protocol = protocol;
    }

    public Observation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public LocalDateTime getApplicableAt() {
        return applicableAt;
    }

    public void setApplicableAt(LocalDateTime applicableAt) {
        this.applicableAt = applicableAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Observation getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(Observation rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
