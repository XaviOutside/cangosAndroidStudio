package com.example.cangos;

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

import java.util.List;

public class ListServices extends Activity {

    ModelHandler db = new ModelHandler(this, "CANGOS", null, 4);
    String[] items=null;
    Services service = new Services();
    List<Services> services = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_services);
        EditText inputSearch = (EditText) findViewById(R.id.searchPet);
        ImageButton addservicebutton = (ImageButton) findViewById(R.id.addPet);

        Bundle extras = getIntent().getExtras();
        String pet_id = null;

        if (extras != null) {
            pet_id = extras.getString("PetId");
        }

        service.add_data("Pet_id", pet_id);
        services = service.get_services(db);
        items = create_service_list();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, items);
        show_service_to_list(adapter);

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

        addservicebutton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent MService = new Intent(ListServices.this, MainServices.class);
                        MService.putExtra("PetId", service.get_data("Pet_id"));
                        ListServices.this.startActivity(MService);
                    }
                }
        );

    }

    private String[] create_service_list() {
        items = new String[this.services.size()];

        for (int i=0; i< this.services.size(); i++) {
            items[i] = this.services.get(i).get_data("") + " " + this.services.get(i).get_data("");
        }

        return items;
    }

    private void show_service_to_list(ArrayAdapter<String> adapter) {
        ListView servicelist = (ListView)findViewById(R.id.listPets);
        servicelist.setAdapter(adapter);
        servicelist.setOnItemClickListener(new OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Log.d("setOnItemClickListener: ", "init");
                                                Intent MService = new Intent(ListServices.this, MainServices.class);
                                                MService.putExtra("PetId", services.get(position).get_data("Id"));
                                                Log.d("setOnItemClickListener: ","return"+ services.get(position).get_data("Id"));
                                                ListServices.this.startActivity(MService);
                                            }
                                        }
        );
    }

}
