package cz.nguyenngocanh.aps.rowmappers;


import org.springframework.jdbc.core.RowMapper;

import java.util.Map;

/**
 * Row mapper plus parameters for simple insert
 * @param <V>
 */
public interface RowMapperPlusPara<V> extends RowMapper<V> {
    Map<String, Object> getParameters(V value);
}
