package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        AppViewModel viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        viewModel.getItems().observe(this, adapter::setItems);

        findViewById(R.id.button_add).setOnClickListener(view -> showAddItemDialog());
    }

    public void showAddItemDialog() {
        AppViewModel viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("הוספה", (dialog, which) -> viewModel.addItem(input.getText().toString()));

        builder.setNegativeButton("ביטול", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}