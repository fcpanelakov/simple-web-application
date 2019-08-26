package cz.nguyenngocanh.aps.rowmappers;

import cz.nguyenngocanh.aps.model.DataSourceConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Row mapper for {@link DataSourceConfig}
 */
public class DataSourceConfigRowMapper implements RowMapperPlusPara<DataSourceConfig> {
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
}
