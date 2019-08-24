package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.jdbc.JdbcConfig;
import cz.nguyenngocanh.aps.rest.ConnectionRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({
        ConnectionRestController.class,
        JdbcConfig.class
})
@SpringBootApplication
public class SimpleWebApplication {

    public static void main(String args[]){
        SpringApplication.run(SimpleWebApplication.class, args);
    }

}
