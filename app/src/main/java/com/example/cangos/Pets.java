/* 
 * Version 1
 */
package com.example.cangos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
	
public class Pets implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String,String> properties;

	public Pets() {
    	this.properties = new HashMap<String,String>();
    }
		    
	public void add_data(String key, String value) {
    	this.properties.put(key, value);
    }
		    
    public String get_data(String key) {
    	return this.properties.get(key);
    }

    public List<Pets> get_pets(ModelHandler db) {
        return db.searchPet(this);
    }
}
