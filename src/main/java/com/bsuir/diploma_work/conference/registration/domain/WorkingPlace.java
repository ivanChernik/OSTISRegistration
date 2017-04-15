package com.bsuir.diploma_work.conference.registration.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class WorkingPlace implements Serializable {

    @NotNull
    @Size(max = 100)
    @Column(name = "county")
    private String country;

    @NotNull
    @Size(max = 100)
    @Column(name = "city")
    private String city;

    @NotNull
    @Size(max = 250)
    @Column(name = "organization")
    private String organization;

    @NotNull
    @Column(name = "position")
    private String position;

    public WorkingPlace() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "WorkingPlace{" +
                ", country='" + this.country + '\'' +
                ", city='" + this.city + '\'' +
                ", organization='" + this.organization + '\'' +
                ", position='" + this.position + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkingPlace that = (WorkingPlace) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
