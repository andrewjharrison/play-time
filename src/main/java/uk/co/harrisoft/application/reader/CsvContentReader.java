package uk.co.harrisoft.application.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import uk.co.harrisoft.application.model.VehicleData;
import uk.co.harrisoft.application.utils.VrmUtil;

/**
 * Reads the Contents of a CSV file
 *
 * @author Andrew
 */
@Component
public class CsvContentReader implements ContentReader {

    /** logger.. */
    private static final Logger LOG = LoggerFactory.getLogger(CsvContentReader.class);

    /** is this mime-type supported by this reader? */
    public boolean supports(final String type) {
        return "text/csv".equals(type);
    }

    /**
     * Reads the contents of the CSV file into a List of @{link VehicleData}.
     *
     * @param file the CSV file to be read.
     */
    public List<VehicleData> getContents(final File file) {
        final List<VehicleData> vehicles = new ArrayList<VehicleData>();

        try {
            for (final String line : IOUtils.readLines(new FileReader(file))) {
                final String[] parts = line.split(",");
                if (parts.length == 3 && VrmUtil.VRM_REGEX.matcher(parts[0]).matches()) {
                    final VehicleData vehicle = new VehicleData();
                    vehicle.setRegistration(parts[0]);
                    vehicle.setMake(parts[1]);
                    vehicle.setColour(parts[2]);
                    vehicles.add(vehicle);
                }
            }
        } catch (final FileNotFoundException e) {
            LOG.error("Could not find the file {}", file, e);
        } catch (final IOException e) {
            LOG.error("Could not read the file {}", file, e);
        }

        return vehicles;
    }

}
