package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.*;
import iu.devinmehringer.project3.controller.dto.CategoryObservationRequest;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.exception.*;
import iu.devinmehringer.project3.manager.ObservationFactory;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;

public class CreateObservationCommand implements Command {
    private final ObservationRequest request;
    private final ObservationAccess observationAccess;
    private final PatientAccess patientAccess;
    private final ProtocolAccess protocolAccess;
    private final PhenomenonTypeAccess phenomenonTypeAccess;
    private final PhenomenonAccess phenomenonAccess;
    private Observation savedObservation;
    private final Long observationId;

    // normal constructor
    public CreateObservationCommand(ObservationRequest request,
                                    ObservationAccess observationAccess,
                                    PatientAccess patientAccess,
                                    ProtocolAccess protocolAccess,
                                    PhenomenonTypeAccess phenomenonTypeAccess,
                                    PhenomenonAccess phenomenonAccess) {
        this.request = request;
        this.observationAccess = observationAccess;
        this.patientAccess = patientAccess;
        this.protocolAccess = protocolAccess;
        this.phenomenonTypeAccess = phenomenonTypeAccess;
        this.phenomenonAccess = phenomenonAccess;
        this.observationId = null;
    }

    public CreateObservationCommand(ObservationAccess observationAccess, Long observationId) {
        this.request = null;
        this.observationAccess = observationAccess;
        this.observationId = observationId;
        this.patientAccess = null;
        this.protocolAccess = null;
        this.phenomenonTypeAccess = null;
        this.phenomenonAccess = null;
    }

    @Override
    public void execute() {
        Patient patient = patientAccess.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Id not found: " + request.getPatientId()));

        Protocol protocol = request.getProtocolId() != null
                ? protocolAccess.getById(request.getProtocolId())
                  .orElseThrow(() -> new ProtocolNotFoundException("Id not found: " + request.getProtocolId()))
                : null;

        PhenomenonType phenomenonType = null;
        Phenomenon phenomenon = null;

        if (request instanceof MeasurementRequest m) {
            phenomenonType = phenomenonTypeAccess.getById(m.getPhenomenonTypeId())
                    .orElseThrow(() -> new PhenomenonTypeNotFoundException("Id not found: " + m.getPhenomenonTypeId()));
        } else if (request instanceof CategoryObservationRequest c) {
            phenomenon = phenomenonAccess.getById(c.getPhenomenonId())
                    .orElseThrow(() -> new PhenomenonNotFoundException("Id not found: " + c.getPhenomenonId()));
        }

        Observation observation = ObservationFactory.create(
                request, patient, protocol, phenomenonType, phenomenon,
                ObservationSource.MANUAL);
        savedObservation = observationAccess.save(observation);
    }

    @Override
    public void undo() {
        Long idToUndo = observationId != null ? observationId : savedObservation.getId();
        Observation obs = observationAccess.findByID(idToUndo)
                .orElseThrow(() -> new ObservationNotFoundException("Observation not found: " + idToUndo));
        obs.setStatus(Status.REJECTED);
        obs.setRejectionReason("Undone by user");
        observationAccess.save(obs);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE_OBSERVATION;
    }

    @Override
    public Object getPayload() {
        return request;
    }

    public Observation getObservation() {
        return savedObservation;
    }
}