package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.rowmappers.DatabaseQueryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * MapStore
 * Serve for manipulating with intern application database (CRUD)
 */
public class MapStore<K, V> {
    private static final Logger log = LoggerFactory.getLogger(MapStore.class);
    private JdbcTemplate jdbcTemplate;
    private DatabaseQueryConfig<V, K> rowMapper;
    private String tableName;


    public MapStore(DatabaseQueryConfig<V, K> rowMapper, String tableName, DataSource dataSource, JdbcTemplateBuilder jdbcTemplateBuilder) {
        this.jdbcTemplate = jdbcTemplateBuilder.build(dataSource);
        this.rowMapper = rowMapper;
        this.tableName = tableName;
    }


    public void put(V value) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(tableName).execute(rowMapper.getParameters(value));
        log.info("Insert successful");

    }

    public List<V> values(){
        return jdbcTemplate.query("SELECT * FROM " + tableName, rowMapper);
    }

    public void remove(K key){
        jdbcTemplate.update("DELETE FROM " + tableName + " WHERE ID = ?", key);
        log.info("Delete record with id: {} was successful", key);
    }

    public void update(K key, V value){
        jdbcTemplate.update(rowMapper.getUpdateQuery(), rowMapper.updatePreparedStatement(key, value));
        log.info("Update record with id: {} was successful", key);
    }

    /**
     * Get specific object from database
     * @param key
     * @return Specific object, if specific object is not in database return null
     */
    public V get(K key){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE ID = ?", rowMapper, key);
        }catch (EmptyResultDataAccessException e){
            log.info("Requested object with id: {} from database not found", key);
            return null;
        }
    }

    public void clear(){
        jdbcTemplate.execute("DELETE FROM " + tableName);
        log.info("All data from table deleted");
    }
}
