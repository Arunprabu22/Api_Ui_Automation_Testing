package com.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected static Properties props = new Properties();
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"useBrowser"})
    public void setup(@Optional("true") String useBrowser) {
        // If tests use UI, initialize driver
        if ("true".equalsIgnoreCase(useBrowser)) {
            WebDriverManager.chromedriver().setup();
            ChromeDriver d = new ChromeDriver();
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(d);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver wd = driver.get();
        if (wd != null) {
            try { wd.quit(); } catch (Exception ignored) {}
            driver.remove();
        }
    }

    protected WebDriver getDriver() {
        return driver.get();
    }
}
