package com.bsuir.diploma_work.conference.registration.rest;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.exception.service.ApplicationAlreadyExistsException;
import com.bsuir.diploma_work.conference.registration.service.ParticipantService;
import com.bsuir.diploma_work.conference.registration.service.SCSGenerationService;
import com.bsuir.diploma_work.conference.registration.service.TranslationService;
import com.bsuir.diploma_work.conference.registration.util.ApplyExternalProperty;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.bsuir.diploma_work.conference.registration.util.ResponseMessageTemplates.APPLICATION_ALREADY_EXISTS;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ConfApplicationResourse {

    private static final Logger logger = getLogger(ConfApplicationResourse.class);

    @Autowired
    private SCSGenerationService scsGenerationService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ApplyExternalProperty applyExternalProperty;

    @CrossOrigin()
    @RequestMapping(value = "/generate", method = POST, consumes = "application/json")
    public String generateApplication(@Valid @RequestBody Participant participant) {

        logger.debug("Received participant: {}", participant);

        Application application = getApplication(participant);

        try {
            applyExternalProperty.applyConferenceYear(application);
            participantService.checkExistingApplication(application);
        } catch (ApplicationAlreadyExistsException e) {
            return APPLICATION_ALREADY_EXISTS;
        }

        processApplicationAndParticipant(participant);

        return "welcome";
    }

    private void processApplicationAndParticipant(Participant participant) {

        Participant checkedParticipant = participantService
                .getExistingParticipantByNameAndEmail(participant);

        Application application = getApplication(checkedParticipant);

        if (checkedParticipant.isNewComer()) {
            translationService.createTranslitSysIndf(checkedParticipant);
            participantService.saveParticipantAndApplication(checkedParticipant);
            scsGenerationService.generateParticipant(checkedParticipant);
        } else {
            participantService.saveApplication(application);
        }

        String tanslitedNameOfArticle = translationService
                .createTranslitApplicationNameOfArticle(application);
        scsGenerationService.generateApplication(checkedParticipant, tanslitedNameOfArticle);
    }

    private Application getApplication(Participant participant) {
        return participant.getApplicationList().get(0);
    }


}
