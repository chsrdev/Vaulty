package dev.chsr.vaulty.model;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import dev.chsr.vaulty.data.EncryptionUtils;

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
        String[] titleCrypt = EncryptionUtils.encrypt(title);
        this.encryptedTitle = titleCrypt[0];
        this.titleIV = titleCrypt[1];

        if (password != null) {
            String[] passwordCrypt = EncryptionUtils.encrypt(password);
            this.encryptedPassword = passwordCrypt[0];
            this.passwordIV = passwordCrypt[1];
        }

        if (email != null) {
            String[] emailCrypt = EncryptionUtils.encrypt(email);
            this.encryptedEmail = emailCrypt[0];
            this.emailIV = emailCrypt[1];
        }

        if (notes != null) {
            String[] notesCrypt = EncryptionUtils.encrypt(notes);
            this.encryptedNotes = notesCrypt[0];
            this.notesIV = notesCrypt[1];
        }
    }
}
