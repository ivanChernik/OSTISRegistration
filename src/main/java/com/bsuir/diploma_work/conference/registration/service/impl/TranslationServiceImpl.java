package com.bsuir.diploma_work.conference.registration.service.impl;


import com.bsuir.diploma_work.conference.registration.domain.Application;
import com.bsuir.diploma_work.conference.registration.domain.Participant;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedApplication;
import com.bsuir.diploma_work.conference.registration.domain.tanslit.TranslitedParticipant;
import com.bsuir.diploma_work.conference.registration.service.TranslationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;


@Service("TranslationService")
public class TranslationServiceImpl implements TranslationService {

    private static final Logger logger = getLogger(TranslationServiceImpl.class);

    private static final String REGEX_CHECKING_SPECIAL_ARTICLES = "[^A-Za-z0-9_]";
    private static final String REGEX_CHECKING_SPACE = "\\s+";

    private static String russianChars = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");

    private static String[] englishChars = {"a", "b", "v", "g", "d", "e", "yo", "g", "z", "i", "y", "i",
            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
            "f", "h", "tz", "ch", "sh", "sh", "", "e", "yu", "ya"};


    @Override
    public void createTranslitSysIndf(Participant participant) {
        String lastName = tanslit(participant.getLastName());
        //first char to Upper case
        lastName = Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1);
        String fistNameFirstChar = tanslit(participant.getFirstName().substring(0, 1)).toUpperCase();

        String middleName = participant.getMiddleName();
        String middleNameFirstChar = "";

        String sysIndf = "";

        if (middleName != null && !middleName.isEmpty()) {
            middleNameFirstChar = tanslit(middleName.substring(0, 1)).toUpperCase();
            sysIndf = lastName + "_" + fistNameFirstChar + "_" + middleNameFirstChar;
        } else {
            sysIndf = lastName + "_" + fistNameFirstChar;
        }

        sysIndf = sysIndf.replaceAll(REGEX_CHECKING_SPECIAL_ARTICLES, "");
        participant.setSysIndf(sysIndf);

        logger.debug("Result system identificator {}", sysIndf);
    }

    @Override
    public TranslitedParticipant createTranslitedParticipant(Participant participant) {

        Application application = participant.getApplication();

        TranslitedParticipant translitedParticipant = new TranslitedParticipant();

        translitedParticipant.setTanslitedApplication(translitApplication(application));

        String translitedLastName = tanslit(participant.getLastName());
        translitedLastName = translitedLastName
                .replaceAll(REGEX_CHECKING_SPECIAL_ARTICLES, "");
        translitedParticipant.setLastName(translitedLastName);

        return translitedParticipant;
    }

    private TranslitedApplication translitApplication(Application application) {
        TranslitedApplication translitedApplication = new TranslitedApplication();
        String translitedNameOfArticle =
                translitNameOfArticle(application.getNameOfArticle());

        translitedApplication.setNameOfArticle(translitedNameOfArticle);

        return translitedApplication;
    }

    private String translitNameOfArticle(String nameOfArticle) {
        String translitedNameOfArticle = nameOfArticle.trim()
                .replaceAll(REGEX_CHECKING_SPACE, "_");
        translitedNameOfArticle = tanslit(translitedNameOfArticle);
        translitedNameOfArticle = translitedNameOfArticle
                .replaceAll(REGEX_CHECKING_SPECIAL_ARTICLES, "");

        translitedNameOfArticle = retrieveFirstChar(translitedNameOfArticle);
        return translitedNameOfArticle;
    }

    private String tanslit(String original) {

        original = original.toLowerCase().trim();

        StringBuilder translitedStr = new StringBuilder();
        char[] chs = original.toCharArray();

        for (int charIndex = 0; charIndex < chs.length; charIndex++) {
            int russianChar = russianChars.indexOf(chs[charIndex]);
            if (russianChar != -1)
                translitedStr.append(englishChars[russianChar]);
            else {
                translitedStr.append(chs[charIndex]);
            }
        }

        logger.debug("Tanslited string: " + translitedStr);
        return translitedStr.toString();
    }

    private String retrieveFirstChar(String original) {

        original = original.toUpperCase().trim();

        StringBuilder firstCharsStr = new StringBuilder();
        char[] chs = original.toCharArray();
        firstCharsStr.append(chs[0]);

        int nextIndex = 1;
        int strLength = chs.length;
        for (int index = 1; index < strLength; index++) {

            nextIndex++;
            if (chs[index] == '_' && nextIndex < strLength) {
                firstCharsStr.append(chs[nextIndex]);
            }
        }

        logger.debug("First chars string: " + firstCharsStr);

        return firstCharsStr.toString();
    }

}
