package com.bsuir.diploma_work.conference.registration.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorkingPlace extends Entity {


    @NotNull
    @Size(max = 100)
    private String country;

    @NotNull
    @Size(max = 100)
    private String city;

    @NotNull
    @Size(max = 250)
    private String organization;

    @NotNull
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
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", organization='" + organization + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
