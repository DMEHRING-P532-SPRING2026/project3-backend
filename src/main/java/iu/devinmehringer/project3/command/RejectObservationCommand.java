package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.ObservationAccess;
import iu.devinmehringer.project3.controller.dto.RejectObservationRequest;
import iu.devinmehringer.project3.controller.exception.ObservationNotFoundException;
import iu.devinmehringer.project3.log.ObservationEvent;
import iu.devinmehringer.project3.model.log.AuditType;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.Observation;
import iu.devinmehringer.project3.model.observation.Status;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

public class RejectObservationCommand implements Command {
    private final Long id;
    private final RejectObservationRequest request;
    private final ObservationAccess observationAccess;
    private Observation savedObservation;

    public RejectObservationCommand(Long id, RejectObservationRequest request,
                                    ObservationAccess observationAccess) {
        this.id = id;
        this.request = request;
        this.observationAccess = observationAccess;
    }

    @Transactional
    @Override
    public void execute() {
        Observation observation = observationAccess.findByID(id)
                .orElseThrow(() -> new ObservationNotFoundException("Id not found: " + id));

        observation.setStatus(Status.REJECTED);
        observation.setRejectionReason(request.getReason());

        if (request.getRejectedById() != null) {
            Observation rejectedBy = observationAccess.findByID(request.getRejectedById())
                    .orElseThrow(() -> new ObservationNotFoundException("Id not found: " + request.getRejectedById()));
            observation.setRejectedBy(rejectedBy);
        }

        savedObservation = observationAccess.save(observation);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.REJECT_OBSERVATION;
    }

    @Override
    public Object getPayload() {
        return Map.of(
                "id", id,
                "reason", request.getReason() != null ? request.getReason() : "",
                "rejectedById", request.getRejectedById() != null ? request.getRejectedById() : ""
        );
    }

    public Observation getObservation() {
        return savedObservation;
    }
}