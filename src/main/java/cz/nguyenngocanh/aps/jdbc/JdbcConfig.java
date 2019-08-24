package cz.nguyenngocanh.aps.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;


public class JdbcConfig {
    @Bean
    public JdbcTemplateBuilder setTemplateBuilder(){
        return new JdbcTemplateBuilder();
    }

    @Bean
    public HashMap<String, JdbcTemplate> connectionMap(){
        return new HashMap<>();
    }

}
