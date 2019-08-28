package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.UnitTestBase;
import cz.nguyenngocanh.aps.model.DataSourceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.unitils.reflectionassert.ReflectionAssert;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * ConnectionRestControllerTest
 */
public class ConnectionRestControllerTest extends UnitTestBase {
    private static final String CREATE_URL = "/connection/create";
    private static final String CONNECTION_LIST_URL = "/connection/list";
    private static final String DELETE_URL = "/connection/delete";
    private static final String UPDATE_URL = "/connection/update/firstConnection";

    @Test
    public void createControllerTest() {
        connectionMap.clear();
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

        EntityExchangeResult<List<DataSourceConfig>> result = webTestClient.get()
                .uri(CONNECTION_LIST_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<DataSourceConfig>>() {
                })
                .returnResult();

        List<DataSourceConfig> testList = new ArrayList<>();
        testList.add(dataSourceConfig);
        ReflectionAssert.assertReflectionEquals(testList, result.getResponseBody());
    }

    @Test
    public void deleteConnectionControllerTest() {
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

        ReflectionAssert.assertReflectionEquals(connectionMap.get("firstConnection"), dataSourceConfig);

        DataSourceConfig secondDataSourceConfig = new DataSourceConfig()
                .setConnectionName("secondConnection")
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

        ReflectionAssert.assertReflectionEquals(connectionMap.get("secondConnection"), secondDataSourceConfig);
    }
}
