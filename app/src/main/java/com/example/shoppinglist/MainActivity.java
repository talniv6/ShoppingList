package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        String user = getIntent().getStringExtra("user");

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        AppViewModel viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        viewModel.getItems().observe(this, adapter::setItems);

        findViewById(R.id.button_add).setOnClickListener(view -> showAddItemDialog(adapter, user));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                Item itemToRemove = adapter.getItems().get(position);
                adapter.removeItem(position);
                viewModel.deleteItem(itemToRemove);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void showAddItemDialog(Adapter adapter, String user) {
        AppViewModel viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setTypeface(AppTypeFace.get(this, user));
        builder.setView(input);

        builder.setPositiveButton("הוספה", (dialog, which) -> {
            String itemName = input.getText().toString();
            if (itemName.matches("\\s+"))
                return;

            Item item = viewModel.addItem(itemName, user);
            adapter.addItem(item);
        });

        builder.setNegativeButton("ביטול", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}