package uk.co.harrisoft.application.reader;

import java.io.File;
import java.util.List;

import uk.co.harrisoft.application.model.VehicleData;

/**
 * Defines the ability to read content (in this case vehicle content) from a
 * file of a specified mime-type. Assumptions are made that supported files will
 * contain VRM, Make and Colour.
 *
 * @author Andrew
 */
public interface ContentReader {

    /**
     * Checks if the ContentReader is supported.
     *
     * @param type
     * @return true is the mime-type is supported by this content reader.
     */
    boolean supports(String type);

    /**
     * Reads the contents of the file.
     *
     * @param file the file to be read
     * @return a List of the @{link VehicleData} contained in the file.
     */
    List<VehicleData> getContents(File file);
}
