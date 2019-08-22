package cz.nguyenngocanh.aps.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTemplateBuilder {
    @ConfigurationProperties(prefix = "spring.datasource")
    private DataSource getDataSource(){
        return DataSourceBuilder.create().build();
    }

    public JdbcTemplate build(){
        return new JdbcTemplate(getDataSource());
    }
}
