package com.bsuir.diploma_work.conference.registration.dao;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface ParticipantDao {

    void searchExistingApplicationWithoutDate(Application application);

    Participant searchExistingParticipantByNameAndEmail(Participant participant);

    void saveParticipantAndApplication(Participant participant);

    void saveApplication(Application application);
}
