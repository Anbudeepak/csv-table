package com.grootan.csvtable.controller;


import com.grootan.csvtable.service.CsvTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Deepak on 31/07/21.
 */
@RestController
public class CsvTableController {

    @Autowired
    private CsvTableService csvTableService;

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {
        String message;
        if (file.isEmpty()) {
            message = "Please select a CSV file to upload.";
        } else {
            try {
                message = csvTableService.parseCSV(file);
            } catch (Exception e) {
                e.printStackTrace();
                message = "An error occurred while processing the CSV file. " + e.getMessage();
            }
        }
        return message;
    }

    @GetMapping("/get-all-records")
    public List<String> getAllRecords() {
        return csvTableService.getAllRecords();
    }
}
