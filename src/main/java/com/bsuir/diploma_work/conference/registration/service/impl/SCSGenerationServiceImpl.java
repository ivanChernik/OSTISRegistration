package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.WorkingPlace;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedParticipant;
import com.bsuir.diploma_work.conference.registration.exception.service.SCSWasNotCreatedException;
import com.bsuir.diploma_work.conference.registration.service.SCSGenerationService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString;

@Service("SCSGenerationService")
public class SCSGenerationServiceImpl implements SCSGenerationService {

    private static final Logger logger = getLogger(SCSGenerationServiceImpl.class);

    private static final String UTF_8 = "utf-8";
    private static final String DD_MM_YYYY = "dd-MM-yyyy";
    private static final String SCS = ".scs";

    private static final String APPLICATION_TEMPLATE_FILE = "templates/scs/ostis_application.vm";
    private static final String NEW_PARTICIPANT_TEMPLATE_FILE = "templates/scs/ostis_new_participant.vm";
    private static final String EXISTING_PARTICIPANT_TEMPLATE_FILE = "templates/scs/ostis_existing_participant.vm";

    @Value("${scs.destination.application.path}")
    private String destinationPathApplicationSCS;

    @Value("${scs.destination.new.participant.path}")
    private String destinationPathNewParticipantSCS;

    @Value("${scs.destination.existing.participant.path}")
    private String destinationPathExistingParticipantSCS;


    @Value("${conference.year}")
    private int conferenceYear;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void generateApplication(Participant participant, TranslitedParticipant translitedParticipant) {

        String fileBody = generateFileBody(APPLICATION_TEMPLATE_FILE,
                generateApplicationModel(participant, translitedParticipant));

        createFile(fileBody, generateFileNameApplication(translitedParticipant));
    }


    private Map<String, Object> generateApplicationModel(Participant participant, TranslitedParticipant translitedParticipant) {
        Map<String, Object> model = new HashedMap();

        model.put("translited_name_of_article",
                translitedParticipant.getTanslitedApplication().getNameOfArticle());

        model.put("conference_year", conferenceYear);
        model.put("creation_date", getFormattedTodayDate());
        model.put("sys_indf_of_participant", participant.getSysIndf());
        model.put("translited_lastname_of_participant", translitedParticipant.getLastName());

        Application application = participant.getApplication();

        model.put("speaker", application.getSpeaker());
        model.put("conference_competition", application.getConferenceCompetition());
        model.put("participation_form", application.getParticipationForm());
        model.put("joint_authors", application.getAuthorsOfArticle());
        model.put("name_of_article", application.getNameOfArticle());

        return model;
    }

    private String generateFileNameApplication(TranslitedParticipant translitedParticipant) {
        String nameOfArticle = translitedParticipant.getTanslitedApplication().getNameOfArticle();

        return destinationPathApplicationSCS +
                "application" + "_" + translitedParticipant.getLastName() + "_" +
                nameOfArticle + SCS;
    }

    private String getFormattedTodayDate() {
        DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
        return df.format(Calendar.getInstance().getTime());
    }


    @Override
    public void generateNewParticipant(Participant participant) {
        logger.debug("Generate new participant");
        String fileBody = generateFileBody(NEW_PARTICIPANT_TEMPLATE_FILE,
                generateParticipantModel(participant));

        createFile(fileBody, generateFileNameParticipant(participant.getSysIndf(),
                destinationPathNewParticipantSCS));
    }

    @Override
    public void generateExistingParticipant(Participant participant) {
        logger.debug("Generate existing participant");
        String fileBody = generateFileBody(EXISTING_PARTICIPANT_TEMPLATE_FILE,
                generateParticipantModel(participant));

        createFile(fileBody, generateFileNameParticipant(participant.getSysIndf(),
                destinationPathExistingParticipantSCS));
    }

    private Map<String, Object> generateParticipantModel(Participant participant) {
        Map<String, Object> model = new HashedMap();

        model.put("sys_idnf", participant.getSysIndf());
        model.put("name", participant.getFirstName());
        model.put("surname", participant.getLastName());
        model.put("middlename", participant.getMiddleName());
        model.put("email", participant.getEmail());
        model.put("academic_degree", participant.getAcademicDegree());
        model.put("academic_title", participant.getAcademicTitle());

        WorkingPlace workingPlace = participant.getWorkingPlace();

        model.put("city", workingPlace.getCity());
        model.put("country", workingPlace.getCountry());
        model.put("organization", workingPlace.getOrganization());
        model.put("position", workingPlace.getPosition());

        return model;
    }


    private String generateFileNameParticipant(String sysIndf, String destinationPath) {
        return destinationPath + sysIndf + SCS;
    }


    private String generateFileBody(String templateLocation, Map<String, Object> model) {

        return mergeTemplateIntoString(velocityEngine, templateLocation, CharEncoding.UTF_8, model);
    }

    private void createFile(String fileBody, String destinationFile) {

        logger.debug("File to create :" + destinationFile);

        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(destinationFile), UTF_8));
            writer.write(fileBody);
        } catch (IOException ex) {
            logger.error("Following file was not created:" + destinationFile, ex);
            throw new SCSWasNotCreatedException();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                logger.warn("Writer was not closed", ex);
            }
        }
    }


}
