package com.example.cangos;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ModelHandler extends SQLiteOpenHelper {
 
    public ModelHandler(Context contexto, String nombre, CursorFactory factory, int version) {
        //super(context, "CANGOS", null, 4);
        super(contexto,nombre,factory,version);
    }

	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(User_id integer primary key autoincrement,User_name,User_address,User_phone,User_email);");
        db.execSQL("CREATE TABLE Pets(Pet_id integer primary key autoincrement, Pet_alias,Pet_age);");
        db.execSQL("CREATE TABLE Services(Service_id integer primary key autoincrement,Service_date);");
        db.execSQL("CREATE TABLE UsersPets(User_id integer, Pet_id integer);");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Users;");
        db.execSQL("DROP TABLE IF EXISTS Pets;");
        db.execSQL("DROP TABLE IF EXISTS Services;");
        db.execSQL("DROP TABLE IF EXISTS UsersPets;");
        // Create tables again
        onCreate(db);
    }
       
    // Adding new contact
    public Users addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put("User_name", user.get_data("Name"));
        values.put("User_address", user.get_data("Address"));
        values.put("User_phone", user.get_data("Phone"));
        values.put("User_email", user.get_data("Email"));
     
        // Inserting Row
        long id = db.insert("Users", null, values);
        db.close(); // Closing database connection
        user.add_data("Id", Long.toString(id));
        return user;
    }
    
    // Adding new pet
    public Pets addPet(Pets pet) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put("Pet_alias", pet.get_data("Alias"));
        values.put("Pet_age", pet.get_data("Age"));
        long id = db.insert("Pets", null, values);
        pet.add_data("Id", Long.toString(id));
        Log.d("addPet: ","return: " + id);
        values = new ContentValues();
        values.put("User_id", pet.get_data("User_id"));
        values.put("Pet_id", pet.get_data("Id"));
        id = db.insert("UsersPets", null, values);
        db.close(); // Closing database connection
        return pet;
    }
    
    // Adding new contact
    public void addService(Services service) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put("Service_date", service.get_data("Date"));
     
        // Inserting Row
        db.insert("Services", null, values);
        db.close(); // Closing database connection
    }
    
    // Getting single contact
    Users getUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String query = "SELECT * FROM Users WHERE User_id = " + id + ";";
    	Log.d("searchUser: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);

        if (cursor != null)
            cursor.moveToFirst();
 
        Users user = new Users();
        user.add_data("Id", (cursor.getString(0)));
        user.add_data("Name", (cursor.getString(1)));
        user.add_data("Address", cursor.getString(2));
        user.add_data("Phone", cursor.getString(3));
        user.add_data("Email", cursor.getString(4));
        
        Log.d("searchUser: ","Result Query: " + user.get_data("Id"));
        
        cursor.close();
        db.close();
        // return contact
        return user;
    }
    
    Pets getPet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String query = "SELECT * FROM Pets WHERE Pet_id = " + id + ";";
    	Log.d("searchPet: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);

        if (cursor != null)
            cursor.moveToFirst();
 
        Pets pet = new Pets();
        pet.add_data("Id", (cursor.getString(0)));
        pet.add_data("Name", (cursor.getString(1)));
        pet.add_data("Age", (cursor.getString(2)));
        
        cursor.close();
        db.close();
        // return contact
        return pet;
    }

    Services getService(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String query = "SELECT * FROM Services WHERE Service_id = " + id + ";";
    	Log.d("searchService: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);

        if (cursor != null)
            cursor.moveToFirst();
 
        Services service = new Services();
        service.add_data("Id", (cursor.getString(0)));
        service.add_data("Date", (cursor.getString(1)));
        
        cursor.close();
        db.close();
        // return contact
        return service;
    }
    
    // Find info user
    List<Users> searchUser(Users user) {
    	List<Users> userList = new ArrayList<Users>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = null;
    	
    	Log.d("searchUser: ","init");
    	query = "SELECT * FROM Users ";
    	//validate if all values are null
    	if ( user.get_data("Name") != null || user.get_data("Address") != null || user.get_data("Phone") != null || user.get_data("Email") != null ) {
    		query = query + "WHERE ";
    	}
    	if ( user.get_data("Name") != null ) {
    		query = query + "User_name like '%" + user.get_data("Name") + "%' ";
    	}
    	if ( user.get_data("Address") != null ) {
    		query = query + "AND User_address like '%" + user.get_data("Address") + "%' ";
    	}
    	if ( user.get_data("Phone") != null ) {
    		query = query + "AND User_phone like '%" + user.get_data("Phone") + "%' ";
    	}
    	if ( user.get_data("Email") != null ) {
    		query = query + "AND User_email like '%" + user.get_data("Email") + "%' ";
    	}
    	query = query + ";";
    	Log.d("searchUser: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users usertemp = new Users();
                usertemp.add_data("Id",cursor.getString(0));
                usertemp.add_data("Name",cursor.getString(1));
                usertemp.add_data("Address",cursor.getString(2));
                usertemp.add_data("Phone",cursor.getString(3));
                usertemp.add_data("Email",cursor.getString(4));
                // Adding contact to list
                userList.add(usertemp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return list   	
    	
    	Log.d("searchUser: ","Return: " + userList);
    	return userList;
    }
    
    
    // Find info pet
    List<Pets> searchPet(Pets pet) {
    	List<Pets> petList = new ArrayList<Pets>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = null;
    	
    	Log.d("searchPet: ","init");
        if ( pet.get_data("User_id") == null ) {
            query = "SELECT * FROM Pets WHERE 1=1";
        } else {
            query = "SELECT * FROM Pets p, UsersPets up WHERE p.Pet_id = up.Pet_id AND up.User_id = " + pet.get_data("User_id");
        }
    	if ( pet.get_data("Alias") != null ) {
    		query = query + " AND Pet_alias like '%" + pet.get_data("Alias") + "%'";
    	}
    	if ( pet.get_data("Age") != null ) {
    		query = query + " AND Pet_age like '%" + pet.get_data("Age") + "%'";
    	}
    	query = query + ";";
    	Log.d("searchPet: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pets pettemp = new Pets();
                pettemp.add_data("Id",cursor.getString(0));
                pettemp.add_data("Alias",cursor.getString(1));
                pettemp.add_data("Age",cursor.getString(2));
                // Adding contact to list
                petList.add(pettemp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return list   	
    	
    	Log.d("searchPet: ","Return: " + petList);
    	return petList;
    }
    
    List<Services> searchService(Services service) {
    	List<Services> serviceList = new ArrayList<Services>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = null;
    	
    	Log.d("searchService: ","init");
    	query = "SELECT * FROM Services ";
    	//validate if all values are null
    	if ( service.get_data("Alias") != null || service.get_data("Age") != null ) {
    		query = query + "WHERE ";
    	}
    	if ( service.get_data("Alias") != null ) {
    		query = query + "Service_alias like '%" + service.get_data("Alias") + "%' ";
    	}
    	if ( service.get_data("Age") != null ) {
    		query = query + "AND Service_age like '%" + service.get_data("Age") + "%' ";
    	}
    	query = query + ";";
    	Log.d("searchService: ","Query: " + query);
    	Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Services servicetemp = new Services();
                servicetemp.add_data("Id",cursor.getString(0));
                servicetemp.add_data("Alias",cursor.getString(1));
                servicetemp.add_data("Age",cursor.getString(2));
                // Adding contact to list
                serviceList.add(servicetemp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return list   	
    	
    	Log.d("searchService: ","Return: " + serviceList);
    	return serviceList;
    }
    
    // Getting All Contacts
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Users";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.add_data("Id",cursor.getString(0));
                user.add_data("Name",cursor.getString(1));
                user.add_data("Address",cursor.getString(2));
                user.add_data("Phone",cursor.getString(3));
                user.add_data("Email",cursor.getString(4));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return userList;
    }
 
    
    // Getting All Pets
    public List<Pets> getAllPets() {
        List<Pets> petList = new ArrayList<Pets>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Pets";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pets pet = new Pets();
                pet.add_data("Id",cursor.getString(0));
                pet.add_data("Alias",cursor.getString(1));
                pet.add_data("Age",cursor.getString(2));
                // Adding contact to list
                petList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return petList;
    }
    
    // Updating single contact
    public int updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put("User_id", user.get_data("Id"));
        values.put("User_name", user.get_data("Name"));
        values.put("User_address", user.get_data("Address"));
        values.put("User_phone", user.get_data("Phone"));
        values.put("User_email", user.get_data("Email"));
 
        // updating row
        return db.update("Users", values, "User_id = ?",
                new String[] { String.valueOf(user.get_data("Id")) });
    }
 
    
    // Updating single pet
    public int updatePet(Pets pet) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        //values.put("User_id", user.get_data("Id"));
        values.put("Pet_alias", pet.get_data("Alias"));
 
        // updating row
        return db.update("Pets", values, "Pet_id = ?",
                new String[] { String.valueOf(pet.get_data("Id")) });
    }
    
    // Updating single pet
    public int updateService(Services service) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        //values.put("User_id", user.get_data("Id"));
        values.put("Pet_alias", service.get_data("Alias"));
 
        // updating row
        return db.update("Services", values, "Service_id = ?",
                new String[] { String.valueOf(service.get_data("Id")) });
    }
    
    // Deleting single contact
    public void deleteUser(Users user) {
    	Log.d("deleteUser: ","init");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Users", "User_id = ?", new String[] { user.get_data("Id") });
        db.close();
    	Log.d("deleteUser: ","User id: " + user.get_data("Id") + ".");
    }
 
    
    // Deleting single pet
    public void deletePet(Pets pet) {
    	Log.d("deletePet: ","init");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Pets", "Pet_id = ?", new String[] { pet.get_data("Id") });
        db.close();
    	Log.d("deletePet: ","Pet id: " + pet.get_data("Id") + ".");
    }
    
    public void deleteService(Services service) {
    	Log.d("deleteService: ","init");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Services", "Service_id = ?", new String[] { service.get_data("Id") });
        db.close();
    	Log.d("deleteService: ","Service id: " + service.get_data("Id") + ".");
    }
    
    // Deleting single contact
    public void deleteBD() {
    	Log.d("deleteBD: ","init");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Users", null, null);
        db.close();
    	Log.d("deleteBD: ","void");
    }
 
    // Getting contacts Count
    public int getUsersCount() {
    	int numero;
    	
    	Log.d("getUsersCount: ","init");
        String countQuery = "SELECT  * FROM Users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        numero = cursor.getCount();
        cursor.close();
        db.close();
        Log.d("getUsersCount: ","return: " + Integer.toString(numero));
 
        // return count
        return numero;
    }
    
    // Getting contacts Count
    public int getPetsCount() {
    	int numero;
    	
    	Log.d("getPetsCount: ","init");
        String countQuery = "SELECT  * FROM Pets";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        numero = cursor.getCount();
        cursor.close();
        db.close();
        Log.d("getPetsCount: ","return: " + Integer.toString(numero));
 
        // return count
        return numero;
    }
    
}
