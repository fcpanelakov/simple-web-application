package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.Column;
import cz.nguyenngocanh.aps.model.PrimaryKey;
import cz.nguyenngocanh.aps.model.TableInformation;
import cz.nguyenngocanh.aps.rowmappers.PrimaryKeyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class OracleDatabaseViewerManager implements DatabaseViewerManager {
    private final String GET_SCHEMAS = "SELECT USERNAME AS SCHEMA_NAME FROM SYS.DBA_USERS";
    private final String GET_TABLES = "SELECT TABLE_NAME FROM ALL_TABLES";
    private final String GET_COLUMNS = "SELECT COLUMN_NAME FROM USER_TAB_COLS WHERE TABLE_NAME = ?";
    private final String GET_PRIMARY_KEYS = "SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM ALL_CONSTRAINTS WHERE TABLE_NAME = ?";
    private final String GET_DATA_FROM_COLUMN = "SELECT ? FROM ?";
    private final String GET_COLUMN_DATA_TYPE = "SELECT DATA_TYPE FROM USER_TAB_COLS WHERE table_name = ? AND COLUMN_NAME = ?";
    private JdbcTemplate jdbcTemplate;

    public OracleDatabaseViewerManager setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    @Override
    public List<String> getSchemas(){
        return jdbcTemplate.queryForList(GET_SCHEMAS, String.class);
    }

    @Override
    public List<String> getTables(){
        return jdbcTemplate.queryForList(GET_TABLES, String.class);
    }

    @Override
    public List<String> getColumns(String tableName){
        return jdbcTemplate.queryForList(GET_COLUMNS, String.class, tableName);
    }

    @Override
    public TableInformation getTableInformation(String tableName){
        TableInformation tableInformation = new TableInformation();
        List<PrimaryKey> primaryKeys = jdbcTemplate.query(GET_PRIMARY_KEYS, new PrimaryKeyRowMapper(), tableName);
        List<String> columnsNames = getColumns(tableName);
        List<Column> columns = columnsNames
                .stream()
                .map(cName -> {
                    List<String> data = jdbcTemplate.queryForList(GET_DATA_FROM_COLUMN, String.class, cName, tableName);
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
