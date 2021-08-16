package org.example.config;

import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Config {
    private static final String SERVER_INI_FILE = "config.ini";
    private static Config instance;
    private Map<String, String> config;

    private Config() {
        try {
            Ini ini = new Ini(new File(SERVER_INI_FILE));
            config = new HashMap<>();
            config.put("jettyPort", ini.get("Jetty", "server"));
            config.put("thriftHost", ini.get("Thrift", "host"));
            config.put("thriftPort", ini.get("Thrift", "port"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public static Config getInstance(){
        if (instance == null) {
            return new Config();
        }
        return instance;
    }


}
