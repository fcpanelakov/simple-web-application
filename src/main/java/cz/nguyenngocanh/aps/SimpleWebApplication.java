package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.rest.RepositoryRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RepositoryRestController.class)
public class SimpleWebApplication {

    public static void main(String args[]){
        SpringApplication.run(SimpleWebApplication.class, args);
    }

}
