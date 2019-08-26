package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import cz.nguyenngocanh.aps.jdbc.MapStore;
import cz.nguyenngocanh.aps.jdbc.OracleDatabaseViewerManager;
import cz.nguyenngocanh.aps.model.DataSourceConfig;
import cz.nguyenngocanh.aps.model.TableInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

/**
 * DatabaseViewerController
 * Rest controller for viewing database from database connections list
 */
@RestController
@RequestMapping(value = "/database")
public class DatabaseViewerController {
    @Autowired
    private MapStore<String, DataSourceConfig> connectionMap;
    @Autowired
    private JdbcTemplateBuilder jdbcTemplateBuilder;
    @Autowired
    private OracleDatabaseViewerManager oracleManager;

    /**
     * @param connectionName - Name of connection of specific database
     * @return All schemas of specific connection
     */
    @GetMapping(value = "/{connectionName}/schemas")
    public List<String> schemas(@PathVariable String connectionName){
        DataSourceConfig dataSourceConfig = connectionMap.get(connectionName);
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        oracleManager.setJdbcTemplate(jdbcTemplateBuilder.build(dataSource));
        return oracleManager.getSchemas();
    }

    /**
     * @param connectionName - Name of connection of specific database
     * @return All tables of specific connection
     */
    @GetMapping(value = "/{connectionName}/tables")
    public List<String> tables(@PathVariable String connectionName){
        DataSourceConfig dataSourceConfig = connectionMap.get(connectionName);
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        oracleManager.setJdbcTemplate(jdbcTemplateBuilder.build(dataSource));
        return oracleManager.getTables();
    }

    /**
     *
     * @param connectionName - Name of connection of specific database
     * @param tableName - Name of the table that we wanna get information from
     * @return Columns of specific table
     */
    @GetMapping(value = "/{connectionName}/{tableName}/columns")
    public List<String> columns(@PathVariable String connectionName, @PathVariable String tableName){
        DataSourceConfig dataSourceConfig = connectionMap.get(connectionName);
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        oracleManager.setJdbcTemplate(jdbcTemplateBuilder.build(dataSource));
        return oracleManager.getColumns(tableName);
    }

    /**
     *
     * @param connectionName - Name of connection of specific database
     * @param tableName - Name of the table that we wanna get information from
     * @return Information of specific table
     */
    @GetMapping(value = "/{connectionName}/{tableName}/tableinfo")
    public TableInformation tableInformation(@PathVariable String connectionName, @PathVariable String tableName){
        DataSourceConfig dataSourceConfig = connectionMap.get(connectionName);
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        oracleManager.setJdbcTemplate(jdbcTemplateBuilder.build(dataSource));
        return oracleManager.getTableInformation(tableName);
    }

}
