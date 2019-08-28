package cz.nguyenngocanh.aps.model;

import java.util.List;

/**
 * TableInformation
 * Information of specific table
 * columnNumber - Number of columns of specific table
 * primaryKeys - All primary keys of specific table
 * columns - All columns of specific table
 */
public class TableInformation {
    private Integer columnNumber;
    private List<PrimaryKey> primaryKeys;
    private List<Column> columns;

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public TableInformation setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
        return this;
    }

    public List<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public TableInformation setPrimaryKeys(List<PrimaryKey> primaryKeys) {
        this.primaryKeys = primaryKeys;
        return this;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public TableInformation setColumns(List<Column> columns) {
        this.columns = columns;
        return this;
    }
}
