package uk.co.harrisoft.application.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import uk.co.harrisoft.application.model.VehicleData;

public class XlsContentReaderTest {

    private final XlsContentReader reader = new XlsContentReader();

    @Test
    public void testSupports() {
        assertTrue(reader.supports("application/vnd.ms-excel"));
        assertFalse(reader.supports("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        assertFalse(reader.supports("text/plain"));
    }

    @Test
    public void testGetContentsXls() {

        final List<VehicleData> result = reader.getContents(new File("src/test/resources/vehicles.xls"));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertEquals(new VehicleData("G446LAW", "SEAT", "RED"), result.get(0));

    }

    @Test
    public void testGetContentsXlsx() {

        final List<VehicleData> result = reader.getContents(new File("src/test/resources/vehicles.xlsx"));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
