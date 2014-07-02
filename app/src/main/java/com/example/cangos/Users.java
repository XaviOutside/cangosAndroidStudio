/* 
 * Version 1
 */
package com.example.cangos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Users> get_users(ModelHandler db) {
        return db.searchUser(this);
    }
}
