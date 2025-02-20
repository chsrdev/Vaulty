package dev.chsr.vaulty.viewmdel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import dev.chsr.vaulty.data.AppDatabase;
import dev.chsr.vaulty.data.PasswordDao;
import dev.chsr.vaulty.model.PasswordEntity;

public class PasswordViewModel extends AndroidViewModel {
    private PasswordDao passwordDao;
    private LiveData<List<PasswordEntity>> allPasswords;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = Room.databaseBuilder(application,
                AppDatabase.class, "passwords").build();
        passwordDao = db.passwordDao();
        allPasswords = passwordDao.getPasswords();
    }

    public LiveData<List<PasswordEntity>> getAllPasswords() {
        return allPasswords;
    }

    public void insert(PasswordEntity passwordEntry) {
        new Thread(() -> {
            passwordDao.insert(passwordEntry);
            allPasswords = passwordDao.getPasswords();
        }).start();
    }

    public void clear() {
        new Thread(() -> {
            passwordDao.clear();
            allPasswords = passwordDao.getPasswords();
        }).start();
    }
}

