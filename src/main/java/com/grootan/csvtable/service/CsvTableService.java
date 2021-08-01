package com.grootan.csvtable.service;

import com.grootan.csvtable.dao.CsvTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Deepak on 31/07/21.
 */
@Service
public class CsvTableService {

    @Autowired
    private CsvTableDao csvTableDao;

    public String parseCSV(MultipartFile csvFile) {

        Supplier<Stream<String>> lines = getRecords(csvFile);
        List<String> headers = getHeaders(lines.get());
        if (headers.isEmpty()) {
            return "Invalid records / No records found";
        }
        csvTableDao.createTable(headers);

        lines.get()
            .skip(1L)
            .map(line -> Arrays.asList(line.split(",")))
            .forEach(values -> {
                csvTableDao.insertValues(headers, values);
            });
        return "Upload Success";
    }

    private Supplier<Stream<String>> getRecords(MultipartFile csvFile) {
        return () -> {
            try {
                return new BufferedReader(new InputStreamReader(csvFile.getInputStream())).lines();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }

    public List<String> getAllRecords() {
        return csvTableDao.findAll();
    }

    private List<String> getHeaders(Stream<String> lines) {
        return lines
            .findFirst()
            .map(line -> Arrays.asList(line.split(",")))
            .orElse(Collections.emptyList());
    }
}
