package uk.co.harrisoft.application.service;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.harrisoft.application.reader.ContentReader;

/**
 * Simple manager to manager {@link ContentReader} implementations to determine
 * if a file type is supported.
 *
 * @author Andrew
 */
@Component
public class ContentReaderManagerImpl implements ContentReaderManager {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ContentReaderManagerImpl.class);

    private final Tika tika = new Tika();

    /** the @{link ContentReader}s. */
    @Autowired
    private ContentReader[] readers;

    public ContentReader getContentReader(final File file) {
        ContentReader result = null;
        try {
            final String fileType = tika.detect(file);
            final String extension = file.getName().split("\\.")[1];
            LOG.info("filename {}, file mime type {}, file size {} bytes, file extension {}",
                file.getName(),
                fileType,
                file.length() / 1024,
                extension);
            for (final ContentReader reader : readers) {
                if (reader.supports(fileType)) {
                    result = reader;
                    break;
                }
            }
        } catch (final IOException e) {
            LOG.error("Unknown file type {}", file);
        }

        return result;
    }


}
