package cz.nguyenngocanh.aps.rowmappers;

import cz.nguyenngocanh.aps.model.PrimaryKey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper for {@link PrimaryKey}
 */
public class PrimaryKeyRowMapper implements RowMapper<PrimaryKey> {

    @Override
    public PrimaryKey mapRow(ResultSet resultSet, int i) throws SQLException {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setName(resultSet.getString("CONSTRAINT_NAME"));
        primaryKey.setType(resultSet.getString("CONSTRAINT_TYPE"));
        return primaryKey;
    }
}
