package dev.chsr.vaulty.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.chsr.vaulty.model.PasswordEntity;

@Dao
public interface PasswordDao {
    @Insert
    void insert(PasswordEntity passwordEntity);

    @Query("SELECT * FROM passwords")
    LiveData<List<PasswordEntity>> getPasswords();

    @Update
    void update(PasswordEntity passwordEntity);

    @Delete
    void delete(PasswordEntity passwordEntity);

    @Query("SELECT * FROM passwords WHERE id=:id")
    LiveData<List<PasswordEntity>> getPassword(int id);

    @Query("DELETE FROM passwords")
    void clear();
}
