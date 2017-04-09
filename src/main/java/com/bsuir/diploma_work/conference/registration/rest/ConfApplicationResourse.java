package com.bsuir.diploma_work.conference.registration.rest;


import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.service.ParticipantService;
import com.bsuir.diploma_work.conference.registration.service.SCSGenerationService;
import com.bsuir.diploma_work.conference.registration.service.TranslationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @RequestMapping(value = "/generate", method = POST, consumes = "application/json",
            produces = "application/json")
    public String generateApplication(@Valid @RequestBody Participant participant) {

        logger.debug("Received participant: {}", participant);

        if (participant.isNewComer()) {
            translationService.createTranslitSysIndf(participant);
           // participantService.saveParticipant(participant);
            scsGenerationService.generateParticipant(participant);
        } else {
           // participantService.saveApplication(participant);
        }

        scsGenerationService.generateApplication(participant);


        return "welcome";
    }

}
