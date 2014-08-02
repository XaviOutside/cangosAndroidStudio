package com.example.cangos.Pets;

import java.util.HashMap;
import java.util.Map;

public class Pets {

    private Map<String, String> properties;

    public Pets() {
        this.properties = new HashMap<String, String>();
    }

    public void add_data(String key, String value) {
        this.properties.put(key, value);
    }

    public String get_data(String key) {
        return this.properties.get(key);
    }

}
