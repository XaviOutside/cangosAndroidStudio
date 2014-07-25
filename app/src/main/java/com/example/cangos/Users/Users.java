package com.example.cangos.Users;

import com.example.cangos.Common.ModelHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;

public class Users {

	private Map<String,String> properties;

    public Users() {
    	this.properties = new HashMap<String,String>();
    }
	    
    public void add_data(String key, String value) {
    	this.properties.put(key, value);
    }
	    
    public String get_data(String key) {
    	return this.properties.get(key);
    }

}
