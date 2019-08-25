package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.HasId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

public class MapStore<K, V extends HasId<K>> {
    private JdbcTemplate jdbcTemplate;
    private RowMapperPlusPara<V> rowMapper;
    private String tableName;


    public MapStore(RowMapperPlusPara<V> rowMapper, String tableName, DataSource dataSource, JdbcTemplateBuilder jdbcTemplateBuilder) {
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
        remove(key);
        put(value);
    }

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
