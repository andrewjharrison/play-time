package uk.co.harrisoft.application.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import uk.co.harrisoft.application.reader.ContentReader;
import uk.co.harrisoft.application.reader.CsvContentReader;
import uk.co.harrisoft.application.reader.XlsContentReader;
import uk.co.harrisoft.application.reader.XlsxContentReader;

public class ContentReaderManagerImplTest {

    private final ContentReaderManager manager = new ContentReaderManagerImpl();

    @Before
    public void setup() {
        final List<ContentReader> readers = new ArrayList<ContentReader>();
        readers.add(new XlsContentReader());
        readers.add(new XlsxContentReader());
        readers.add(new CsvContentReader());
        ReflectionTestUtils.setField(manager, "readers", readers.toArray(new ContentReader[2]));
    }

    @Test
    public void testGetContentReader() {


        assertNull(manager.getContentReader(new File("src/test/resources/bobbins.txt")));
        assertNotNull(manager.getContentReader(new File("src/test/resources/vehicles.csv")));
        assertNotNull(manager.getContentReader(new File("src/test/resources/vehicles.xls")));
        assertNotNull(manager.getContentReader(new File("src/test/resources/vehicles.xlsx")));
        assertNull(manager.getContentReader(new File("src/test/resources/iban-api.xml")));
    }

}
