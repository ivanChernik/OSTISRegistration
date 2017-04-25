package com.bsuir.diploma_work.conference.registration.service;

import com.bsuir.diploma_work.conference.registration.domain.Participant;

public interface MailingService {

    void sendEmailToParticipant(Participant participant);
}
