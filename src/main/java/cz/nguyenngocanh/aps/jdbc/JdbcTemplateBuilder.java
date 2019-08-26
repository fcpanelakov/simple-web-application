package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JdbcTemplateBuilder
 * Builder for JdbcTemplate and DataSource
 */
public class JdbcTemplateBuilder {

    public DataSource getNewDataSource(DataSourceConfig dataSourceConfig){
        return DataSourceBuilder.create()
                .url(dataSourceConfig.getUrl())
                .username(dataSourceConfig.getUsername())
                .password(dataSourceConfig.getPassword())
                .build();
    }

    public JdbcTemplate build(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
