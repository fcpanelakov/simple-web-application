package cz.nguyenngocanh.aps.jdbc;

public class DataSourceConfig {
    private String connectionName;
    private String url;
    private String username;
    private String password;

    public DataSourceConfig() {
    }

    public DataSourceConfig(String connectionName, String url, String username, String password) {
        this.connectionName = connectionName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public DataSourceConfig setConnectionName(String connectionName) {
        this.connectionName = connectionName;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DataSourceConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DataSourceConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DataSourceConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "connectionName='" + connectionName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
