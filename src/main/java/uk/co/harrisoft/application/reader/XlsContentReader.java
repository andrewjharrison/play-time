package uk.co.harrisoft.application.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import uk.co.harrisoft.application.model.VehicleData;
import uk.co.harrisoft.application.utils.VrmUtil;

/**
 * Reads the contents of an Excel xls spreadsheet.
 *
 * @author Andrew
 */
@Component
public class XlsContentReader implements ContentReader {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(XlsContentReader.class);

    /**
     * Checks if the mime-type is supported by this reader.
     *
     * @param type the mime-type to check
     * @return true if the mime-type is supported, otherwise false.
     */
    public boolean supports(final String type) {
        return "application/vnd.ms-excel".equals(type);
    }

    /**
     * Reads the contents of an xls format Excel Spreadsheet.
     *
     * @param file the file to be read
     * @return the vehicle data as a list
     */
    public List<VehicleData> getContents(final File file) {
        final List<VehicleData> vehicles = new ArrayList<VehicleData>();

        HSSFWorkbook wb = null;
        try {
            final POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            wb = new HSSFWorkbook(fs);
            final HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            // As the data is in a Excel Spreadsheet, we assume that the data
            // starts in column A. The data is read from the first row beginning
            // with a valid number plate
            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    boolean valid = true;
                    final String[] data = new String[3];
                    int c = 0;

                    do {
                        cell = row.getCell((short) c);
                        if (c == 0 && !VrmUtil.VRM_REGEX.matcher(cell.getStringCellValue()).matches()) {
                            valid = false;
                        } else {
                            data[c] = cell.getStringCellValue();
                        }
                        c++;
                    } while (valid && c < row.getPhysicalNumberOfCells());

                    if (valid) {
                    final VehicleData vehicle = new VehicleData();
                    vehicle.setRegistration(data[0]);
                    vehicle.setMake(data[1]);
                    vehicle.setColour(data[2]);
                    vehicles.add(vehicle);
                    }
                }
            }
            wb.close();
        } catch (final Exception e) {
            LOG.error("Failed to read file {}", file.getName(), e);
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (final IOException e) {
                    LOG.error("Unable to close workbook after fault", e);
                }
            }
        }
        return vehicles;
    }

}
