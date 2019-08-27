package cz.nguyenngocanh.aps.rowmappers;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * DataSrcCDatabaseQueryConfig
 * Row mapper for {@link DataSourceConfig}
 */
public class DataSrcCDatabaseQueryConfig implements DatabaseQueryConfig<DataSourceConfig, String> {
    private final String UPDATE_QUERY = "UPDATE " + DataSourceConfig.TABLE_NAME + " SET ID = ?, CONNECTION_NAME = ?, URL = ?, USER_NAME = ?, PASSWORD = ? WHERE ID = ? ";
    @Override
    public Map<String, Object> getParameters(DataSourceConfig dataSourceConfig) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", dataSourceConfig.getConnectionName());
        parameters.put("CONNECTION_NAME", dataSourceConfig.getConnectionName());
        parameters.put("URL", dataSourceConfig.getUrl());
        parameters.put("USER_NAME", dataSourceConfig.getUsername());
        parameters.put("PASSWORD", dataSourceConfig.getPassword());
        return parameters;
    }

    @Override
    public DataSourceConfig mapRow(ResultSet resultSet, int i) throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setConnectionName(resultSet.getString("CONNECTION_NAME"));
        dataSourceConfig.setUrl(resultSet.getString("URL"));
        dataSourceConfig.setPassword(resultSet.getString("PASSWORD"));
        dataSourceConfig.setUsername(resultSet.getString("USER_NAME"));
        return dataSourceConfig;
    }

    @Override
    public PreparedStatementSetter updatePreparedStatement(String id, DataSourceConfig dataSourceConfig){
        return  (ps) -> {
                ps.setString(1, dataSourceConfig.getConnectionName());
                ps.setString(2, dataSourceConfig.getConnectionName());
                ps.setString(3, dataSourceConfig.getUrl());
                ps.setString(4, dataSourceConfig.getUsername());
                ps.setString(5, dataSourceConfig.getPassword());
                ps.setString(6, String.valueOf(id));
            };
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }
}
