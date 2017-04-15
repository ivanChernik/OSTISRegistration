package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.WorkingPlace;
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
    private static final String DD_MM_YYYY = "dd_MM_yyyy";
    private static final String SCS = ".scs";

    private static final String APPLICATION_TEMPLATE_FILE = "templates/ostis_application.vm";
    private static final String PARTICIPANT_TEMPLATE_FILE = "templates/ostis_participant.vm";


    @Value("${scs.destination.application.path}")
    private String destinationPathApplicationSCS;

    @Value("${scs.destination.participant.path}")
    private String destinationPathParticipantSCS;

    @Value("${conference.year}")
    private int conferenceYear;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void generateApplication(Participant participant, String tanslitedNameOfArticle) {

        String fileBody = generateFileBody(APPLICATION_TEMPLATE_FILE,
                generateApplicationModel(participant, tanslitedNameOfArticle));

        createFile(fileBody, generateFileNameApplication(participant, tanslitedNameOfArticle));
    }


    private Map<String, Object> generateApplicationModel(Participant participant, String tanslitedNameOfArticle) {
        Map<String, Object> model = new HashedMap();

        model.put("tanslited_name_of_article", tanslitedNameOfArticle);

        model.put("conference_year", conferenceYear);
        model.put("creation_date", getFormattedTodayDate());
        model.put("sys_indf_of_participant", participant.getSysIndf());
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

        Application application = participant.getApplicationList().get(0);

        model.put("date", application.getCreationDate());
        model.put("speaker", application.getSpeaker());
        model.put("conference_competition", application.getConferenceCompetition());
        model.put("participation_form", application.getParticipationForm());
        model.put("joint_authors", application.getAuthorsOfArticle());
        model.put("name_of_article", application.getNameOfArticle());

        return model;
    }

    private String generateFileNameApplication(Participant participant, String tanslitedNameOfArticle) {
        String sysIndf = participant.getSysIndf();

        return destinationPathApplicationSCS +
                "application_to_OSTIS_" + conferenceYear + "_"
                + tanslitedNameOfArticle + "_" +
                sysIndf + "_" + getFormattedTodayDate() + ".scs";
    }

    private String getFormattedTodayDate() {
        DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
        return df.format(Calendar.getInstance().getTime());
    }


    @Override
    public void generateParticipant(Participant participant) {
        String fileBody = generateFileBody(PARTICIPANT_TEMPLATE_FILE,
                generateParticipantModel(participant));

        createFile(fileBody, generateFileNameParticipant(participant.getSysIndf()));
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

    private String generateFileNameParticipant(String sysIndf) {
        return destinationPathParticipantSCS + sysIndf + SCS;
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
