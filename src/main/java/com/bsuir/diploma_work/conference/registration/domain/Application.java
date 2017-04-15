package com.bsuir.diploma_work.conference.registration.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "name_of_article")
    private String nameOfArticle;

    @NotNull
    @Size(max = 300)
    @Column(name = "authors_of_article")
    private String authorsOfArticle;

    @NotNull
    @Column(name = "participation_form")
    private String participationForm;

    @NotNull
    @Size(max = 250)
    @Column(name = "speaker")
    private String speaker;

    @NotNull
    @Column(name = "conference_competition")
    private String conferenceCompetition;

    
    @Column(name = "conference_year")
    private int conferenceYear;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participant")
    private Participant participant;

    /*    @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
    @Column(name = "creation_time")
    private Date creationDate;

    public Application() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getConferenceYear() {
        return conferenceYear;
    }

    public void setConferenceYear(int conferenceYear) {
        this.conferenceYear = conferenceYear;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + this.id +
                ", nameOfArticle='" + this.nameOfArticle + '\'' +
                ", authorsOfArticle='" + this.authorsOfArticle + '\'' +
                ", participationForm='" + this.participationForm + '\'' +
                ", speaker='" + this.speaker + '\'' +
                ", conferenceCompetition='" + this.conferenceCompetition + '\'' +
                ", conferenceYear=" + this.conferenceYear +
                ", creationDate=" + this.creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (id != that.id) return false;
        if (conferenceYear != that.conferenceYear) return false;
        if (nameOfArticle != null ? !nameOfArticle.equals(that.nameOfArticle) : that.nameOfArticle != null)
            return false;
        if (authorsOfArticle != null ? !authorsOfArticle.equals(that.authorsOfArticle) : that.authorsOfArticle != null)
            return false;
        if (participationForm != null ? !participationForm.equals(that.participationForm) : that.participationForm != null)
            return false;
        if (speaker != null ? !speaker.equals(that.speaker) : that.speaker != null) return false;
        if (conferenceCompetition != null ? !conferenceCompetition.equals(that.conferenceCompetition) : that.conferenceCompetition != null)
            return false;
        if (participant != null ? !participant.equals(that.participant) : that.participant != null) return false;
        return creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nameOfArticle != null ? nameOfArticle.hashCode() : 0);
        result = 31 * result + (authorsOfArticle != null ? authorsOfArticle.hashCode() : 0);
        result = 31 * result + (participationForm != null ? participationForm.hashCode() : 0);
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        result = 31 * result + (conferenceCompetition != null ? conferenceCompetition.hashCode() : 0);
        result = 31 * result + conferenceYear;
        result = 31 * result + (participant != null ? participant.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
