package uk.co.harrisoft.application.service;

import java.io.File;

import uk.co.harrisoft.application.reader.ContentReader;

/**
 * Manages the ContentReaders
 *
 * @author Andrew
 */
public interface ContentReaderManager {

    /**
     * Returns a @{link ContentReader} appropriate for the mime-type of the
     * provided file.
     *
     * @param file the file to retieve the ContentReader for
     * @return an appropriate ContentReader or null if no ContentReaders could
     *         be found.
     */
    ContentReader getContentReader(File file);
}
