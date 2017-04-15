package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.dao.ParticipantDao;
import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.exception.dao.EntryNotFoundException;
import com.bsuir.diploma_work.conference.registration.exception.dao.EntryWasFoundException;
import com.bsuir.diploma_work.conference.registration.exception.service.ApplicationAlreadyExistsException;
import com.bsuir.diploma_work.conference.registration.service.ParticipantService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

@Service("ParticipantService")
public class ParticipantServiceImpl implements ParticipantService {

    private static final Logger logger = getLogger(ParticipantServiceImpl.class);

    @Autowired
    private ParticipantDao participantDao;

    @Override
    @Transactional
    public void checkExistingApplication(Application application) {

        try {
            participantDao
                    .searchExistingApplicationWithoutDate(application);
        } catch (EntryWasFoundException e) {
            logger.warn("Application already exists:{}", application);
            throw new ApplicationAlreadyExistsException();
        }

    }

    @Override
    @Transactional
    public Participant getExistingParticipantByNameAndEmail(Participant incomingParticipant) {
        Participant foundParticipant = null;
        try {
            foundParticipant = participantDao.searchExistingParticipantByNameAndEmail(incomingParticipant);
            foundParticipant.setNewComer(FALSE);

            List newApplications = incomingParticipant.getApplicationList();
            foundParticipant.setApplicationList(newApplications);
        } catch (EntryNotFoundException e) {
            foundParticipant = incomingParticipant;
            foundParticipant.setNewComer(TRUE);
            logger.warn("Participant does not exists :", incomingParticipant);
        }

        return prepareApplication(foundParticipant);
    }

    private Participant prepareApplication(Participant participant) {
        Application application = participant.getApplicationList().get(0);
        application.setParticipant(participant);
        participant.setApplicationList(asList(application));
        return participant;
    }


    @Override
    @Transactional
    public void saveParticipantAndApplication(Participant participant) {
        participantDao.saveParticipantAndApplication(participant);
    }

    @Override
    @Transactional
    public void saveApplication(Application application) {
        participantDao.saveApplication(application);
    }
}
