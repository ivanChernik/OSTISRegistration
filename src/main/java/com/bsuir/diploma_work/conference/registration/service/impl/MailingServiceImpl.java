package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.exception.service.EmailWasNotSendException;
import com.bsuir.diploma_work.conference.registration.service.MailingService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.CharEncoding;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString;

@Service("mailingService")
public class MailingServiceImpl implements MailingService {

    @Value("${conference.year}")
    private String conferenceYear;

    private static final Logger logger = getLogger(SCSGenerationServiceImpl.class);
    private static final String EMAIL_TEMPLATE_FILE = "templates/email/ostis_confirmation_email.vm";
    private static final String EMAIL_SUBJECT_APPLICATION = "Заявка на участие в конференции OSTIS-";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VelocityEngine velocityEngine;


    @Override
    public void sendEmailToParticipant(Participant participant) {
        logger.debug("enter sendEmailToParticipant(): {}", participant.getEmail());

        String emailBody = generateEmailBody(EMAIL_TEMPLATE_FILE,
                generateParticipantModel(participant));

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            mail.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(participant.getEmail()));
            mail.setSubject(EMAIL_SUBJECT_APPLICATION + conferenceYear);
            mail.setContent(emailBody, "text/html; charset=utf-8");
            javaMailSender.send(mail);
        } catch (Exception e) {
            logger.error("Email was not send:", e);
            throw new EmailWasNotSendException();
        }

        logger.debug("exit sendEmailToParticipant(){}", participant.getEmail());
    }

    private String generateEmailBody(String templateLocation, Map<String, Object> model) {

        return mergeTemplateIntoString(velocityEngine, templateLocation, CharEncoding.UTF_8, model);
    }

    private Map<String, Object> generateParticipantModel(Participant participant) {
        Map<String, Object> model = new HashedMap();

        model.put("name", participant.getFirstName());
        model.put("surname", participant.getLastName());
        model.put("middlename", participant.getMiddleName());
        model.put("name_of_article", participant.getApplication()
                .getNameOfArticle());
        model.put("conference_year", conferenceYear);

        return model;
    }
}
