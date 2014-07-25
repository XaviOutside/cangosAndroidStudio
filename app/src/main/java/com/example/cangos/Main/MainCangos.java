package com.example.cangos.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.cangos.R;
import com.example.cangos.Users.ListUsers;

public class MainCangos extends Activity {

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cangos);
        Intent IntentToListUsers= new Intent(MainCangos.this, ListUsers.class);
        MainCangos.this.startActivity(IntentToListUsers);
    }

}
