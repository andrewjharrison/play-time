package uk.co.harrisoft.application.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import uk.co.harrisoft.application.model.VehicleData;
import uk.co.harrisoft.application.selenium.web.Expectations;

/**
 * @author Andrew
 */
@Component
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${application.files.location}")
    private String directoryPath;

    @Autowired
    private ContentReaderManager fileManager;

    @Autowired
    private Expectations expectations;

    /**
     * Returns a List of @{link File}s that are supported by the current @{link
     * ContentReader}s.
     *
     * @return a list of files
     */
    public List<File> getSupportedFiles() {

        final List<File> supportedFiles = new ArrayList<File>();

        // read the directory
        final File location = new File(directoryPath);

        // check that we have a directory to begin with
        if (location.isDirectory()) {
            for (final File file : location.listFiles()) {
                // check the ContentReaders for supported types
                if (!file.isDirectory() && fileManager.getContentReader(file) != null) {
                    supportedFiles.add(file);
                }
            }
        }

        return supportedFiles;
    }

    public void checkVehicles(final List<File> files) {
        for (final File file : files) {
            final List<VehicleData> vehicles = fileManager.getContentReader(file).getContents(file);
            expectations.checkVehicles(vehicles);
        }
    }

}
