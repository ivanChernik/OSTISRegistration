package com.bsuir.diploma_work.conference.registration.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "participant")
public class Participant implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sys_indf")
    private String sysIndf;

    @NotNull
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 100)
    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "academic_degree")
    private String academicDegree;

    @NotNull
    @Column(name = "academic_title")
    private String academicTitle;

    @NotNull
    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @NotNull
    private WorkingPlace workingPlace;

    @NotNull
    @Transient
    private boolean newComer;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "participant")
    private List<Application> applicationList;

    public Participant() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSysIndf() {
        return sysIndf;
    }

    public void setSysIndf(String sysIndf) {
        this.sysIndf = sysIndf;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WorkingPlace getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(WorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }

    public boolean isNewComer() {
        return newComer;
    }

    public void setNewComer(boolean newComer) {
        this.newComer = newComer;
    }


    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + this.id +
                ", sysIndf='" + this.sysIndf + '\'' +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", middleName='" + this.middleName + '\'' +
                ", academicDegree='" + this.academicDegree + '\'' +
                ", academicTitle='" + this.academicTitle + '\'' +
                ", email='" + this.email + '\'' +
                ", workingPlace=" + this.workingPlace +
                ", newComer=" + this.newComer +
                ", applicationList=" + this.applicationList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (id != that.id) return false;
        if (newComer != that.newComer) return false;
        if (sysIndf != null ? !sysIndf.equals(that.sysIndf) : that.sysIndf != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (academicDegree != null ? !academicDegree.equals(that.academicDegree) : that.academicDegree != null)
            return false;
        if (academicTitle != null ? !academicTitle.equals(that.academicTitle) : that.academicTitle != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (workingPlace != null ? !workingPlace.equals(that.workingPlace) : that.workingPlace != null) return false;
        return applicationList != null ? applicationList.equals(that.applicationList) : that.applicationList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sysIndf != null ? sysIndf.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (academicDegree != null ? academicDegree.hashCode() : 0);
        result = 31 * result + (academicTitle != null ? academicTitle.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (workingPlace != null ? workingPlace.hashCode() : 0);
        result = 31 * result + (newComer ? 1 : 0);
        result = 31 * result + (applicationList != null ? applicationList.hashCode() : 0);
        return result;
    }
}
