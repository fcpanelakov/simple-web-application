package cz.nguyenngocanh.aps.model;

import java.util.List;

public class TableInformation {
    Integer columnNumber;
    List<PrimaryKey> primaryKeys;
    List<Column> columns;

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
