package uk.co.harrisoft.application.selenium.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import uk.co.harrisoft.application.model.VehicleData;

/**
 * Implementation of the DVLA browser actions. Assumptions are made that this is
 * not a service that allows deep-linking, meaning that all calls are expected
 * to be dome in sequence.
 *
 * @author Andrew
 */
@Component
public class ServiceActionsImpl implements ServiceActions {

    /** the homepage url. */
    @Value("${application.dvla.homepage.url}")
    private String homepage;


    public void getHomePage(final WebDriver driver) {

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        // get the home page
        driver.get(homepage);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("get-started")));
    }

    public void getVrmEntryPage(final WebDriver driver) {

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click on the continue button on the home page.
        driver.findElement(By.className("button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Continue")));
    }

    public void getVehicleOverview(final WebDriver driver, final String vrm) {
        final WebElement inputBox = driver.findElement(By.id("Vrm"));
        inputBox.sendKeys(vrm);

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.name("Continue")).click();

        // Check for an error
        final String heading =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("heading-large"))).getText();
        if (!heading.contains("Is this the vehicle you are looking for?")) {
            throw new IllegalArgumentException("Unable to find the VRM provided : " + vrm);
        }

        // Get the overview details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-summary-item")));
    }

    public void getVehicleDetail(final WebDriver driver) {
        final WebElement checkboxDiv = driver.findElement(By.className("multiple-choice"));
        final WebElement yesbox = checkboxDiv.findElement(By.id("Correct_True"));

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        // as we should already have verified the vehicle displayed on the page,
        // click yes.
        yesbox.click();

        // hit the continue button
        final WebElement button = driver.findElement(By.tagName("Button"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("reg-mark")));
    }

    public boolean verifyOverview(final WebDriver driver, final VehicleData vehicle) {

        final List<WebElement> items = driver.findElements(By.className("list-summary-item"));

        // Check the vrm mark
        final String pvrm = StringUtils.trimAllWhitespace(getSecondSpan(items.get(0)).getText());

        // Check the Make
        final String pmake = getSecondSpan(items.get(1)).findElement(By.tagName("strong")).getText();

        // Check the colour
        final String pcolour = getSecondSpan(items.get(2)).findElement(By.tagName("strong")).getText();

        return vehicle.getRegistration().equalsIgnoreCase(pvrm) && vehicle.getMake().equalsIgnoreCase(pmake)
               && vehicle.getColour().equalsIgnoreCase(pcolour);
    }

    public boolean verifyVehicleDetail(final WebDriver driver, final VehicleData vehicle) {
        // Get the vrm
        final String pvrm = StringUtils.trimAllWhitespace(driver.findElement(By.className("reg-mark")).getText());

        final List<WebElement> summaryItems = driver.findElements(By.className("list-summary-item"));

        // Get the Make
        final String pmake = getSecondSpan(summaryItems.get(0)).findElement(By.tagName("strong")).getText();

        // Get the colour
        final String pcolour = getSecondSpan(summaryItems.get(8)).findElement(By.tagName("strong")).getText();

        return vehicle.getRegistration().equalsIgnoreCase(pvrm) && vehicle.getMake().equalsIgnoreCase(pmake)
               && vehicle.getColour().equalsIgnoreCase(pcolour);
    }

    /**
     * Convenience method to return the second in a list of <code><span></code>
     * tags.
     *
     * @param element
     * @return the second span element or null if there isn't one.
     */
    private WebElement getSecondSpan(final WebElement element) {
        WebElement result = null;
        final List<WebElement> elements = element.findElements(By.tagName("span"));
        if (elements.size() == 2) {
            result = elements.get(1);
        }
        return result;
    }
}
