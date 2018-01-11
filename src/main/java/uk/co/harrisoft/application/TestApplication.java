package uk.co.harrisoft.application;


import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import uk.co.harrisoft.application.service.FileService;

/**
 * Application for interrogating various different file types in order to
 * retrieve vehicle data and check against the DVLA website.
 *
 * @author Andrew
 */
public class TestApplication {

    /** the logger. */
    private static final Logger LOG = LoggerFactory.getLogger(TestApplication.class);


    public static void main(final String[] args) {
        LOG.debug("Started application");
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        final FileService service = ctx.getBean(FileService.class);

        final List<File> files = service.getSupportedFiles();

        service.checkVehicles(files);

        LOG.debug("Finished Application");
    }

}
