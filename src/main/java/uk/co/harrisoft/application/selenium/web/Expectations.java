package uk.co.harrisoft.application.selenium.web;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.harrisoft.application.model.VehicleData;

/**
 * Sets the expectations of the check against the DVLA system.
 *
 * @author Andrew
 */
@Component
public class Expectations {

    /** the logger. */
    private static final Logger LOG = LoggerFactory.getLogger(Expectations.class);

    /** service class to define the actions taken in the browser. */
    @Autowired
    private ServiceActions actions;

    /**
     * Check the vehicle data read from file against the DVAL system. Currently
     * only outputs the result to the log. Does not fail instantly as that would
     * prevent further checking of other vehicle details in the list.
     *
     * @param vehicles
     */
    public void checkVehicles(final List<VehicleData> vehicles) {
        for (final VehicleData vehicle : vehicles) {

            LOG.info("Attempting vehicle with VRM {}", vehicle.getRegistration());
            final WebDriver driver = new FirefoxDriver();
            try {
                // Get the home page
                actions.getHomePage(driver);

                // Get the VRM entry page
                actions.getVrmEntryPage(driver);

                // Get the Vehicle Overview page
                actions.getVehicleOverview(driver, vehicle.getRegistration());

                // Verify the vehicle details.
                boolean verified = actions.verifyOverview(driver, vehicle);
                LOG.info("Vehicle with VRM {} {} been verified on the overview page",
                    vehicle.getRegistration(),
                    verified ? "has" : "has not");

                if (verified) {
                    // Get the Vehicle Details page
                    actions.getVehicleDetail(driver);

                    // Verify the Vehicle Details.
                    verified = actions.verifyVehicleDetail(driver, vehicle);
                    LOG.info("Vehicle with VRM {} {} been verified on the details page",
                        vehicle.getRegistration(),
                        verified ? "has" : "has not");
                }
            } catch (final TimeoutException e) {
                LOG.error("Failed miserably with {}", e, e);
            } catch (final IllegalArgumentException e) {
                LOG.warn(e.getMessage());
            } finally {
                driver.close();
            }

        }
    }

}
