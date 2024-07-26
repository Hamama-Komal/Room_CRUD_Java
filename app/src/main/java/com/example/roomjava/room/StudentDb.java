package com.example.roomjava.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 2, exportSchema = false)
public abstract class StudentDb extends RoomDatabase {

    public abstract StudentDao getStudentDao();

    public static StudentDb INSTANCE;

    public static StudentDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, StudentDb.class, "StudentDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
