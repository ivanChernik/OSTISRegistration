package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface SCSGenerationService {

    void generateApplication(Participant participant);

    void generateParticipant(Participant participant);

}
