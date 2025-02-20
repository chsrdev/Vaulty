package dev.chsr.vaulty.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dev.chsr.vaulty.model.PasswordEntity;

@Database(entities = {PasswordEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PasswordDao passwordDao();
}
