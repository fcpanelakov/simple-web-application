package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.jdbc.DataSourceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.unitils.reflectionassert.ReflectionAssert;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
public class ConnectionRestControllerTest extends UnitTestBase {
    private static final String CREATE_URL = "/connection/create";
    private static final String CONNECTION_LIST_URL = "/connection/list";
    private static final String DELETE_URL = "/connection/delete";
    private static final String UPDATE_URL = "/connection/update";

    @Test
    public void createControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        EntityExchangeResult<Set> result = webTestClient.post()
                .uri(CREATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dataSourceConfig), DataSourceConfig.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Set.class).returnResult();
        ReflectionAssert.assertReflectionEquals(result.getResponseBody(), Arrays.asList("firstConnection"));
    }

    @Test
    public void getConnectionListControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        connectionMap.put("firstConnection", jdbcTemplateBuilder.build(dataSource));

        EntityExchangeResult<Set> result = webTestClient.get()
                .uri(CONNECTION_LIST_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Set.class).returnResult();
        ReflectionAssert.assertReflectionEquals(result.getResponseBody(), Arrays.asList("firstConnection"));
    }

    @Test
    public void deleteConnectionControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        connectionMap.put("firstConnection", jdbcTemplateBuilder.build(dataSource));

        Assert.assertNotNull(connectionMap.get("firstConnection"));

        webTestClient.delete()
                .uri(DELETE_URL + "/firstConnection")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        Assert.assertNull(connectionMap.get("firstConnection"));
    }

    @Test
    public void updateConnectionControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        DataSource dataSource = jdbcTemplateBuilder.getNewDataSource(dataSourceConfig);
        connectionMap.put("firstConnection", jdbcTemplateBuilder.build(dataSource));
        DataSourceConfig secondDataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa2")
                .setPassword("password2");
        DataSource secondDataSource = jdbcTemplateBuilder.getNewDataSource(secondDataSourceConfig);
        webTestClient.post()
                .uri(UPDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(secondDataSourceConfig), DataSourceConfig.class)
                .exchange()
                .expectStatus().isOk();
        ReflectionAssert.assertReflectionEquals(connectionMap.get("firstConnection"), jdbcTemplateBuilder.build(secondDataSource));
    }
}
