package com.example.cutpet.Services;

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

public class MainServices extends Activity {

	Services service = new Services();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_services);
        ImageButton addservicebutton = (ImageButton)findViewById(R.id.addbutton);
        ImageButton cleanservicebutton = (ImageButton)findViewById(R.id.cleanfields);
        final ModelHandler db = new ModelHandler(this);

        if ( getdataService() != null ) {
            service = db.getService(getdataService());
            set_field_service(service);
        } else {
            // damos error
        }

        addservicebutton.setOnClickListener(
        		new View.OnClickListener() {
        			@Override
        			public void onClick(View v) { 
        				ProgressDialog dialog = new ProgressDialog(MainServices.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
    			        Log.d("Insert: ", "Inserting ..");
    			        get_field_service();
    			        db.addService(MainServices.this.service);

    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }

                        Intent BackListService = new Intent(MainServices.this, ListServices.class);
                        BackListService.putExtra("PetId", service.get_data("Pet_Id"));
                        MainServices.this.startActivity(BackListService);
        			}
        		}
        );
        
        cleanservicebutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clean_field_service();
					}
				}
        );
        
    }

    private void clean_field_service() {
    	EditText ServiceDateField = (EditText)findViewById(R.id.ServiceDateField);

        ServiceDateField.setText(null);
        
        service = null;
    }
    
    private void set_field_service(Services service) {
    	EditText ServiceDateField = (EditText)findViewById(R.id.ServiceDateField);
    	
        ServiceDateField.setText(service.get_data("Service_Date"));
    } 
    
    private void get_field_service() {
    	
    	EditText ServiceDateField = (EditText)findViewById(R.id.ServiceDateField);

        service.add_data("Service_Date", ServiceDateField.getText().toString());
        service.add_data("Pet_Id", getdataPet());
    }
    
	private String getdataService() {

		String ServiceId = null;

        if ( getIntent().getExtras() != null ) {
            ServiceId = getIntent().getExtras().getString("ServiceId");
        }

		return(ServiceId); 	
	}

    private String getdataPet() {

        String PetId = null;

        if ( getIntent().getExtras() != null ) {
            PetId = getIntent().getExtras().getString("PetId");
            Log.d("getdataPet","Getting extra param PetId: " + getIntent().getExtras().getString("PetId"));
        }
        return(PetId);
    }
}
