package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.access.PatientAccess;
import iu.devinmehringer.project3.controller.dto.PatientRequest;
import iu.devinmehringer.project3.model.log.CommandType;
import iu.devinmehringer.project3.model.patient.Patient;

public class CreatePatientCommand implements Command {
    private final PatientRequest request;
    private final PatientAccess patientAccess;

    public CreatePatientCommand(PatientRequest request, PatientAccess patientAccess) {
        this.request = request;
        this.patientAccess = patientAccess;
    }

    @Override
    public void execute() {
        Patient patient = new Patient(request.getFullName(), request.getDateOfBirth(), request.getNote());
        patientAccess.savePatient(patient);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE_PATIENT;
    }

    @Override
    public Object getPayload() {
        return request;
    }
}
