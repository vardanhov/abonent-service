package com.abonent.helper;

import com.abonent.model.AbonentId;
import com.abonent.model.Sessions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class CSVHelper {

    /*
     * Method converts data from csv to list of sessions
     *
     */
    public static List<Sessions> convertCsvToSessions(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CSVRecord> csvRecords = csvParser.getRecords();

            return csvRecords.stream()
                    .map(a -> new Sessions(a.get("cell_id"), new AbonentId(a.get("ctn")))
                    ).toList();
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}