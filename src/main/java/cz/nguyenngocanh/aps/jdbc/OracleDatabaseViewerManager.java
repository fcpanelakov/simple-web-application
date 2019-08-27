package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.Column;
import cz.nguyenngocanh.aps.model.PrimaryKey;
import cz.nguyenngocanh.aps.model.TableInformation;
import cz.nguyenngocanh.aps.rowmappers.PrimaryKeyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OracleDatabaseViewerManager
 * Database viewer manager specific for ORACLE database
 */
public class OracleDatabaseViewerManager implements DatabaseViewerManager {
    private static final Logger log = LoggerFactory.getLogger(OracleDatabaseViewerManager.class);
    private static final String GET_SCHEMAS = "SELECT USERNAME AS SCHEMA_NAME FROM SYS.DBA_USERS";
    private static final String GET_TABLES = "SELECT TABLE_NAME FROM ALL_TABLES";
    private static final String GET_COLUMNS = "SELECT COLUMN_NAME FROM USER_TAB_COLS WHERE TABLE_NAME = ?";
    private static final String GET_PRIMARY_KEYS = "SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM ALL_CONSTRAINTS WHERE TABLE_NAME = ?";
    private static final String GET_COLUMN_DATA_TYPE = "SELECT DATA_TYPE FROM USER_TAB_COLS WHERE table_name = ? AND COLUMN_NAME = ?";
    private JdbcTemplate jdbcTemplate;

    public OracleDatabaseViewerManager setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    /**
     * @return All schemas in database
     */
    @Override
    public List<String> getSchemas(){
        log.info("Get schemas started");
        return jdbcTemplate.queryForList(GET_SCHEMAS, String.class);
    }

    /**
     * @return All tables in database
     */
    @Override
    public List<String> getTables(){
        log.info("Get tables started");
        return jdbcTemplate.queryForList(GET_TABLES, String.class);
    }

    /**
     * @param tableName - Name of specific table
     * @return All columns of specific table
     */
    @Override
    public List<String> getColumns(String tableName){
        log.info("Get columns started");
        return jdbcTemplate.queryForList(GET_COLUMNS, String.class, tableName);
    }

    /**
     *
     * @param tableName - Name of specific table
     * @return Table information, contains all his primary keys, number of columns, columns(type, data)
     */
    @Override
    public TableInformation getTableInformation(String tableName){
        log.info("Get table information started");
        TableInformation tableInformation = new TableInformation();
        List<PrimaryKey> primaryKeys = jdbcTemplate.query(GET_PRIMARY_KEYS, new PrimaryKeyRowMapper(), tableName);
        List<String> columnsNames = getColumns(tableName);
        List<Column> columns = columnsNames
                .stream()
                .map(cName -> {
                    List<String> data = jdbcTemplate.queryForList("SELECT " + cName + " FROM " + tableName, String.class);
                    String type = jdbcTemplate.queryForObject(GET_COLUMN_DATA_TYPE, String.class, tableName, cName);
                    return new Column()
                            .setColumnType(type)
                            .setData(data);
                }).collect(Collectors.toList());

        return tableInformation.setColumnNumber(columnsNames.size())
                .setColumns(columns)
                .setPrimaryKeys(primaryKeys);
    }

}
