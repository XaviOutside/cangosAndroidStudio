package com.example.cangos;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainPets extends Activity {	

	Pets pet = new Pets();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pets);
        Button addpetbutton = (Button)findViewById(R.id.MainPetsButton2); //Add pets
        Button deletepetbutton = (Button)findViewById(R.id.MainPetsButton1); //Delete pets
        Button searchpetbutton = (Button)findViewById(R.id.MainPetsButton3); //Find pets
        Button gotopetservicebutton = null;
        Button cleanpetbutton = null;
        Button editpetbutton = null;
        final ModelHandler db = new ModelHandler(MainPets.this, "CANGOS", null, 4);


        if ( getdataUser() != null ) {
        	Users user = db.getUser(getdataUser());
            //damos error
        }
        
        addpetbutton.setOnClickListener(
        		new View.OnClickListener() {
        			@Override
        			public void onClick(View v) { 
        				ProgressDialog dialog = new ProgressDialog(MainPets.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
       			        Log.d("MainPets: ", "Inserting .."); 
    			        Pets pet = get_field_pet();
                        pet.add_data("User_id",getdataUser());
    			        db.addPet(pet);
    			        Log.d("MainPets: ", "Pet insertado: " + MainPets.this.pet.get_data("Id"));  
    			        
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
        			}
        		}
        );
        
        deletepetbutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
        				ProgressDialog dialog = new ProgressDialog(MainPets.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        Log.d("MainPets: ", "Deleting .."); 
    			        if ( pet != null ) {
    			        	db.deletePet(pet);
    			        }
    			        Log.d("MainPets: ", "Deleting id: " + pet.get_data("Id")); 
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
    			        clean_field_pet();
					}
				}
        );
        
        searchpetbutton.setOnClickListener(
        	new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
    				ProgressDialog dialog = new ProgressDialog(MainPets.this);
					dialog.setMessage("Conectando con modelo...");
			        dialog.show();
			        
			        pet = get_field_pet();
			        
			        final List<Pets> pets = db.searchPet(pet);
			        final String[] items = new String[pets.size()];
			        
			        for (int i=0; i< pets.size(); i++) {
			            String log = "Id: "+pets.get(i).get_data("Id")+" ,Alias: " + pets.get(i).get_data("Alias")+" ,Age: " + pets.get(i).get_data("Age");
			            Log.d("Name: ", log);			            			            
			            items[i] = pets.get(i).get_data("Alias") + " " + pets.get(i).get_data("Age");
			        }  
	
			        AlertDialog.Builder builder = new AlertDialog.Builder(MainPets.this);
			        if ( pets.size() > 0 ) {
			        	builder.setTitle("Elige mascota:");
			        	builder.setItems(items, new DialogInterface.OnClickListener() {
			        		public void onClick(DialogInterface dialog, int item) {
			        			pet = pets.get(item);
			        			setdata(pet);
			        			dialog.cancel();
			        		}
			        	});
			        } else {
			        	builder.setTitle("No se han encontrado resultados.");
			        	builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			        		public void onClick(DialogInterface dialog, int id) {
			        			dialog.cancel();
			        		}
			        	});
			        }
		        	AlertDialog alert = builder.create();
		        	alert.show();
		        	
			        if (dialog.isShowing()) {
        	            dialog.dismiss();
        	        }
				}
			}
        );
        
    /*gotopetservicebutton.setOnClickListener(
        	new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
			        if (pet.get_data("Id") != null) {
        				Intent MService = new Intent(MainPets.this, MainPets.class);
        				MService.putExtra("PetId", pet.get_data("Id"));
        		        MainPets.this.startActivity(MService);
			        } else {
			        	ProgressDialog dialog = new ProgressDialog(MainPets.this);
						dialog.setMessage("Debes buscar la mascota primero.");
				        dialog.show();
			        }
				}
			}
		);
        
        cleanpetbutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clean_field_pet();
					}
				}
        );
        
        editpetbutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String IdPet = null;
        				ProgressDialog dialog = new ProgressDialog(MainPets.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
    			        Log.d("Edit: ", "Editing ..");
    			        IdPet = getdataPet(pet);
    			        if ( IdPet == null ) {
    			        	Log.d("Edit: ", "No se encuentra id de la mascota ..");
    			        	dialog.setMessage("No se encuentra id de la mascota...");
    			        	dialog.show();
    			        } else {
    			        	pet = get_field_pet();
    			        	pet.add_data("Id",IdPet);
    			        	Log.d("Edit: ", "Updating mascota con id: "+pet.get_data("Id"));
    			        	db.updatePet(pet);
    			        	//gotopetservicebutton.setEnabled(true);
    			        }
    			        
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }					}
				}
        );*/
        
    }

    private void clean_field_pet() {
        EditText txt1 = (EditText)findViewById(R.id.MainPetsField1);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
    	
        txt1.setText(null);
        txt2.setText(null);
        
        pet = null;
    }
	
	private Pets get_field_pet() {
		
        EditText txt1 = (EditText)findViewById(R.id.MainPetsField1);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        
		String alias = txt1.getText().toString();
        String age = txt2.getText().toString();
        //validate pets params
        final Pets pet = new Pets();
        pet.add_data("Alias", alias);
        pet.add_data("Age", age);
        return pet;
	}
	
	private void setdata(Pets pet) {
		
        EditText txt1 = (EditText)findViewById(R.id.MainPetsField1);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        
        MainPets.this.pet = pet;
		txt1.setText(pet.get_data("Alias"));
		txt2.setText(pet.get_data("Age"));
		//Habilitar bot�n servicios
	}
	
	private String getdataUser() {

		String UserId = null;
		
		if ( getIntent().getExtras() != null ) {
			UserId = getIntent().getExtras().getString("UserId");
		}
		return(UserId); 	
	}

	private String getdataPet(Pets pet) {

		String PetId = null;
		
		if ( pet.get_data("Id") != null ) {
			PetId = pet.get_data("Id");
		}
		return(PetId); 	
	}
	
}
