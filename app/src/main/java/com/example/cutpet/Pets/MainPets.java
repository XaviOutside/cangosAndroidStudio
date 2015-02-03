package com.example.cutpet.Pets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cutpet.Common.ModelHandler;
import com.example.cutpet.R;
import com.example.cutpet.Users.Users;

public class MainPets extends Activity {	

	Pets pet = new Pets();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pets);
        ImageButton addpetbutton = (ImageButton)findViewById(R.id.addbutton);
        ImageButton cleanpetbutton = (ImageButton)findViewById(R.id.cleanfields);
        final ModelHandler db = new ModelHandler(this);

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
    			        get_field_pet();
                        MainPets.this.pet.add_data("User_id", getdataUser());
    			        db.addPet(MainPets.this.pet);
    			        Log.d("MainPets: ", "Pet insertado: " + MainPets.this.pet.get_data("Id"));  
    			        
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
                        Intent BackMainPetList = new Intent(MainPets.this, ListPets.class);
                        BackMainPetList.putExtra("UserId", pet.get_data("User_id"));
                        MainPets.this.startActivity(BackMainPetList);
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
        
    }

    private void clean_field_pet() {
        EditText NamePetField = (EditText)findViewById(R.id.NamePetField);
        EditText AgePetField = (EditText)findViewById(R.id.AgePetField);
        EditText WeightPetField = (EditText)findViewById(R.id.WeightPetField);
        EditText RacePetField = (EditText)findViewById(R.id.RacePetField);

        NamePetField.setText(null);
        AgePetField.setText(null);
        WeightPetField.setText(null);
        RacePetField.setText(null);
        
        pet = null;
    }
	
	private void get_field_pet() {
        EditText NamePetField = (EditText)findViewById(R.id.NamePetField);
        EditText AgePetField = (EditText)findViewById(R.id.AgePetField);
        EditText WeightPetField = (EditText)findViewById(R.id.WeightPetField);
        EditText RacePetField = (EditText)findViewById(R.id.RacePetField);

        pet.add_data("Alias", NamePetField.getText().toString());
        pet.add_data("Age", AgePetField.getText().toString());
        pet.add_data("Weight", WeightPetField.getText().toString());
        pet.add_data("Race", RacePetField.getText().toString());
	}
	
	private void setdata(Pets pet) {
        EditText NamePetField = (EditText)findViewById(R.id.NamePetField);
        EditText AgePetField = (EditText)findViewById(R.id.AgePetField);
        EditText WeightPetField = (EditText)findViewById(R.id.WeightPetField);
        EditText RacePetField = (EditText)findViewById(R.id.RacePetField);

        NamePetField.setText(this.pet.get_data("Alias"));
        AgePetField.setText(this.pet.get_data("Age"));
        WeightPetField.setText(this.pet.get_data("Weight"));
        RacePetField.setText(this.pet.get_data("Race"));
	}
	
	private String getdataUser() {

		String UserId = null;
		
		if ( getIntent().getExtras() != null ) {
			UserId = getIntent().getExtras().getString("UserId");
		}
		return(UserId); 	
	}
	
}
