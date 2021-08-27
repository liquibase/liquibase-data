package org.liquibase.ext.persistence.titan;

public class TitanPort {
    public String protocol;
    public String port;

    public String getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
