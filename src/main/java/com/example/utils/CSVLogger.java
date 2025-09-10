package com.example.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

public class CSVLogger {
    private final File csvFile;

    public CSVLogger(Properties props) {
        String path = props.getProperty("reports.csv", "reports/results.csv");
        csvFile = new File(path);
        try {
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            if (!csvFile.exists()) {
                try (CSVWriter w = new CSVWriter(new FileWriter(csvFile, true))) {
                    w.writeNext(new String[]{"test_name", "status", "execution_time", "duration_ms", "error_message"});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void append(String testName, String status, String executionTime, long durationMs, String errorMessage) {
        try (CSVWriter w = new CSVWriter(new FileWriter(csvFile, true))) {
            w.writeNext(new String[]{testName, status, executionTime, String.valueOf(durationMs), errorMessage == null ? "" : errorMessage});
        } catch (IOException e) {
            System.err.println("Failed to write CSV: " + e.getMessage());
        }
    }
}
