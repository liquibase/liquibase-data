package org.liquibase.ext.persistence.titan;

public class JDBCHelper {
    private final String Protocol;
    private final String Driver;
    private final String Host;
    private String Port;
    private final String Database;

    public JDBCHelper(String protocol, String driver, String host, String port, String database) {
        Protocol = protocol;
        Driver = driver;
        Host = host;
        this.setPort(port);
        Database = database;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    // jdbc:postgresql://localhost:5432/postgres
    public String toString() {
        return String.format("%s:%s://%s:%s/%s", Protocol, Driver, Host, Port, Database);
    }

    // jdbc:postgresql://localhost:55663/postgres
    public static JDBCHelper fromString(String url){
        String split[] = url.split("://", 2);
        String front[] = split[0].split(":",2);
        String back[] = split[1].split("/", 2);
        String fullHost[] = back[0].split(":", 2);
        return new JDBCHelper(front[0], front[1],  fullHost[0], fullHost[1], back[1]);
    }
}
