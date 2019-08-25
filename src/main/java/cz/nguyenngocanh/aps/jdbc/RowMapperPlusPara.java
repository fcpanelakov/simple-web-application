package cz.nguyenngocanh.aps.jdbc;


import org.springframework.jdbc.core.RowMapper;

import java.util.Map;


public interface RowMapperPlusPara<V> extends RowMapper<V> {
    Map<String, Object> getParameters(V value);
}
