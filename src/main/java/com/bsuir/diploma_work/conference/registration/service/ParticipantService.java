package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface ParticipantService {

    void saveParticipant(Participant participant);

    void saveApplication(Participant participant);

    Participant getUserByNameAndEmail(Participant participant);

}
