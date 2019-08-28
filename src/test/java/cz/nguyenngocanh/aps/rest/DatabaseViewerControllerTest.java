package cz.nguyenngocanh.aps.rest;

import cz.nguyenngocanh.aps.UnitTestBase;
import cz.nguyenngocanh.aps.model.Column;
import cz.nguyenngocanh.aps.model.PrimaryKey;
import cz.nguyenngocanh.aps.model.TableInformation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.unitils.reflectionassert.ReflectionAssert;

import java.math.BigDecimal;
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
    private static final String GET_MEDIAN_URL = "/database/firstConnection/TEST/TEST_THREE/median";
    private static final String GET_MAX_URL = "/database/firstConnection/TEST/TEST_THREE/max";
    private static final String GET_MIN_URL = "/database/firstConnection/TEST/TEST_THREE/min";


    @Test
    public void getSchemasControllerTest() {
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

    @Test
    public void getColumnsControllerTest() {
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

    @Test
    public void getTableInfoControllerTest() {
        EntityExchangeResult<TableInformation> tableInfo = webTestClient.get()
                .uri(GET_TABLE_INFO_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TableInformation.class)
                .returnResult();

        //Create expected TableInformation
        List<Column> expectedColumns = Arrays.asList(
                new Column().setColumnName("TEST_ONE")
                        .setColumnType("VARCHAR")
                        .setData(Arrays.asList("FIVE","FOUR","ONE","THREE","TWO")),

                new Column().setColumnName("TEST_TWO")
                        .setColumnType("VARCHAR")
                        .setData(Arrays.asList("TWO", "TWO", "TWO", "TWO", "TWO")),

                new Column().setColumnName("TEST_THREE")
                        .setColumnType("NUMBER")
                        .setData(Arrays.asList(1, 2, 3, 3.4, 5.4))
                        .setDataMaxValue(new BigDecimal(5.4))
                        .setDataMinValue(new BigDecimal(1))
                        .setDataMedianValue(new BigDecimal(3)),

                new Column().setColumnName("TEST_FOUR")
                        .setColumnType("VARCHAR")
                        .setData(Arrays.asList("FOUR","FOUR","FOUR","FOUR","FOUR")),

                new Column().setColumnName("TEST_FIVE")
                        .setColumnType("DATE")
                        .setData(Arrays.asList("2019-08-19", "2019-08-19", "2019-08-19", "2019-08-19", "2019-08-19")));

        List<PrimaryKey> expectedPrimaryKeys = Arrays.asList(new PrimaryKey().setName("TEST_PK").setType("PRIMARY KEY"));
        TableInformation expectedTableInformation = new TableInformation()
                .setColumnNumber(5)
                .setColumns(expectedColumns)
                .setPrimaryKeys(expectedPrimaryKeys);
        ReflectionAssert.assertReflectionEquals(expectedTableInformation, tableInfo.getResponseBody());
    }

    @Test
    public void getMedianControllerTest() {
        EntityExchangeResult<BigDecimal> median = webTestClient.get()
                .uri(GET_MEDIAN_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class)
                .returnResult();

        Assert.assertEquals(new BigDecimal(3), median.getResponseBody());
    }

    @Test
    public void getMaxControllerTest() {
        EntityExchangeResult<BigDecimal> max = webTestClient.get()
                .uri(GET_MAX_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class)
                .returnResult();

        Assert.assertEquals(new BigDecimal("5.4").setScale(1), max.getResponseBody());
    }

    @Test
    public void getMinControllerTest() {
        EntityExchangeResult<BigDecimal> min = webTestClient.get()
                .uri(GET_MIN_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class)
                .returnResult();

        Assert.assertEquals(new BigDecimal(1), min.getResponseBody());
    }
}
