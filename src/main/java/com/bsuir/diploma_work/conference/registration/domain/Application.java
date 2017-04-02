package com.bsuir.diploma_work.conference.registration.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Application extends Entity {

    @NotNull
    @Size(max = 250)
    private String nameOfArticle;

    @NotNull
    @Size(max = 300)
    private String authorsOfArticle;

    @NotNull
    private String participationForm;

    @NotNull
    @Size(max = 250)
    private String speaker;

    @NotNull
    private String conferenceCompetition;

    private LocalDateTime creationTime;

    public Application() {

    }

    public String getNameOfArticle() {
        return nameOfArticle;
    }

    public void setNameOfArticle(String nameOfArticle) {
        this.nameOfArticle = nameOfArticle;
    }

    public String getAuthorsOfArticle() {
        return authorsOfArticle;
    }

    public void setAuthorsOfArticle(String authorsOfArticle) {
        this.authorsOfArticle = authorsOfArticle;
    }

    public String getParticipationForm() {
        return participationForm;
    }

    public void setParticipationForm(String participationForm) {
        this.participationForm = participationForm;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getConferenceCompetition() {
        return conferenceCompetition;
    }

    public void setConferenceCompetition(String conferenceCompetition) {
        this.conferenceCompetition = conferenceCompetition;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Application{" +
                "nameOfArticle='" + nameOfArticle + '\'' +
                ", authorsOfArticle='" + authorsOfArticle + '\'' +
                ", participationForm='" + participationForm + '\'' +
                ", speaker='" + speaker + '\'' +
                ", conferenceCompetition='" + conferenceCompetition + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
