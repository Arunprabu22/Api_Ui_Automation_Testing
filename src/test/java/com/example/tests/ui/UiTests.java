package com.example.tests.ui;

import com.example.tests.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UiTests extends BaseTest {

    @Test
    public void openGoogle_checkTitle() {
        WebDriver d = getDriver();
        d.get("https://www.google.com");
        String t = d.getTitle();
        Assert.assertTrue(t.toLowerCase().contains("google"));
    }

    @Test
    public void openExampleDotCom() {
        WebDriver d = getDriver();
        d.get("https://example.com");
        Assert.assertEquals(d.getTitle(), "Example Domain");
    }

    @Test
    public void openJsonPlaceholderHome() {
        WebDriver d = getDriver();
        d.get("https://jsonplaceholder.typicode.com");
        Assert.assertTrue(d.getTitle().length() > 0);
    }

    @Test
    public void navigateToAboutMozilla() {
        WebDriver d = getDriver();
        d.get("https://www.mozilla.org");
        Assert.assertTrue(d.getTitle().toLowerCase().contains("mozilla"));
    }

    @Test
    public void openGithub_home() {
        WebDriver d = getDriver();
        d.get("https://github.com");
        Assert.assertTrue(d.getTitle().toLowerCase().contains("github"));
    }

    @Test
    public void openStackOverflow() {
        WebDriver d = getDriver();
        d.get("https://stackoverflow.com");
        Assert.assertTrue(d.getTitle().toLowerCase().contains("stackoverflow"));
    }

    @Test
    public void openWikipedia() {
        WebDriver d = getDriver();
        d.get("https://www.wikipedia.org");
        Assert.assertTrue(d.getTitle().toLowerCase().contains("wikipedia"));
    }

    @Test
    public void openDomainDotCom() {
        WebDriver d = getDriver();
        d.get("https://domain.com");
        Assert.assertTrue(d.getTitle().length() > 0);
    }

    @Test
    public void openBing() {
        WebDriver d = getDriver();
        d.get("https://www.bing.com");
        Assert.assertTrue(d.getTitle().toLowerCase().contains("bing"));
    }

    @Test
    public void openYahoo() {
        WebDriver d = getDriver();
        d.get("https://www.yahoo.com");
        Assert.assertTrue(d.getTitle().length() > 0);
    }
}
