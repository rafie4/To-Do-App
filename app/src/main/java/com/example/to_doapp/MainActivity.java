package com.example.to_doapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.os.FileUtils;
import org.apache.commons.io.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAddTask;
    EditText editItem;
    RecyclerView rvItems;
    ItemAdapter itemsAdapter;

    //@SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        rvItems = findViewById(R.id.rvItems);
        editItem = findViewById(R.id.editItem);

       // editItem.setText("I'm doing this from Android Studio!");

        loadItems();
//        items = new ArrayList<>();
//        items.add("Buy Milk");
//        items.add("Call Gran Gran!");
//        items.add("Pause, Appreciate life!");

        ItemAdapter.OnLongClickListener onLongClickListener =  new ItemAdapter.OnLongClickListener (){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model or screen
                items.remove(position);
                //Notify the adapter of the deleted position
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Task has been removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter = new ItemAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));


        //Implementing Add button logic
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editItem.getText().toString();
                //Add item to the model
                items.add(todoItem);
                //Modify the adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() -1);
                editItem.setText("");
                Toast.makeText(getApplicationContext(), "Task has been added!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getBaseContext(), "Reason can not be blank", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });

    }


    private File getdataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getdataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            //log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }

    }

    //This function saves items by writing them to the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getdataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
           // log.e("MainActivity", "Error Writing items", e);
        }

    }
}
