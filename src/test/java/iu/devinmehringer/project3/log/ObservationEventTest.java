package iu.devinmehringer.project3.log;

import iu.devinmehringer.project3.access.*;
import iu.devinmehringer.project3.command.Command;
import iu.devinmehringer.project3.controller.dto.MeasurementRequest;
import iu.devinmehringer.project3.controller.dto.RejectObservationRequest;
import iu.devinmehringer.project3.manager.CommandRunner;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.processor.ObservationProcessor;
import iu.devinmehringer.project3.model.log.AuditType;
import iu.devinmehringer.project3.model.observation.*;
import iu.devinmehringer.project3.model.patient.Patient;
import iu.devinmehringer.project3.model.user.Role;
import iu.devinmehringer.project3.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObservationEventTest {

    @Mock private PatientAccess patientAccess;
    @Mock private ProtocolAccess protocolAccess;
    @Mock private PhenomenonTypeAccess phenomenonTypeAccess;
    @Mock private PhenomenonAccess phenomenonAccess;
    @Mock private ObservationAccess observationAccess;
    @Mock private AssociativeFunctionAccess associativeFunctionAccess;
    @Mock private CommandRunner commandRunner;
    @Mock private ApplicationEventPublisher eventPublisher;
    @Mock private ObservationProcessor observationProcessor;
    @Mock private CommandLogAccess commandLogAccess;

    @InjectMocks
    private ObservationManager observationManager;

    private Patient patient;
    private PhenomenonType phenomenonType;
    private Measurement savedMeasurement;
    private MeasurementRequest request;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test", Role.CLINICIAN);

        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Smith");

        phenomenonType = new PhenomenonType();
        phenomenonType.setId(1L);
        phenomenonType.setName("Body Temperature");
        phenomenonType.setKind(Kind.QUANTITATIVE);
        phenomenonType.setAllowedUnits(List.of("celsius"));

        savedMeasurement = new Measurement();
        savedMeasurement.setId(1L);
        savedMeasurement.setPatient(patient);
        savedMeasurement.setStatus(Status.ACTIVE);
        savedMeasurement.setPhenomenonType(phenomenonType);
        savedMeasurement.setAmount(new BigDecimal("37.5"));
        savedMeasurement.setUnit("celsius");
        savedMeasurement.setRecordedAt(LocalDateTime.now());
        savedMeasurement.setApplicableAt(LocalDateTime.now());

        request = new MeasurementRequest();
        request.setPatientId(1L);
        request.setPhenomenonTypeId(1L);
        request.setAmount(new BigDecimal("37.5"));
        request.setUnit("celsius");
        request.setApplicableAt(LocalDateTime.now());
        request.setPerformedBy(testUser);
    }

    @Test
    void createObservation_validRequest_publishesCreateEvent() {
        // Arrange
        when(observationProcessor.process(any())).thenAnswer(i -> i.getArgument(0));
        doAnswer(invocation -> {
            Command command = invocation.getArgument(0);
            command.execute();
            return null;
        }).when(commandRunner).execute(any(), any());
        when(patientAccess.findById(1L)).thenReturn(Optional.of(patient));
        when(phenomenonTypeAccess.getById(1L)).thenReturn(Optional.of(phenomenonType));
        when(observationAccess.save(any())).thenReturn(savedMeasurement);

        // Act
        observationManager.createObservation(request);

        // Assert
        ArgumentCaptor<ObservationEvent> captor = ArgumentCaptor.forClass(ObservationEvent.class);
        verify(eventPublisher, times(1)).publishEvent(captor.capture());
        assertEquals(AuditType.CREATE, captor.getValue().eventType());
        assertEquals(1L, captor.getValue().observation().getId());
    }

    @Test
    void rejectObservation_validId_publishesRejectEvent() {
        // Arrange
        RejectObservationRequest rejectRequest = new RejectObservationRequest();
        rejectRequest.setPerformedBy(testUser);
        doAnswer(invocation -> {
            Command command = invocation.getArgument(0);
            command.execute();
            return null;
        }).when(commandRunner).execute(any(), any());
        when(observationAccess.findByID(1L)).thenReturn(Optional.of(savedMeasurement));
        when(observationAccess.save(any())).thenReturn(savedMeasurement);

        // Act
        observationManager.rejectObservation(1L, rejectRequest);

        // Assert
        ArgumentCaptor<ObservationEvent> captor = ArgumentCaptor.forClass(ObservationEvent.class);
        verify(eventPublisher, times(1)).publishEvent(captor.capture());
        assertEquals(AuditType.REJECT, captor.getValue().eventType());
        assertEquals(1L, captor.getValue().observation().getId());
    }
}