package application;

import controllers.OrderController;
import models.builders.KitBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import repository.*;

@SpringBootApplication
@ComponentScan(basePackageClasses = OrderController.class)
public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting application");
        SpringApplication.run(App.class);
    }
}
