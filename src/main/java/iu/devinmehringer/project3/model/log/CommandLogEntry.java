package iu.devinmehringer.project3.model.log;

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
    private String user;

    public CommandLogEntry(CommandType commandType, String payload, LocalDateTime executedAt, String user) {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
