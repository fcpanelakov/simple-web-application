package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
import cz.nguyenngocanh.aps.jdbc.MapStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "/connection")
public class ConnectionRestController {
    @Autowired
    private MapStore<String, DataSourceConfig> connectionMap;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNewConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        connectionMap.put(dataSourceConfig);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DataSourceConfig> getConnectionList() {
        return connectionMap.values();
    }

    @DeleteMapping(value = "/delete/{connectionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteConnection(@PathVariable String connectionName) {
            connectionMap.remove(connectionName);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateConnection(@RequestBody DataSourceConfig dataSourceConfig) {
        connectionMap.update(dataSourceConfig.getConnectionName(), dataSourceConfig);
    }
}