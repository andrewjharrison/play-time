package uk.co.harrisoft.application.service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import uk.co.harrisoft.application.reader.CsvContentReader;
import uk.co.harrisoft.application.reader.XlsContentReader;
import uk.co.harrisoft.application.reader.XlsxContentReader;
import uk.co.harrisoft.application.selenium.web.Expectations;

public class FileServiceImplTest {

    /** the class under test. */
    private final FileService fileService = new FileServiceImpl();

    /** the mocked ContentReaderManager. */
    private final ContentReaderManager contentReaderManager = EasyMock.createMock(ContentReaderManager.class);

    /** the mocked Expectations.*/
    private final Expectations expectations = EasyMock.createMock(Expectations.class);

    /** sets up the class under test. */
    @Before
    public void setup() {
        ReflectionTestUtils.setField(fileService, "contentReaderManager", contentReaderManager);
        ReflectionTestUtils.setField(fileService, "expectations", expectations);
        ReflectionTestUtils.setField(fileService, "directoryPath", "src/test/resources/");
    }

    /**
     * Tests that the correct content readers are returned.
     */
    @Test
    public void testGetSupportedFiles() {
        EasyMock.reset(contentReaderManager, expectations);

        // expect the content readers.
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/bobbins.txt")))
                .andReturn(null);
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/iban-api.xml")))
                .andReturn(null);
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/logback.xml")))
                .andReturn(null);
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/vehicles.csv")))
                .andReturn(new CsvContentReader());
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/vehicles.xls")))
                .andReturn(new XlsContentReader());
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/vehicles.xlsx")))
                .andReturn(new XlsxContentReader());

        EasyMock.replay(contentReaderManager, expectations);

        // try the service
        final List<File> files = fileService.getSupportedFiles();

        // assert the results.
        assertTrue(files.contains(new File("src/test/resources/vehicles.csv")));
        assertTrue(files.contains(new File("src/test/resources/vehicles.xls")));
        assertTrue(files.contains(new File("src/test/resources/vehicles.xlsx")));
        EasyMock.verify(contentReaderManager, expectations);
    }

    /** tests the call to check vehicles. */
    @Test
    public void testCheckVehicles() {
        EasyMock.reset(contentReaderManager, expectations);

        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/vehicles.csv")))
                .andReturn(new CsvContentReader());
        EasyMock.expect(contentReaderManager.getContentReader(new File("src/test/resources/vehicles.xlsx")))
                .andReturn(new XlsxContentReader());

        expectations.checkVehicles(EasyMock.isA(List.class));
        EasyMock.expectLastCall().times(2);

        EasyMock.replay(contentReaderManager, expectations);

        final List<File> files = new ArrayList<File>();
        files.add(new File("src/test/resources/vehicles.csv"));
        files.add(new File("src/test/resources/vehicles.xlsx"));

        fileService.checkVehicles(files);
        EasyMock.verify(contentReaderManager, expectations);
    }

    /** tests the call to check vehicles with an empty set of files. */
    @Test
    public void testCheckVehiclesEmpty() {
        EasyMock.reset(contentReaderManager, expectations);
        EasyMock.replay(contentReaderManager, expectations);
        final List<File> files = new ArrayList<File>();
        fileService.checkVehicles(new ArrayList<File>());
        EasyMock.verify(contentReaderManager, expectations);
    }
}
