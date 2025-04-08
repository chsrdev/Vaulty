package dev.chsr.vaulty.viewmdel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.stream.Collectors;

import dev.chsr.vaulty.data.AppDatabase;
import dev.chsr.vaulty.data.PasswordDao;
import dev.chsr.vaulty.model.PasswordEntity;

public class PasswordViewModel extends AndroidViewModel {
    private final PasswordDao passwordDao;
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

    public void update(PasswordEntity passwordEntity) {
        new Thread(() -> passwordDao.update(passwordEntity)).start();
    }

    public void delete(PasswordEntity passwordEntity) {
        new Thread(() -> {
            passwordDao.delete(passwordEntity);
            allPasswords = getAllPasswords();
        }).start();
    }

    public PasswordEntity getPasswordById(int id) {
        if (allPasswords.getValue() == null || allPasswords.getValue().isEmpty())
            return null;

        List<PasswordEntity> ids = allPasswords.getValue().stream().filter(passwordEntity -> passwordEntity.id == id).collect(Collectors.toList());

        if (ids.size() == 1)
            return ids.get(0);
        else
            return null;
    }

    public void clear() {
        new Thread(() -> {
            passwordDao.clear();
            allPasswords = passwordDao.getPasswords();
        }).start();
    }
}

