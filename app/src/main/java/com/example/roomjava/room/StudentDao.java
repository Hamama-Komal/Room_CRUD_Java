package com.example.roomjava.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Query("DELETE FROM student WHERE id=:id")
    void delete(int id);

    @Query("SELECT * FROM student")
    List<Student> getAllStudents();
}
