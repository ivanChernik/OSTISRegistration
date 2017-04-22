package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedParticipant;

public interface SCSGenerationService {

    void generateApplication(Participant participant, TranslitedParticipant translitedParticipant);

    void generateParticipant(Participant participant);

}
