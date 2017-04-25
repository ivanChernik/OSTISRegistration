package com.bsuir.diploma_work.conference.registration.rest;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedParticipant;
import com.bsuir.diploma_work.conference.registration.service.MailingService;
import com.bsuir.diploma_work.conference.registration.service.SCSGenerationService;
import com.bsuir.diploma_work.conference.registration.service.TranslationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ConfApplicationResourse {

    private static final Logger logger = getLogger(ConfApplicationResourse.class);
    private static final String STATUS_OK = "{\"status\":\"ok\"}";

    @Autowired
    private SCSGenerationService scsGenerationService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private MailingService mailingService;

    @Value("${conference.year}")
    private int conferenceYear;

    @CrossOrigin()
    @RequestMapping(value = "/generate", method = POST, consumes = "application/json")
    public String generateApplication(@Valid @RequestBody Participant participant) {

        logger.debug("Received participant: {}", participant);

        Application application = participant.getApplication();
        application.setConferenceYear(conferenceYear);


        processApplicationAndParticipant(participant);

        mailingService.sendEmailToParticipant(participant);
        return STATUS_OK;
    }

    private void processApplicationAndParticipant(Participant participant) {

        if (isEmpty(participant.getSysIndf())) {
            translationService.createTranslitSysIndf(participant);
        }

        scsGenerationService.generateParticipant(participant);
        TranslitedParticipant translitedParticipant = translationService
                .createTranslitedParticipant(participant);
        scsGenerationService.generateApplication(participant, translitedParticipant);
    }

}
