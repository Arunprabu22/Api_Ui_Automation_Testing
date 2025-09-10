package com.example.listeners;

import com.example.utils.CSVLogger;
import com.example.utils.DBLogger;
import com.example.utils.HTMLReportGenerator;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TestResultListener implements ITestListener {
    private DBLogger dbLogger;
    private CSVLogger csvLogger;
    private HTMLReportGenerator html;
    private Properties props;

    @Override
    public void onStart(ITestContext context) {
        props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
        dbLogger = new DBLogger(props);
        csvLogger = new CSVLogger(props);
        html = new HTMLReportGenerator(props.getProperty("reports.html", "reports/results.html"));
    }

    private String now() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()).format(Instant.now());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        String testName = result.getMethod().getMethodName();
        dbLogger.insertResult(testName, "PASS", duration, null);
        csvLogger.append(testName, "PASS", now(), duration, null);
        html.addRow(testName, "PASS", now(), duration, null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        String testName = result.getMethod().getMethodName();
        String err = result.getThrowable() == null ? "" : result.getThrowable().toString();
        dbLogger.insertResult(testName, "FAIL", duration, err);
        csvLogger.append(testName, "FAIL", now(), duration, err);
        html.addRow(testName, "FAIL", now(), duration, err);
    }

    @Override
    public void onFinish(ITestContext context) {
        html.flush();
    }

    // other ITestListener methods left empty
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        String testName = result.getMethod().getMethodName();
        dbLogger.insertResult(testName, "SKIPPED", duration, null);
        csvLogger.append(testName, "SKIPPED", now(), duration, null);
        html.addRow(testName, "SKIPPED", now(), duration, null);
    }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) { onTestFailure(result); }
}
