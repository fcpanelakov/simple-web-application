package cz.nguyenngocanh.aps.model;

import java.util.List;

public class Column {
    String columnType;
    List<String> data;

    public String getColumnType() {
        return columnType;
    }

    public Column setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public List<String> getData() {
        return data;
    }

    public Column setData(List<String> data) {
        this.data = data;
        return this;
    }
}
