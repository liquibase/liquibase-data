package org.liquibase.ext.persistence.titan;

public class JDBCHelper {
    private final String Protocol;
    private final String Driver;
    private final String Host;
    private final String Port;
    private final String Database;

    public JDBCHelper(String protocol, String driver, String host, String port, String databse) {
        Protocol = protocol;
        Driver = driver;
        Host = host;
        Port = port;
        Database = databse;
    }

    public String toString() {
        return String.format("%s:%s://%s:%s/%s", Protocol, Driver, Host, Port, Database);
    }
}
