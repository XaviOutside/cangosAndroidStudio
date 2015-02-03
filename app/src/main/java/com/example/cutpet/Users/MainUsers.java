package com.example.cutpet.Users;

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

public class MainUsers extends Activity {

	Users user = new Users();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_users);
        ImageButton adduserbutton = (ImageButton)findViewById(R.id.addbutton);
        ImageButton cleanuserbutton = (ImageButton)findViewById(R.id.gotopet);
        final ModelHandler db = new ModelHandler(this);


        adduserbutton.setOnClickListener(
        		new View.OnClickListener() {
        			@Override
        			public void onClick(View v) { 
        				ProgressDialog dialog = new ProgressDialog(MainUsers.this);
    					dialog.setMessage("Conectando con modelo...");
    			        dialog.show();
    			        
    			        Log.d("Insert: ", "Inserting ..");
    			        user = get_field_user();
    			        db.addUser(user);

    			        if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }

                        Intent BackMainUserList = new Intent(MainUsers.this, ListUsers.class);
                        MainUsers.this.startActivity(BackMainUserList);
        			}
        		}
        );
        
        cleanuserbutton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						clean_field_user();
					}
				}
        );
        
    }

    private void clean_field_user() {
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);
    	
        txt1.setText(null);
        txt2.setText(null);
        txt3.setText(null);
        txt4.setText(null);
        
        user = null;
    }
    
    private void set_field_user(Users user) {
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);
    	
        txt1.setText(user.get_data("Name"));
        txt2.setText(user.get_data("Address"));
        txt3.setText(user.get_data("Phone"));
        txt4.setText(user.get_data("Email"));
    } 
    
    private Users get_field_user() {
    	
    	EditText txt1 = (EditText)findViewById(R.id.NamePetField);
        EditText txt2 = (EditText)findViewById(R.id.MainPetsField2);
        EditText txt3 = (EditText)findViewById(R.id.editText3);
        EditText txt4 = (EditText)findViewById(R.id.editText4);

    	String name = txt1.getText().toString();
        String address = txt2.getText().toString();
        String phone = txt3.getText().toString();
        String email = txt4.getText().toString();

        Users user = new Users();
        user.add_data("Name", name);
        user.add_data("Address", address);
        user.add_data("Phone", phone);
        user.add_data("Email", email);
        
        return(user);
    }

}
