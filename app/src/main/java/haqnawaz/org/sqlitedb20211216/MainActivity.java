package haqnawaz.org.sqlitedb20211216;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button buttonAdd, buttonViewAll, buttonupd, buttondel;
    EditText editName, editAge;
    Switch switchIsActive;
    ListView listViewStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        buttonupd = findViewById(R.id.updatebtn);
        buttondel = findViewById(R.id.deletebtn);
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            StudentModel studentModel;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.userinfo, null))
                        .setPositiveButton("Add student", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                editName = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextName);
                                editAge = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextAge);
                                switchIsActive = (Switch) ((AlertDialog) dialog).findViewById(R.id.switchStudent);
                                    try
                                    {
                                        studentModel = new StudentModel(editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked());
                                        Toast.makeText(MainActivity.this, studentModel.toString(), Toast.LENGTH_SHORT).show();
                                        DbHelper dbHelper = new DbHelper(MainActivity.this);
                                        dbHelper.addStudent(studentModel);

                                    }
                                    catch (Exception e){
                                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                ArrayList<StudentModel> list = dbHelper.getAllStudents();

                RecyclerView.Adapter adapter = new myRecyclerViewAdapter(list);
                recyclerView.setAdapter(adapter);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

            }
        });

//        buttonupd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        buttondel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }
}
