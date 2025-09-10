package com.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLReportGenerator {
    private final File htmlFile;
    private final List<String> rows = new ArrayList<>();

    public HTMLReportGenerator(String path) {
        this.htmlFile = new File(path);
        File parent = htmlFile.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
    }

    public synchronized void addRow(String testName, String status, String executionTime, long durationMs, String err) {
        rows.add(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%s</td></tr>",
                escape(testName), escape(status), escape(executionTime), durationMs, escape(err == null ? "" : err)));
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public synchronized void flush() {
        String html = "<html><head><meta charset=\"utf-8\"><title>Test Results</title></head><body>"
                + "<h2>Automation Test Results</h2>"
                + "<table border=1 cellpadding=5 cellspacing=0><thead><tr><th>Test</th><th>Status</th><th>Time</th><th>Duration(ms)</th><th>Error</th></tr></thead><tbody>"
                + String.join("\n", rows)
                + "</tbody></table></body></html>";
        try (FileWriter fw = new FileWriter(htmlFile)) {
            fw.write(html);
        } catch (IOException e) {
            System.err.println("Failed to write HTML report: " + e.getMessage());
        }
    }
}
