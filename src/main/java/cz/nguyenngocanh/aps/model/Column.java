package cz.nguyenngocanh.aps.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Column
 * Column data type
 */
public class Column<T> {
    public static final String TYPE_NUMBER = "NUMBER";
    private String columnName;
    private String columnType;
    private List<T> data;
    private BigDecimal dataMaxValue;
    private BigDecimal dataMinValue;
    private BigDecimal dataMedianValue;

    public String getColumnName() {
        return columnName;
    }

    public Column<T> setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnType() {
        return columnType;
    }

    public Column setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public Column<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public BigDecimal getDataMaxValue() {
        return dataMaxValue;
    }

    public Column<T> setDataMaxValue(BigDecimal dataMaxValue) {
        this.dataMaxValue = dataMaxValue;
        return this;
    }

    public BigDecimal getDataMinValue() {
        return dataMinValue;
    }

    public Column<T> setDataMinValue(BigDecimal dataMinValue) {
        this.dataMinValue = dataMinValue;
        return this;
    }

    public BigDecimal getDataMedianValue() {
        return dataMedianValue;
    }

    public Column<T> setDataMedianValue(BigDecimal dataMedianValue) {
        this.dataMedianValue = dataMedianValue;
        return this;
    }
}
