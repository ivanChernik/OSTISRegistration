package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface TranslationService {

    void createTranslitSysIndf(Participant participant);

    String createTranslitApplicationNameOfArticle(Application application);
}
