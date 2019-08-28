package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.jdbc.JdbcTemplateBuilder;
import cz.nguyenngocanh.aps.jdbc.MapStore;
import cz.nguyenngocanh.aps.model.DataSourceConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * UnitTestBase
 * Base class for rest unit tests
 */
@SpringBootTest
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
public abstract class UnitTestBase {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected MapStore<String, DataSourceConfig> connectionMap;
    @Autowired
    protected JdbcTemplateBuilder jdbcTemplateBuilder;

    @Before
    public void init(){
        connectionMap.clear();

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);
    }
}
