package uk.co.harrisoft.application.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import uk.co.harrisoft.application.model.VehicleData;

public class CsvContentReaderTest {

    private final CsvContentReader reader = new CsvContentReader();

    @Test
    public void testSupports() {
        assertTrue(reader.supports("text/csv"));
        assertFalse(reader.supports("text/plain"));
    }

    @Test
    public void testGetContentsCsv() {

        final List<VehicleData> result = reader.getContents(new File("src/test/resources/vehicles.csv"));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertEquals(new VehicleData("BJ16RNN", "SUZUKI", "RED"), result.get(0));
    }

    @Test
    public void testGetContentsTxt() {

        final List<VehicleData> result = reader.getContents(new File("src/test/resources/bobbins.txt"));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
