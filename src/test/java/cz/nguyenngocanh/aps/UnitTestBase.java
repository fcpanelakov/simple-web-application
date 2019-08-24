package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;

public class UnitTestBase {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected HashMap<String, JdbcTemplate> connectionMap;
    @Autowired
    protected JdbcTemplateBuilder jdbcTemplateBuilder;

    @Before
    public void init(){
        connectionMap.clear();
    }
}
