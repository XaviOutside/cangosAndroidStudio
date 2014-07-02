/* 
 * Version 1
 */
package com.example.cangos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
	
public class Services implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String,String> properties;

    public Services() {
    	this.properties = new HashMap<String,String>();
    }
	    
    public void add_data(String key, String value) {
    	this.properties.put(key, value);
    }
	    
    public String get_data(String key) {
    	return this.properties.get(key);
    }	

}
