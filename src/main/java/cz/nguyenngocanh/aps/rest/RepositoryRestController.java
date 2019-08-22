package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RepositoryRestController {
    private HashMap<String, JdbcTemplate> connectionMap;
    private JdbcTemplateBuilder jdbcTemplateBuilder;
    public RepositoryRestController() {
        this.connectionMap = new HashMap<>();
        this.jdbcTemplateBuilder = new JdbcTemplateBuilder();
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    //TODO: UNIT TEST
    @PostMapping(value= "/create")
    public HashMap<String, JdbcTemplate> createNewConnection(@RequestBody String connectionName){
        JdbcTemplate jdbcTemplate = jdbcTemplateBuilder.build();
        connectionMap.put(connectionName, jdbcTemplate);
        return this.connectionMap;
    }
}
