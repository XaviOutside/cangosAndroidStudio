package com.example.cangos.Pets;

import android.app.Activity;

import com.example.cangos.Common.ModelHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
	
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
