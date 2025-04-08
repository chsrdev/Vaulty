package dev.chsr.vaulty.util;
import android.content.Context;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecurePrefs {
    private static final String PREFS_NAME = "secure_prefs";
    public static final String MASTER_PASSWORD_HASH = "MasterPasswordHash";
    private static EncryptedSharedPreferences sharedPreferences;

    public static void init(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception ignored) {
        }
    }

    public static void saveData(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getData(String key) {
        return sharedPreferences.getString(key, null);
    }
}
