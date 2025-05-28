package dev.chsr.vaulty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.chsr.vaulty.util.EncryptionUtils;
import dev.chsr.vaulty.util.SecurePrefs;

public class MasterPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
        if (!prefs.getBoolean("customMasterPassword", false)) {
            startActivity(new Intent(this, MainActivity.class));
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_master_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button enterButton = findViewById(R.id.enterButton);
        EditText masterPasswordText = findViewById(R.id.masterPassword);

        SecurePrefs.init(this);
        String savedHash = SecurePrefs.getData(SecurePrefs.MASTER_PASSWORD_HASH);

        enterButton.setOnClickListener(v -> {
            String hashedInput = EncryptionUtils.sha256(masterPasswordText.getText().toString());
            Intent passIntent = new Intent(this, MainActivity.class);
            if (savedHash.equals("0")) {
                SecurePrefs.saveData(SecurePrefs.MASTER_PASSWORD_HASH, hashedInput);
                startActivity(passIntent);
            } else if (savedHash.equals(hashedInput)) {
                startActivity(passIntent);
            }
        });
    }
}