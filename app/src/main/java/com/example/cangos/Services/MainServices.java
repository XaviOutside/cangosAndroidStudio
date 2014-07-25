package com.example.cangos.Services;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cangos.Common.ModelHandler;
import com.example.cangos.R;

public class MainServices extends Activity {

	Services service = new Services();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_services);
        ImageButton addservicebutton = null;
        ImageButton deleteservicebutton = null;
        ImageButton searchservicebutton = null;
        ImageButton editservicebutton = null;
        //ImageButton gotopetservicebutton = null;
        ImageButton cleanservicebutton = null;
        final ModelHandler db = new ModelHandler(this);

        //gotopetuserbutton.setEnabled(false);

        addservicebutton.setOnClickListener(
        		new View.OnClickListener() {
        			@Override
        			public void onClick(View v) { 
        				ProgressDialog dialog = new ProgressDialog(MainServices.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
    			        Log.d("Insert: ", "Inserting ..");
    			        service = get_field_service();
    			        db.addService(service);
    			        //gotopetuserbutton.setEnabled(true);
    			            			        
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
        			}
        		}
        );
      
        deleteservicebutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
        				ProgressDialog dialog = new ProgressDialog(MainServices.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        if ( service != null ) {
    			        	db.deleteService(service);
    			        }
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
    			        clean_field_service();
    			        //gotopetuserbutton.setEnabled(false);
					}
				}
        );
        
        searchservicebutton.setOnClickListener(
        	new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
    				ProgressDialog dialog = new ProgressDialog(MainServices.this);
					dialog.setMessage("Conectando con modelo...");
			        dialog.show();
			        
			        service = get_field_service();
			        
			        final List<Services> services = db.searchService(service);
			        final String[] items = new String[services.size()];
			        
			        for (int i=0; i< services.size(); i++) {
			            String log = "Id: "+services.get(i).get_data("Id")+" ,Name: " + services.get(i).get_data("Name")+" ,Address: " + services.get(i).get_data("Address")+" ,Phone: " + services.get(i).get_data("Phone")+" ,Email: " + services.get(i).get_data("Email");
			            Log.d("Name: ", log);			            			            
			            items[i] = services.get(i).get_data("Name") + " " + services.get(i).get_data("Phone");
			        }  
			        
		        	AlertDialog.Builder builder = new AlertDialog.Builder(MainServices.this);
			        if ( services.size() > 0 ) {
			        	builder.setTitle("Elige servicio:");
			        	builder.setItems(items, new DialogInterface.OnClickListener() {
			        		public void onClick(DialogInterface dialog, int item) {
			        			service = services.get(item);
			        			set_field_service(service);
			        			//gotopetservicebutton.setEnabled(true);
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
			        if (user.get_data("Id") != null) {
        				Intent MPet = new Intent(MainServices.this, MainPets.class);
        				MPet.putExtra("UserId", user.get_data("Id"));
        		        MainServices.this.startActivity(MPet);
			        } else {
			        	ProgressDialog dialog = new ProgressDialog(MainServices.this);
						dialog.setMessage("Debes buscar un usuario primero.");
				        dialog.show();
			        }
				}
			}
		);*/
        
        cleanservicebutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clean_field_service();
					}
				}
        );
        
        editservicebutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String IdService = null;
        				ProgressDialog dialog = new ProgressDialog(MainServices.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
    			        Log.d("Edit: ", "Editing ..");
    			        IdService = getdataService(service);
    			        if ( IdService == null ) {
    			        	Log.d("Edit: ", "No se encuentra id del servicio ..");
    			        	dialog.setMessage("No se encuentra id del servicio...");
    			        	dialog.show();
    			        } else {
    			        	service = get_field_service();
    			        	service.add_data("Id",IdService);
    			        	Log.d("Edit: ", "Updating servicio con id: "+service.get_data("Id"));
    			        	db.updateService(service);
    			        	//gotopetuserbutton.setEnabled(true);
    			        }
    			        
    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }					}
				}
        );
        
    }

    private void clean_field_service() {
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);
    	
        txt1.setText(null);
        txt2.setText(null);
        txt3.setText(null);
        txt4.setText(null);
        
        service = null;
    }
    
    private void set_field_service(Services service) {
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);
    	
        txt1.setText(service.get_data("Name"));
        txt2.setText(service.get_data("Address"));
        txt3.setText(service.get_data("Phone"));
        txt4.setText(service.get_data("Email"));
    } 
    
    private Services get_field_service() {
    	
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);
        
    	String name = txt1.getText().toString();
        String address = txt2.getText().toString();
        String phone = txt3.getText().toString();
        String email = txt4.getText().toString();

        Services service = new Services();
        service.add_data("Name", name);
        service.add_data("Address", address);
        service.add_data("Phone", phone);
        service.add_data("Email", email);
        
        return(service);
    }
    
	private String getdataService(Services service) {

		String ServiceId = null;
		
		if ( service.get_data("Id") != null ) {
			ServiceId = service.get_data("Id");
		}
		return(ServiceId); 	
	}
}
