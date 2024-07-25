package com.example.roomjava;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roomjava.databinding.ActivityMainBinding;
import com.example.roomjava.room.Student;
import com.example.roomjava.room.StudentDao;
import com.example.roomjava.room.StudentDb;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    private StudentDao studentDao;
    private StudentDb studentDb;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        studentDb = StudentDb.getInstance(this);
        studentDao = studentDb.getStudentDao();


        recyclerAdapter = new RecyclerAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(recyclerAdapter);

        fetchData();


        binding.buttonInsert.setOnClickListener(v ->{
            String name = binding.editTexName.getText().toString().trim();
            String email = binding.editTextEmail.getText().toString().trim();

            Student student = new Student(0, name, email);
            recyclerAdapter.addStudent(student);
            studentDao.insert(student);
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();


            binding.editTexName.setText("");
            binding.editTextEmail.setText("");
        });

    }

    private void fetchData(){
        List<Student> studentList = studentDao.getAllStudents();
        recyclerAdapter.updateStudentList(studentList);
    }



}