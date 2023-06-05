package sg.edu.rp.c346.id21023028.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerActivity;
    Spinner spinnerTask;
    EditText etInput;
    Button btnAdd;
    Button btnClear;
    Button btnDelete;
    ListView lvTask;
    ArrayList<String> taskDataSource;
    ArrayList<String> displayDataSource;
    ArrayAdapter adapter;
    ArrayAdapter taskAdapter;
    Space taskSpace;
    Space etSpace;
    String selectMsg = "Choose a task to delete";
    int taskid;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu); //inflate method creates the menu.
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        taskDataSource.remove(taskid);
        displayDataSource.remove(taskid+1);
        manageAdapter();
        return super.onContextItemSelected(item); //pass menu item to the superclass implementation
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link task
        taskDataSource = new ArrayList<>();
        displayDataSource = new ArrayList<>();
        displayDataSource.add(selectMsg);
        taskSpace = findViewById(R.id.taskSpace);
        etSpace = findViewById(R.id.etSpace);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,taskDataSource);
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,displayDataSource);
        spinnerActivity = findViewById(R.id.spinnerActivity);
        spinnerTask = findViewById(R.id.spinnerTask);
        etInput = findViewById(R.id.editInput);
        btnAdd = findViewById(R.id.buttonAdd);
        btnClear = findViewById(R.id.buttonClear);
        btnDelete = findViewById(R.id.buttonDelete);
        lvTask = findViewById(R.id.listViewTask);

        spinnerTask.setAdapter(taskAdapter);
        lvTask.setAdapter(adapter);
        addActivityState();

        registerForContextMenu(lvTask);

        //create listener
        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskid = position;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etInput.getText().toString();
                if (addValidate()){
                    taskDataSource.add(input);
                    displayDataSource.add(input);
                    manageAdapter();
                }
            }
        });

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (deleteValidate()){
//                    taskDataSource.remove(spinnerTask.getSelectedItem().toString());
//                    displayDataSource.remove(spinnerTask.getSelectedItem().toString());
//                    manageAdapter();
//                }
//            }
//        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDataSource.clear();
                displayDataSource.clear();
                displayDataSource.add(selectMsg);
                manageAdapter();
            }
        });

        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: //add task
                        addActivityState();
                        break;
//                    case 1: //delete task
//                        deleteActivityState();
//                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addActivityState(){
        btnAdd.setEnabled(true);
        btnDelete.setEnabled(false);
        spinnerTask.setVisibility(View.GONE);
        etInput.setVisibility(View.VISIBLE);
        etSpace.setVisibility(View.VISIBLE);
        taskSpace.setVisibility(View.GONE);
        etInput.setText("");
    }

//    private void deleteActivityState(){
//        btnAdd.setEnabled(false);
//        btnDelete.setEnabled(true);
//        spinnerTask.setVisibility(View.VISIBLE);
//        etInput.setVisibility(View.GONE);
//        etSpace.setVisibility(View.GONE);
//        taskSpace.setVisibility(View.VISIBLE);
//        spinnerTask.setSelection(0);
//        Toast.makeText(MainActivity.this,"Hint: you can press and hold to manage task.",
//                Toast.LENGTH_LONG).show();
//    }

    private void manageAdapter(){
        adapter.notifyDataSetChanged();
        taskAdapter.notifyDataSetChanged();
    }

    private boolean addValidate(){
        String input = etInput.getText().toString();
        if (input.trim().isEmpty()){
            Toast.makeText(MainActivity.this,"Task cannot be empty.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            etInput.setText(""); //clear field
            return true;
        }
    }

//    private boolean deleteValidate(){
//        String select = spinnerTask.getSelectedItem().toString();
//        if (select.equals(selectMsg)){
//            Toast.makeText(MainActivity.this,"Choose a task to delete",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
}