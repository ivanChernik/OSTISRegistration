package com.bsuir.diploma_work.conference.registration.service;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface ParticipantService {

    void checkExistingApplication(Application application);

    Participant getExistingParticipantByNameAndEmail(Participant participant);

    void saveParticipantAndApplication(Participant participant);

    void saveApplication(Application application);

}
