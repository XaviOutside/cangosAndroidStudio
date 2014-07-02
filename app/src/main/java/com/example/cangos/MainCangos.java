package com.example.cangos;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainCangos extends Activity {

    ModelHandler db = new ModelHandler(this, "CANGOS", null, 4);
    String[] items=null;
    Users user = new Users();
    List<Users> users = null;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cangos);
        EditText inputSearch = (EditText) findViewById(R.id.inputSearch);
        ImageButton adduserbutton = (ImageButton) findViewById(R.id.adduserbutton);

        users = user.get_users(db);
        items = create_user_list();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, items);
        show_user_to_list(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	adapter.getFilter().filter(cs);
            }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {				
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

        });
                
        adduserbutton.setOnClickListener(
            	new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
            				Intent MUsers = new Intent(MainCangos.this, MainUsers.class);
            		        MainCangos.this.startActivity(MUsers);
    				}
    			}
    	);

    }

    private String[] create_user_list() {
        items = new String[this.users.size()];

        for (int i=0; i< this.users.size(); i++) {
            items[i] = this.users.get(i).get_data("Name") + " " + this.users.get(i).get_data("Phone");
        }

        return items;
    }

    private void show_user_to_list(ArrayAdapter<String> adapter) {
        ListView userlist = (ListView)findViewById(R.id.list_view);
        userlist.setAdapter(adapter);
        userlist.setOnItemClickListener(new OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Log.d("setOnItemClickListener: ","init");
                                                Intent MPet = new Intent(MainCangos.this, ListPets.class);
                                                MPet.putExtra("UserId", users.get(position).get_data("Id"));
                                                Log.d("setOnItemClickListener: ","return"+ users.get(position).get_data("Id"));
                                                MainCangos.this.startActivity(MPet);
                                            }
                                        }
        );
    }

}
