package cz.nguyenngocanh.aps.jdbc;

import cz.nguyenngocanh.aps.model.TableInformation;

import java.util.List;

/**
 * DatabaseViewerManager
 * Viewer manager interface
 */
public interface DatabaseViewerManager {
    List<String> getSchemas();
    List<String> getTables();
    List<String> getColumns(String tableName);
    TableInformation getTableInformation(String tableName);
}
