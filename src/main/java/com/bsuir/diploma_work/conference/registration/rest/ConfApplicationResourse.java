package com.bsuir.diploma_work.conference.registration.rest;


import com.bsuir.diploma_work.conference.registration.domain.Participant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ConfApplicationResourse {

    @RequestMapping(value = "/generate", method = POST, consumes = "application/json",
            produces = "application/json")
    public String generateApplication(@Valid @RequestBody Participant participant) {
        System.out.println(participant);
        return "welcome";
    }


}
