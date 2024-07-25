package com.example.roomjava;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomjava.databinding.ItemLayoutBinding;
import com.example.roomjava.room.Student;
import com.example.roomjava.room.StudentDao;
import com.example.roomjava.room.StudentDb;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private  List<Student> studentList;
    public StudentDao studentDao;

    public RecyclerAdapter(Context context) {
        this.context = context;
        this.studentList = new ArrayList<>();
        this.studentDao = StudentDb.getInstance(context).getStudentDao();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student item = studentList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void addStudent(Student student) {
        studentList.add(student);
        notifyDataSetChanged();
    }

    public void updateStudentList(List<Student> students) {
        this.studentList = students;
        notifyDataSetChanged();
    }

    public void clearData() {
        studentList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutBinding binding;

        public ViewHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Student item) {
            binding.textViewName.setText(item.getName());
            binding.textViewEmail.setText(item.getEmail());

            // Set click listeners for edit and delete buttons if needed
            binding.edit.setOnClickListener(v -> {
                showEditDialog(item);
            });

            binding.delete.setOnClickListener(v -> {

                showDeleteConfirmationDialog(item.getId());
            });
        }

        private void showEditDialog(Student student) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            View dialogView = inflater.inflate(R.layout.dialog_edit_student, null);
            builder.setView(dialogView);

            EditText editTextName = dialogView.findViewById(R.id.editTextName);
            EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
            editTextName.setText(student.getName());
            editTextEmail.setText(student.getEmail());

            builder.setTitle("Edit Student")
                    .setPositiveButton("Update", (dialog, which) -> {
                        String newName = editTextName.getText().toString().trim();
                        String newEmail = editTextEmail.getText().toString().trim();
                        student.setName(newName);
                        student.setEmail(newEmail);
                        studentDao.update(student);
                        updateStudentList(studentDao.getAllStudents());
                        Toast.makeText(itemView.getContext(), "Student updated", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        private void showDeleteConfirmationDialog(int position) {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        studentDao.delete(position);
                        updateStudentList(studentDao.getAllStudents());
                        Toast.makeText(itemView.getContext(), "Student deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

    }

}
