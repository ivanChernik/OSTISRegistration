package com.bsuir.diploma_work.conference.registration.domain.tanslit;


public class TranslitedParticipant {

    private String lastName;

    private TranslitedApplication tanslitedApplication;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TranslitedApplication getTanslitedApplication() {
        return tanslitedApplication;
    }

    public void setTanslitedApplication(TranslitedApplication tanslitedApplication) {
        this.tanslitedApplication = tanslitedApplication;
    }
}
