package uk.co.harrisoft.application.selenium;

import org.openqa.selenium.WebDriver;

/**
 * A Factory for creating WebDriver instances.
 *
 * @author Andrew
 */
public interface WebDriverFactory {

    /** @return a webdriver. */
    WebDriver getDriver();
}
