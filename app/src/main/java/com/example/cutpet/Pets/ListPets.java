package com.example.cutpet.Pets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cutpet.Common.ModelHandler;
import com.example.cutpet.R;
import com.example.cutpet.Services.ListServices;

import java.util.List;

public class ListPets extends Activity {

    String[] items=null;
    Pets pet = new Pets();
    List<Pets> pets = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pets);
        EditText inputSearch = (EditText) findViewById(R.id.searchPet);
        ImageButton addpetbutton = (ImageButton) findViewById(R.id.addPet);
        ModelHandler db = new ModelHandler(this);

        Bundle extras = getIntent().getExtras();
        String user_id=null;

        if (extras != null) {
            user_id = extras.getString("UserId");
        }

        pet.add_data("User_id",user_id);
        pets = db.searchPet(pet);
        items = create_pet_list();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, items);
        show_pet_to_list(adapter);

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

        addpetbutton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent MPet = new Intent(ListPets.this, MainPets.class);
                        MPet.putExtra("UserId", pet.get_data("User_id"));
                        ListPets.this.startActivity(MPet);
                    }
                }
        );

    }

    private String[] create_pet_list() {
        items = new String[this.pets.size()];

        for (int i=0; i< this.pets.size(); i++) {
            items[i] = this.pets.get(i).get_data("Alias") + " " + this.pets.get(i).get_data("Age");
        }

        return items;
    }

    private void show_pet_to_list(ArrayAdapter<String> adapter) {
        ListView petlist = (ListView)findViewById(R.id.listPets);
        petlist.setAdapter(adapter);
        petlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Log.d("setOnItemClickListener: ", "init");
                                                Intent MPet = new Intent(ListPets.this, ListServices.class);
                                                MPet.putExtra("PetId", pets.get(position).get_data("Id"));
                                                Log.d("setOnItemClickListener: ","return"+ pets.get(position).get_data("Id"));
                                                ListPets.this.startActivity(MPet);
                                            }
                                        }
        );
    }

}
