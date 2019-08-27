package cz.nguyenngocanh.aps.rowmappers;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.Map;

/**
 * DatabaseQueryConfig
 * Row mapper plus parameters for simple insert
 * @param <V> Type of specific object
 * @param <K> Type of id of specific object
 */
public interface DatabaseQueryConfig<V, K> extends RowMapper<V> {
    Map<String, Object> getParameters(V value);
    PreparedStatementSetter updatePreparedStatement(K id, V dataSourceConfig);
    String getUpdateQuery();
}
