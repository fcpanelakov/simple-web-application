package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.rowmappers.DatabaseQueryConfig;
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
    }

    public List<V> values(){
        return jdbcTemplate.query("SELECT * FROM " + tableName, rowMapper);
    }

    public void remove(K key){
        jdbcTemplate.update("DELETE FROM " + tableName + " WHERE ID = ?", key);
    }

    public void update(K key, V value){
        jdbcTemplate.update(rowMapper.getUpdateQuery(), rowMapper.updatePreparedStatement(key, value));
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
            return null;
        }
    }

    public void clear(){
        jdbcTemplate.execute("DELETE FROM " + tableName);
    }
}
