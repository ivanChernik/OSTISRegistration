package com.bsuir.diploma_work.conference.registration.dao.impl;


import com.bsuir.diploma_work.conference.registration.dao.ParticipantDao;
import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.exception.dao.EntryNotFoundException;
import com.bsuir.diploma_work.conference.registration.exception.dao.EntryWasFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Calendar;

import static org.slf4j.LoggerFactory.getLogger;

@Repository("participantDao")
public class ParticipantDaoImpl implements ParticipantDao {

    private static final Logger logger = getLogger(ParticipantDaoImpl.class);

    private static final String SEARCH_EXISTING_APPLICATION_HQL =
            "FROM  com.bsuir.diploma_work.conference.registration.domain.Application A " +
                    "WHERE A.nameOfArticle= :nameOfArticle " +
                    "AND A.authorsOfArticle = :authorsOfArticle " +
                    "AND A.participationForm = :participationForm " +
                    "AND A.speaker = :speaker " +
                    "AND A.conferenceCompetition = :conferenceCompetition " +
                    "AND A.conferenceYear = :conferenceYear ";

    private static final String SEARCH_EXISTING_PARTICIPANT_HQL =
            "FROM  com.bsuir.diploma_work.conference.registration.domain.Participant P " +
                    "WHERE P.firstName= :firstName " +
                    "AND P.lastName = :lastName " +
                    "AND P.email = :email ";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void searchExistingApplicationWithoutDate(Application application) {

        logger.debug("Search application : {}", application);

        Query searchQuery = sessionFactory.getCurrentSession().createQuery(SEARCH_EXISTING_APPLICATION_HQL);
        searchQuery.setParameter("nameOfArticle", application.getNameOfArticle());
        searchQuery.setParameter("participationForm", application.getParticipationForm());
        searchQuery.setParameter("authorsOfArticle", application.getAuthorsOfArticle());
        searchQuery.setParameter("speaker", application.getSpeaker());
        searchQuery.setParameter("conferenceCompetition", application.getConferenceCompetition());
        searchQuery.setParameter("conferenceYear", application.getConferenceYear());

        if (!searchQuery.list().isEmpty()) {
            throw new EntryWasFoundException();
        }

    }


    @Override
    public Participant searchExistingParticipantByNameAndEmail(Participant participant) {

        Query searchQuery = sessionFactory.getCurrentSession().createQuery(SEARCH_EXISTING_PARTICIPANT_HQL);
        searchQuery.setParameter("firstName", participant.getFirstName());
        searchQuery.setParameter("lastName", participant.getLastName());
        searchQuery.setParameter("email", participant.getEmail());

        if (searchQuery.list().isEmpty()) {
            throw new EntryNotFoundException();
        }

        Participant foundParticipant = (Participant) searchQuery.list().get(0);
        logger.debug("Participant was found: {}", foundParticipant);
        return foundParticipant;
    }

    @Override
    public void saveParticipantAndApplication(Participant participant) {
        setApplicationDateNow(participant);
        sessionFactory.getCurrentSession().save(participant);
        logger.debug("Participant was saved: {}", participant);
    }

    @Override
    public void saveApplication(Application application) {
        application.setCreationDate(getNow());
        sessionFactory.getCurrentSession().saveOrUpdate(application);

        logger.debug("Application was saved: {}", application);
    }

    private Date getNow() {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    private void setApplicationDateNow(Participant participant) {
        participant.getApplicationList().get(0).setCreationDate(getNow());
    }
}
