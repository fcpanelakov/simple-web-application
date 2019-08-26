package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
import cz.nguyenngocanh.aps.rowmappers.DataSourceConfigRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JdbcConfig
 * Configurations for communication with databases
 */
public class JdbcConfig {
    @Bean
    public JdbcTemplateBuilder setTemplateBuilder() {
        return new JdbcTemplateBuilder();
    }

    @Bean
    public MapStore<String, DataSourceConfig> connectionMap(DataSource dataSource, JdbcTemplateBuilder jdbcTemplateBuilder) {
        return new MapStore<>(new DataSourceConfigRowMapper(), DataSourceConfig.TABLE_NAME, dataSource, jdbcTemplateBuilder);
    }

    @Bean
    public JdbcTemplate setJdbcTemplate(DataSource dataSource, JdbcTemplateBuilder jdbcTemplateBuilder) {
        return jdbcTemplateBuilder.build(dataSource);
    }

    @Bean
    public OracleDatabaseViewerManager setOracleDatabaseViewerManager(){
        return new OracleDatabaseViewerManager();
    }

}
