package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
import cz.nguyenngocanh.aps.jdbc.MapStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ConnectionRestController
 * Rest controller for communication with intern application database
 */
@RestController
@RequestMapping(value = "/connection")
public class ConnectionRestController {
    @Autowired
    private MapStore<String, DataSourceConfig> connectionMap;

    /**
     * Add new database connection
     * @param dataSourceConfig
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNewConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        connectionMap.put(dataSourceConfig);
    }

    /**
     * @return List of database connections
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DataSourceConfig> getConnectionList() {
        return connectionMap.values();
    }

    /**
     * Delete database connection from list
     * @param connectionName
     */
    @DeleteMapping(value = "/delete/{connectionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteConnection(@PathVariable String connectionName) {
            connectionMap.remove(connectionName);
    }

    /**
     * Update database connection
     * @param dataSourceConfig
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        connectionMap.update(dataSourceConfig.getConnectionName(), dataSourceConfig);
    }
}