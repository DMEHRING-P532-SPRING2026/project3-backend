package iu.devinmehringer.project3.model.log;

import iu.devinmehringer.project3.model.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "command_log")
public class CommandLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CommandType commandType;

    private String payload;
    private LocalDateTime executedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "observation_id")
    private Long observationId;

    private boolean isUndone = false;

    public CommandLogEntry(CommandType commandType, String payload, LocalDateTime executedAt, User user) {
        this.commandType = commandType;
        this.payload = payload;
        this.executedAt = executedAt;
        this.user = user;
    }

    public CommandLogEntry() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public boolean isUndone() {
        return isUndone;
    }

    public void setUndone(boolean undone) {
        isUndone = undone;
    }
}