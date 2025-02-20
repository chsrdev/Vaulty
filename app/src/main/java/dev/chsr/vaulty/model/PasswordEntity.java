package dev.chsr.vaulty.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

import dev.chsr.vaulty.util.EncryptionUtils;

@Entity(tableName = "passwords")
public class PasswordEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "encrypted_title")
    public String encryptedTitle;

    @ColumnInfo(name = "title_iv")
    public String titleIV;

    @ColumnInfo(name = "encrypted_password")
    public String encryptedPassword;

    @ColumnInfo(name = "password_iv")
    public String passwordIV;

    @ColumnInfo(name = "encrypted_email")
    public String encryptedEmail;

    @ColumnInfo(name = "email_iv")
    public String emailIV;

    @ColumnInfo(name = "encrypted_notes")
    public String encryptedNotes;

    @ColumnInfo(name = "notes_iv")
    public String notesIV;

    public PasswordEntity(String encryptedTitle, String titleIV,
                          String encryptedPassword, String passwordIV,
                          String encryptedEmail, String emailIV,
                          String encryptedNotes, String notesIV) {
        this.encryptedTitle = encryptedTitle;
        this.titleIV = titleIV;
        this.encryptedPassword = encryptedPassword;
        this.passwordIV = passwordIV;
        this.encryptedEmail = encryptedEmail;
        this.emailIV = emailIV;
        this.encryptedNotes = encryptedNotes;
        this.notesIV = notesIV;
    }

    public PasswordEntity(String title, String password,
                          String email, String notes) throws Exception {
        if (title == null) throw new Exception("Title must be not null");
        setTitle(title);

        if (password != null)
            setPassword(password);

        if (email != null)
            setEmail(email);

        if (notes != null)
            setNotes(notes);
    }

    public void setTitle(String title) throws GeneralSecurityException, IOException {
        if (title != null) {
            String[] titleCrypt = EncryptionUtils.encrypt(title);
            this.encryptedTitle = titleCrypt[0];
            this.titleIV = titleCrypt[1];
        }
    }

    public void setPassword(String password) throws GeneralSecurityException, IOException {
        if (password != null) {
            String[] passwordCrypt = EncryptionUtils.encrypt(password);
            this.encryptedPassword = passwordCrypt[0];
            this.passwordIV = passwordCrypt[1];
        }
    }

    public void setEmail(String email) throws GeneralSecurityException, IOException {
        if (email != null) {
            String[] emailCrypt = EncryptionUtils.encrypt(email);
            this.encryptedEmail = emailCrypt[0];
            this.emailIV = emailCrypt[1];
        }
    }

    public void setNotes(String notes) throws GeneralSecurityException, IOException {
        if (notes != null) {
            String[] notesCrypt = EncryptionUtils.encrypt(notes);
            this.encryptedNotes = notesCrypt[0];
            this.notesIV = notesCrypt[1];
        }
    }

    public String getTitle() throws GeneralSecurityException, IOException {
        return EncryptionUtils.decrypt(encryptedTitle, titleIV);
    }

    public String getPassword() throws GeneralSecurityException, IOException {
        return encryptedPassword == null || passwordIV == null ? "" : EncryptionUtils.decrypt(encryptedPassword, passwordIV);
    }

    public String getEmail() throws GeneralSecurityException, IOException {
        return encryptedEmail == null || emailIV == null ? "" : EncryptionUtils.decrypt(encryptedEmail, emailIV);
    }

    public String getNotes() throws GeneralSecurityException, IOException {
        return encryptedNotes == null || notesIV == null ? "" : EncryptionUtils.decrypt(encryptedNotes, notesIV);
    }
}
