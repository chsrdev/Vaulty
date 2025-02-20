package dev.chsr.vaulty.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.chsr.vaulty.model.PasswordEntity;

@Dao
public interface PasswordDao {
    @Insert
    void insert(PasswordEntity passwordEntity);

    @Query("SELECT * FROM passwords")
    LiveData<List<PasswordEntity>> getPasswords();

    @Query("DELETE FROM passwords WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM passwords")
    void clear();
}
