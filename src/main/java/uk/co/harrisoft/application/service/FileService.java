package uk.co.harrisoft.application.service;

import java.io.File;
import java.util.List;

/**
 * A Simple file service to read a directory configured in properties.
 *
 * @author Andrew
 */
public interface FileService {

    /**
     * Returns a list of files supported by the @{link ContentReader}s
     *
     * @return a List of File.
     */
    List<File> getSupportedFiles();

    /**
     * Reads the contents of the files and compares against the vehicle website.
     *
     * @param files the supported files.
     */
    void checkVehicles(final List<File> files);
}
