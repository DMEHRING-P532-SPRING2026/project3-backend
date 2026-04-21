package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.*;
import iu.devinmehringer.project3.command.*;
import iu.devinmehringer.project3.controller.dto.*;
import iu.devinmehringer.project3.controller.exception.*;
import iu.devinmehringer.project3.log.ObservationEvent;
import iu.devinmehringer.project3.manager.processor.ObservationProcessor;
import iu.devinmehringer.project3.model.log.AuditType;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import iu.devinmehringer.project3.model.observation.AssociativeFunction;
import iu.devinmehringer.project3.model.observation.Kind;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import iu.devinmehringer.project3.model.observation.Protocol;
import iu.devinmehringer.project3.model.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationManager {
    private final PhenomenonTypeAccess phenomenonTypeAccess;
    private final ProtocolAccess protocolAccess;
    private final ObservationAccess observationAccess;
    private final PhenomenonAccess phenomenonAccess;
    private final PatientAccess patientAccess;
    private final AssociativeFunctionAccess associativeFunctionAccess;
    private final CommandRunner commandRunner;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObservationProcessor observationProcessor;
    private final CommandLogAccess commandLogAccess;

    public ObservationManager(PhenomenonTypeAccess phenomenonTypeAccess,
                              ProtocolAccess protocolAccess,
                              ObservationAccess observationAccess,
                              PatientAccess patientAccess,
                              CommandRunner commandRunner,
                              PhenomenonAccess phenomenonAccess,
                              AssociativeFunctionAccess associativeFunctionAccess,
                              ApplicationEventPublisher applicationEventPublisher,
                              ObservationProcessor observationProcessor,
                              CommandLogAccess commandLogAccess) {
        this.phenomenonTypeAccess = phenomenonTypeAccess;
        this.protocolAccess = protocolAccess;
        this.observationAccess = observationAccess;
        this.patientAccess = patientAccess;
        this.commandRunner = commandRunner;
        this.phenomenonAccess = phenomenonAccess;
        this.associativeFunctionAccess = associativeFunctionAccess;
        this.applicationEventPublisher = applicationEventPublisher;
        this.observationProcessor = observationProcessor;
        this.commandLogAccess = commandLogAccess;
    }

    public void createPhenomenonType(PhenomenonTypeRequest request) {
        validatePhenomenonRequest(request);
        commandRunner.execute(new CreatePhenomenonTypeCommand(request, phenomenonTypeAccess),
                request.getPerformedBy());
    }

    public List<PhenomenonType> getPhenomenonTypes() {
        return phenomenonTypeAccess.getPhenomenonTypes();
    }

    public void updatePhenomenonType(Long id, PhenomenonTypeRequest request) {
        validatePhenomenonRequest(request);
        commandRunner.execute(new UpdatePhenomenonTypeCommand(id, request, phenomenonTypeAccess),
                request.getPerformedBy());
    }

    public void validatePhenomenonRequest(PhenomenonTypeRequest request) {
        if (request.getName() == null) {
            throw new InvalidCreatePhenomenonRequestException("Name is required");
        }
        if (request.getKind() == null) {
            throw new InvalidCreatePhenomenonRequestException("Kind is required");
        }
        if (request.getKind().equals(Kind.QUANTITATIVE) && request.getAllowedUnits().isEmpty()) {
            throw new InvalidCreatePhenomenonRequestException("Allowed units is empty for quantitative request");
        }
        if (request.getKind().equals(Kind.QUALITATIVE) && request.getPhenomena().isEmpty()) {
            throw new InvalidCreatePhenomenonRequestException("Phenomena is empty for qualitative request");
        }
    }

    public void createProtocol(ProtocolRequest request) {
        validateProtocolRequest(request);
        commandRunner.execute(new CreateProtocolCommand(request, protocolAccess),
                request.getPerformedBy());
    }

    public List<Protocol> getProtocols() {
        return protocolAccess.getProtocols();
    }

    public void updateProtocol(Long id, ProtocolRequest request) {
        validateProtocolRequest(request);
        commandRunner.execute(new UpdateProtocolCommand(id, request, protocolAccess),
                request.getPerformedBy());
    }

    public void validateProtocolRequest(ProtocolRequest request) {
        if (request.getName() == null) {
            throw new InvalidCreateProtocolException("Name is required");
        }
        if (request.getDescription() == null) {
            throw new InvalidCreateProtocolException("Description is required");
        }
        if (request.getAccuracyRating() == null) {
            throw new InvalidCreateProtocolException("Accuracy rating is required");
        }
    }

    public void createObservation(ObservationRequest request) {
        ObservationRequest processed = observationProcessor.process(request);
        CreateObservationCommand command = new CreateObservationCommand(
                processed, observationAccess, patientAccess,
                protocolAccess, phenomenonTypeAccess, phenomenonAccess
        );
        commandRunner.execute(command, request.getPerformedBy());
        applicationEventPublisher.publishEvent(new ObservationEvent(command.getObservation(), AuditType.CREATE));
    }

    public void rejectObservation(Long id, RejectObservationRequest request) {
        RejectObservationCommand command = new RejectObservationCommand(
                id, request, observationAccess
        );
        commandRunner.execute(command, request.getPerformedBy());
        applicationEventPublisher.publishEvent(new ObservationEvent(command.getObservation(), AuditType.REJECT));
    }

    public List<AssociativeFunction> getAssociativeFunctions() {
        return associativeFunctionAccess.getAll();
    }

    public void setStrategyAssociativeFunction(Long id, AssociativeFunctionRequest request) {
        AssociativeFunction associativeFunction = associativeFunctionAccess.getById(id)
                .orElseThrow(() -> new AssociativeFunctionNotFoundException("Associative function not found with id: " + id));
        associativeFunction.setDiagnosisStrategyType(request.getDiagnosisStrategyType());
        associativeFunctionAccess.save(associativeFunction);
    }

    public void undo(Long commandLogId, User requestingUser) {
        commandRunner.undo(commandLogId, requestingUser);
    }

    public void createPhenomenon(PhenomenonRequest request) {
        commandRunner.execute(
                new CreatePhenomenonCommand(request, phenomenonAccess, phenomenonTypeAccess),
                request.getPerformedBy()
        );
    }

    public List<PhenomenonResponse> getPhenomena() {
        return phenomenonAccess.getAll().stream()
                .map(p -> new PhenomenonResponse(p.getId(), p.getName(), p.getPhenomenonType().getId()))
                .collect(Collectors.toList());
    }
}