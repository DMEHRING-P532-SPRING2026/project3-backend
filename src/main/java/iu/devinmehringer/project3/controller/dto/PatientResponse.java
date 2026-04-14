package iu.devinmehringer.project3.controller.dto;

import java.time.LocalDate;

public class PatientResponse {
    private Long id;
    private String name;
    private LocalDate dob;
    private String note;

    public PatientResponse(Long id, String name, LocalDate dob, String note) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
