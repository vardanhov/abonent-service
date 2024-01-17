package com.abonent.service;


import com.abonent.helper.CSVHelper;
import com.abonent.model.Sessions;
import com.abonent.repo.SessionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Component
public class SessionInit {

    @Value("${csv.file.directory}")
    private String csvFileDirectory;
    private final SessionsRepository sessionsRepository;

    public Logger logger = LoggerFactory.getLogger("SessionInit");

    @Autowired
    public SessionInit(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }


    /*
     * Method save sessions to database from csv file
     *
     */
    @PostConstruct
    public void saveSessionsCsvToDatabase() throws Exception {

        logger.info("Starting read file");
        File file = new File(csvFileDirectory);
        FileInputStream input = new FileInputStream(file);

        try {
            List<Sessions> sessions = CSVHelper.convertCsvToSessions(input);
            logger.info("Saving sessions into database");
            sessionsRepository.saveAll(sessions);
        } catch (Exception exception) {
            logger.error("Could not find the file");
            throw new Exception(exception.getMessage());
        }
    }
}
