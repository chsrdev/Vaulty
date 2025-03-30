package dev.chsr.vaulty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.chsr.vaulty.fragment.FragmentSwitcher;
import dev.chsr.vaulty.fragment.NewPasswordFragment;
import dev.chsr.vaulty.fragment.PasswordListFragment;
import dev.chsr.vaulty.fragment.SettingsFragment;
import dev.chsr.vaulty.util.SecurePrefs;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().getBooleanExtra("passed", false)
            && getPreferences(MODE_PRIVATE).getBoolean("customMasterPassword", false))
                startActivity(new Intent(this, MasterPasswordActivity.class));

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton passwordListButton = findViewById(R.id.passwordsBtn);
        ImageButton newPasswordButton = findViewById(R.id.addPasswordBtn);
        ImageButton settingsButton = findViewById(R.id.settingsBtn);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentSwitcher.changeFragment(getSupportFragmentManager(), new PasswordListFragment());
        }

        passwordListButton.setOnClickListener(view -> FragmentSwitcher.changeFragment(
                getSupportFragmentManager(), new PasswordListFragment()));
        newPasswordButton.setOnClickListener(view ->
                FragmentSwitcher.changeFragment(getSupportFragmentManager(),
                        NewPasswordFragment.newInstance(fragmentManager.findFragmentByTag("CURRENT")))
        );
        settingsButton.setOnClickListener(view -> FragmentSwitcher.changeFragment(
                getSupportFragmentManager(), new SettingsFragment()));
    }
}