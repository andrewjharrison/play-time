package uk.co.harrisoft.application.service;

import java.io.File;

import uk.co.harrisoft.application.reader.ContentReader;

/**
 * Manages the ContentReaders
 * 
 * @author Andrew
 */
public interface ContentReaderManager {

    ContentReader getContentReader(File file);
}
