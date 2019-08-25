package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import cz.nguyenngocanh.aps.jdbc.MapStore;
import cz.nguyenngocanh.aps.model.DataSourceConfig;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class UnitTestBase {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected MapStore<String, DataSourceConfig> connectionMap;
    @Autowired
    protected JdbcTemplateBuilder jdbcTemplateBuilder;

    @Before
    public void init(){
        connectionMap.clear();
    }
}
