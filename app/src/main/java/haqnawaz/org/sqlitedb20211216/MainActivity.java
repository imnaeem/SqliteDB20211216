package haqnawaz.org.sqlitedb20211216;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button buttonAdd, buttonViewAll, buttonupd, buttondel;
    EditText editName, editAge;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchIsActive;
    ArrayList<StudentModel> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        buttonupd = findViewById(R.id.updatebtn);
        buttondel = findViewById(R.id.deletebtn);
        recyclerView = findViewById(R.id.myRecyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            StudentModel studentModel;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.userinfo, new LinearLayout(MainActivity.this), false))
                        .setPositiveButton("Add student", (dialog, id) -> {
                            editName = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextName);
                            editAge = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextAge);
                            switchIsActive = (Switch) ((AlertDialog) dialog).findViewById(R.id.switchStudent);
                                try
                                {
                                    studentModel = new StudentModel(editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked());
                                    DbHelper dbHelper = new DbHelper(MainActivity.this);
                                    dbHelper.addStudent(studentModel);
                                    Toast.makeText(MainActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                    ViewStudents();
                                }
                                catch (Exception e){
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            })
                        .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonViewAll.setOnClickListener(v -> ViewStudents());

    }


    public void ViewStudents()
    {
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        list = dbHelper.getAllStudents();
        myRecyclerViewAdapter adapter = new myRecyclerViewAdapter(list);
        adapter.setOnItemClickListener((position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Student will be permanent deleted");
            builder.setTitle("Delete Student");
            builder.setCancelable(false);
            builder.setPositiveButton("Delete", (dialog, asdf) -> {

                dbHelper.deleteStudent(list.get(position).getId());
                adapter.deleteRecord(position);
                Toast.makeText(MainActivity.this, "Student Deleted Successfully", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        recyclerView.setAdapter(adapter);
    }
}
