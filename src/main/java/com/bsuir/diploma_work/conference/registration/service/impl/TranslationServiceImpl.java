package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.service.TranslationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;


@Service("TranslationService")
public class TranslationServiceImpl implements TranslationService {

    private static final Logger logger = getLogger(TranslationServiceImpl.class);

    private static final String REGEX_CHECKING_SPECIAL_ARTICLES = "[^A-Za-z0-9_]";
    private static final String REGEX_CHECKING_SPACE = "\\s";

    private String alpha = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");

    private String[] _alpha = {"a", "b", "v", "g", "d", "e", "yo", "g", "z", "i", "y", "i",
            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
            "f", "h", "tz", "ch", "sh", "sh", "", "e", "yu", "ya"};


    @Override
    public void createTranslitSysIndf(Participant participant) {
        String lastName = tanslit(participant.getLastName());
        String fistNameFirstChar = tanslit(participant.getFirstName().substring(0, 1));

        String middleName = participant.getMiddleName();
        String middleNameFirstChar = "";
        if (middleName != null && !middleName.isEmpty()) {
            middleNameFirstChar = tanslit(middleName.substring(0, 1));
        }

        String sysIndf = lastName + "_" + fistNameFirstChar + "_" + middleNameFirstChar;

        participant.setSysIndf(sysIndf);

        logger.debug("Result system identificator", sysIndf);
    }

    @Override
    public String createTranslitApplicationNameOfArticle(Application application) {
        String nameOfArticle = application.getNameOfArticle();
        nameOfArticle = tanslit(nameOfArticle);
        nameOfArticle = nameOfArticle
                .replaceAll(REGEX_CHECKING_SPACE, "_");
        nameOfArticle = nameOfArticle
                .replaceAll(REGEX_CHECKING_SPECIAL_ARTICLES, "");
        return nameOfArticle;
    }

    private String tanslit(String original) {

        original = original.toLowerCase();

        StringBuffer newStr = new StringBuffer("");
        char[] chs = original.toCharArray();

        for (int i = 0; i < chs.length; i++) {
            int k = alpha.indexOf(chs[i]);
            if (k != -1)
                newStr.append(_alpha[k]);
            else {
                newStr.append(chs[i]);
            }
        }

        logger.debug("Tanslated string: " + newStr);
        return newStr.toString();
    }
}
