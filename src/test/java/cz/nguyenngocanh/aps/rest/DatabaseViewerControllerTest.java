package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.UnitTestBase;
import cz.nguyenngocanh.aps.model.Column;
import cz.nguyenngocanh.aps.model.DataSourceConfig;
import cz.nguyenngocanh.aps.model.PrimaryKey;
import cz.nguyenngocanh.aps.model.TableInformation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.Arrays;
import java.util.List;

/**
 * DatabaseViewerControllerTest
 */
public class DatabaseViewerControllerTest extends UnitTestBase {
    private static final String GET_SCHEMAS_URL = "/database/firstConnection/schemas";
    private static final String GET_TABLES_URL = "/database/firstConnection/tables";
    private static final String GET_COLUMNS_URL = "/database/firstConnection/TEST/columns";
    private static final String GET_TABLE_INFO_URL = "/database/firstConnection/TEST/tableinfo";


    @Test
    public void getSchemasControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);

        EntityExchangeResult<List<String>> schemas = webTestClient.get()
                .uri(GET_SCHEMAS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .returnResult();
        List<String> expectedSchemas = Arrays.asList("INFORMATION_SCHEMA", "PUBLIC", "SYS");
        ReflectionAssert.assertReflectionEquals(expectedSchemas, schemas.getResponseBody());
    }

    @Test
    public void getTablesControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);

        EntityExchangeResult<List<String>> tables = webTestClient.get()
                .uri(GET_TABLES_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .returnResult();
        List<String> expectedTables = Arrays.asList("FUNCTION_COLUMNS", "CONSTANTS", "SEQUENCES", "RIGHTS", "TRIGGERS", "CATALOGS",
                "CROSS_REFERENCES", "SETTINGS", "FUNCTION_ALIASES", "VIEWS", "TYPE_INFO", "CONSTRAINTS", "COLUMNS", "LOCKS",
                "KEY_COLUMN_USAGE", "DOMAINS", "SCHEMATA", "COLUMN_PRIVILEGES", "HELP", "SESSION_STATE", "TABLE_PRIVILEGES",
                "REFERENTIAL_CONSTRAINTS", "TABLE_TYPES", "TABLES", "QUERY_STATISTICS", "ROLES", "SESSIONS", "IN_DOUBT", "USERS",
                "COLLATIONS", "SYNONYMS", "TABLE_CONSTRAINTS", "INDEXES", "TEST", "ALL_CONSTRAINTS", "ALL_TABLES", "USER_TAB_COLS",
                "CONNECTIONS", "DBA_USERS");
        ReflectionAssert.assertReflectionEquals(expectedTables, tables.getResponseBody());
    }

    //TODO: ASSERT
    @Test
    public void getColumnsControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);

        EntityExchangeResult<List<String>> columns = webTestClient.get()
                .uri(GET_COLUMNS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<String>>() {
                })
                .returnResult();
        List<String> expectedColumns = Arrays.asList("TEST_ONE", "TEST_TWO", "TEST_THREE", "TEST_FOUR", "TEST_FIVE");
        ReflectionAssert.assertReflectionEquals(expectedColumns, columns.getResponseBody());
    }

    //TODO: ASSERT
    @Test
    public void getTableInfoControllerTest() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setConnectionName("firstConnection")
                .setUrl("jdbc:h2:mem:testdb")
                .setUsername("sa")
                .setPassword("password");
        connectionMap.put(dataSourceConfig);

        EntityExchangeResult<TableInformation> tableInfo = webTestClient.get()
                .uri(GET_TABLE_INFO_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TableInformation.class)
                .returnResult();
        List<Column> expectedColumns = Arrays.asList(new Column().setColumnType("VARCHAR").setData(Arrays.asList("ONE")),
                new Column().setColumnType("VARCHAR").setData(Arrays.asList("TWO")),
                new Column().setColumnType("DATE").setData(Arrays.asList("2019-08-19")),
                new Column().setColumnType("VARCHAR").setData(Arrays.asList("FOUR")),
                new Column().setColumnType("DATE").setData(Arrays.asList("2019-08-19")));
        List<PrimaryKey> expectedPrimaryKeys = Arrays.asList(new PrimaryKey().setName("TEST_PK").setType("PRIMARY KEY"));
        TableInformation expectedTableInformation = new TableInformation()
                .setColumnNumber(5)
                .setColumns(expectedColumns)
                .setPrimaryKeys(expectedPrimaryKeys);
        ReflectionAssert.assertReflectionEquals(expectedTableInformation, tableInfo.getResponseBody());
    }
}
