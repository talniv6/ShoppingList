package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.concurrent.atomic.AtomicReference;

public class ChooseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (pref.contains("user")){
            Intent myIntent = new Intent(ChooseUserActivity.this, MainActivity.class);
            myIntent.putExtra("user", pref.getString("user", null));
            ChooseUserActivity.this.startActivity(myIntent);
            return;
        }

        setContentView(R.layout.activity_choose_user);
        Spinner dropdown = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        Button button = findViewById(R.id.button_continue);

        AtomicReference<String> user = new AtomicReference<>();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.set((String) adapterView.getItemAtPosition(i));
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(view -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user", user.get());
            editor.apply();

            Intent myIntent = new Intent(ChooseUserActivity.this, MainActivity.class);
            myIntent.putExtra("user", user.get());
            ChooseUserActivity.this.startActivity(myIntent);
        });
    }
}