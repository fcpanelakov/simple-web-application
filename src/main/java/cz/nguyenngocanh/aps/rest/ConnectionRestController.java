package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.jdbc.DataSourceConfig;
import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping(value = "/connection")
public class ConnectionRestController {
    @Autowired
    private HashMap<String, JdbcTemplate> connectionMap;
    @Autowired
    private JdbcTemplateBuilder jdbcTemplateBuilder;

    //TODO: UNIT TEST
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> createNewConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        JdbcTemplate jdbcTemplate = jdbcTemplateBuilder.build(dataSource);
        connectionMap.put(dataSourceConfig.getConnectionName(), jdbcTemplate);
        return this.connectionMap.keySet();
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getConnectionList() {
        return connectionMap.keySet();
    }

    @DeleteMapping(value = "/delete/{connectionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> deleteConnection(@PathVariable String connectionName) {
        if (connectionMap.containsKey(connectionName)) {
            connectionMap.remove(connectionName);
        }
        return connectionMap.keySet();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        connectionMap.computeIfPresent(dataSourceConfig.getConnectionName(),
                (key, value) -> {
                    DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
                    return jdbcTemplateBuilder.build(dataSource);
                });
        connectionMap.size();
    }
}