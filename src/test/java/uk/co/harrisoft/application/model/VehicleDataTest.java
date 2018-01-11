package uk.co.harrisoft.application.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class VehicleDataTest {

    /**
     * Tests that the two constructors produce the same result.
     */
    @Test
    public void testVehicleData() {
        final VehicleData expected = new VehicleData("AB12CDE", "BOBBINS", "PURPLE");
        final VehicleData result = new VehicleData();
        result.setRegistration("AB12CDE");
        result.setMake("BOBBINS");
        result.setColour("PURPLE");

        assertEquals(expected.getRegistration(), result.getRegistration());
        assertEquals(expected.getColour(), result.getColour());
        assertEquals(expected.getMake(), result.getMake());
        assertEquals(expected.toString(), result.toString());
        assertEquals(expected, result);

        assertNotEquals(expected, new VehicleData("AB12CDE", "BLUE", "PURPLE"));
    }

}
