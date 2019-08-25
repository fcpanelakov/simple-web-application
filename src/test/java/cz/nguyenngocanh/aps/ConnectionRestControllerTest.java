package cz.nguyenngocanh.aps;

import cz.nguyenngocanh.aps.model.DataSourceConfig;
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

import java.util.*;

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

      webTestClient.post()
                .uri(CREATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dataSourceConfig), DataSourceConfig.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getConnectionListControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);

        EntityExchangeResult<List<DataSourceConfig>> result = webTestClient.get()
                .uri(CONNECTION_LIST_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DataSourceConfig.class)
                .returnResult();

        List<DataSourceConfig> testList = new ArrayList<>();
        testList.add(dataSourceConfig);
        ReflectionAssert.assertReflectionEquals(testList, result.getResponseBody());
    }

    @Test
    public void deleteConnectionControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");

        connectionMap.put(dataSourceConfig);

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

        connectionMap.put(dataSourceConfig);
        ReflectionAssert.assertReflectionEquals(connectionMap.get("firstConnection"), dataSourceConfig);

        DataSourceConfig secondDataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa2")
                .setPassword("password2");

        webTestClient.post()
                .uri(UPDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(secondDataSourceConfig), DataSourceConfig.class)
                .exchange()
                .expectStatus().isOk();

        ReflectionAssert.assertReflectionEquals(connectionMap.get("firstConnection"), secondDataSourceConfig);
    }
}
