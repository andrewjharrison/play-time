package uk.co.harrisoft.application.selenium.web;

import org.openqa.selenium.WebDriver;

import uk.co.harrisoft.application.model.VehicleData;

/**
 * Defines the paths through the
 * 
 * @author Andrew
 */
public interface ServiceActions {

    /**
     * Drives the browser to the home page
     * 
     * @param driver the webDriver
     */
    void getHomePage(WebDriver driver);

    /**
     * Drives the browser to the VRM enty page
     * 
     * @param driver the webDriver
     */
    void getVrmEntryPage(WebDriver driver);

    /**
     * Drives the browser to the Vehicle Overview page
     * 
     * @param driver the webDriver
     * @param vrm the VRM to input.
     */
    void getVehicleOverview(WebDriver driver, String vrm);

    /**
     * Drives the browser to the vehicle detail page.
     * 
     * @param driver the webDriver
     */
    void getVehicleDetail(WebDriver driver);

    /**
     * Verifies the vehicle detail on the overview page.
     * 
     * @param driver the webDriver
     * @param vehicle the @{link VehicleData} to check
     * @return true if the vehicle details match, otherwise false.
     */
    boolean verifyOverview(WebDriver driver, VehicleData vehicle);

    /**
     * Verifies the vehicle detail on the Vehicle Details page.
     * 
     * @param driver the webDriver
     * @param vehicle the @{link VehicleData}
     * @return true if the vehicle data matches, otherwise false.
     */
    boolean verifyVehicleDetail(WebDriver driver, VehicleData vehicle);
}
