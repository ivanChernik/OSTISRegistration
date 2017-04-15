package com.bsuir.diploma_work.conference.registration.util;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ApplyExternalProperty {

    @Value("${conference.year}")
    private int conferenceYear;

    public void applyConferenceYear(Application application){
        application.setConferenceYear(conferenceYear);
    }
}
