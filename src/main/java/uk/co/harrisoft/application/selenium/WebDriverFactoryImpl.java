package uk.co.harrisoft.application.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

/**
 * Just a cursory implementation for now. Future versions will allow for
 * different Driver instances such as Firefox, IE, etc..
 *
 * @author Andrew
 */
@Component
public class WebDriverFactoryImpl implements WebDriverFactory {

    public WebDriver getDriver() {
        return new ChromeDriver();
    }

}
