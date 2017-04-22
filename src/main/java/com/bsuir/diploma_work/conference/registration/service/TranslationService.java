package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedParticipant;

public interface TranslationService {

    void createTranslitSysIndf(Participant participant);

    TranslitedParticipant createTranslitedParticipant(Participant participant);
}
