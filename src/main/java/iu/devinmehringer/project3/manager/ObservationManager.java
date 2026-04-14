package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.*;
import iu.devinmehringer.project3.command.*;
import iu.devinmehringer.project3.controller.dto.ObservationRequest;
import iu.devinmehringer.project3.controller.dto.PhenomenonTypeRequest;
import iu.devinmehringer.project3.controller.dto.ProtocolRequest;
import iu.devinmehringer.project3.controller.dto.RejectObservationRequest;
import iu.devinmehringer.project3.controller.exception.InvalidCreatePhenomenonRequestException;
import iu.devinmehringer.project3.controller.exception.InvalidCreateProtocolException;
import iu.devinmehringer.project3.log.ObservationEvent;
import iu.devinmehringer.project3.model.log.AuditType;
import iu.devinmehringer.project3.model.observation.Kind;
import iu.devinmehringer.project3.model.observation.PhenomenonType;
import iu.devinmehringer.project3.model.observation.Protocol;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationManager {
    private final PhenomenonTypeAccess phenomenonTypeAccess;
    private final ProtocolAccess protocolAccess;
    private final ObservationAccess observationAccess;
    private final PhenomenonAccess phenomenonAccess;
    private final PatientAccess patientAccess;
    private final CommandRunner commandRunner;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ObservationManager(PhenomenonTypeAccess phenomenonTypeAccess,
                              ProtocolAccess protocolAccess,
                              ObservationAccess observationAccess,
                              PatientAccess patientAccess,
                              CommandRunner commandRunner,
                              PhenomenonAccess phenomenonAccess,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.phenomenonTypeAccess = phenomenonTypeAccess;
        this.protocolAccess = protocolAccess;
        this.observationAccess = observationAccess;
        this.patientAccess = patientAccess;
        this.commandRunner = commandRunner;
        this.phenomenonAccess = phenomenonAccess;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void createPhenomenonType(PhenomenonTypeRequest phenomenonTypeRequest) {
        validatePhenomenonRequest(phenomenonTypeRequest);
        commandRunner.execute(new CreatePhenomenonTypeCommand(phenomenonTypeRequest, phenomenonTypeAccess));
    }

    public List<PhenomenonType> getPhenomenonTypes() {
        return phenomenonTypeAccess.getPhenomenonTypes();
    }

    public void updatePhenomenonType(Long id, PhenomenonTypeRequest phenomenonTypeRequest) {
        validatePhenomenonRequest(phenomenonTypeRequest);
        commandRunner.execute(new UpdatePhenomenonTypeCommand(id, phenomenonTypeRequest, phenomenonTypeAccess));
    }

    public void deletePhenomenonType(Long id) {
        commandRunner.execute(new DeletePhenomenonTypeCommand(id, phenomenonTypeAccess));
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

    public void createProtocol(ProtocolRequest protocolRequest) {
        validateProtocolRequest(protocolRequest);
        commandRunner.execute(new CreateProtocolCommand(protocolRequest, protocolAccess));
    }

    public List<Protocol> getProtocols() {
        return protocolAccess.getProtocols();
    }

    public void updateProtocol(Long id, ProtocolRequest protocolRequest) {
        validateProtocolRequest(protocolRequest);
        commandRunner.execute(new UpdateProtocolCommand(id, protocolRequest, protocolAccess));
    }

    public void deleteProtocol(Long id) {
        commandRunner.execute(new DeleteProtocolCommand(id, protocolAccess));
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
        CreateObservationCommand command = new CreateObservationCommand(
                request, observationAccess, patientAccess,
                protocolAccess, phenomenonTypeAccess, phenomenonAccess
        );
        commandRunner.execute(command);
        applicationEventPublisher.publishEvent(new ObservationEvent(command.getObservation(), AuditType.CREATE));
    }

    public void rejectObservation(Long id, RejectObservationRequest request) {
        RejectObservationCommand command = new RejectObservationCommand(
                id, request, observationAccess
        );
        commandRunner.execute(command);
        applicationEventPublisher.publishEvent(new ObservationEvent(command.getObservation(), AuditType.REJECT));
    }

}
