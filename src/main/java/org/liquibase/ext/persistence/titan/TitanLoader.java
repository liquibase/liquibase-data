package org.liquibase.ext.persistence.titan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class TitanLoader {
    public String Context;
    public String Host;
    public String Port;
    public String Type;

    public TitanLoader(String context, String host, String port, String type) {
        this.Context = context;
        this.Host = host;
        this.Port = port;
        this.Type = type;
    }

    public static TitanLoader load() {
        String home = System.getProperty("user.home");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(home + "/.titan/config"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Map<String, Object> contexts = oMapper.convertValue(data.get("contexts"), Map.class);
        Iterator<Map.Entry<String, Object>> iterator = contexts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            TitanContext context = oMapper.convertValue(entry.getValue(), TitanContext.class);
            if (entry.getValue().toString().contains("default=true")) {
                return new TitanLoader(entry.getKey(), context.host, context.port, context.type);
            }
        }
        return null;
    }
}

class TitanContext {
    public String host;
    public String port;
    public String type;
}