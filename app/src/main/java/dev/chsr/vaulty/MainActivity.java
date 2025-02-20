package dev.chsr.vaulty;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dev.chsr.vaulty.fragment.NewPasswordFragment;
import dev.chsr.vaulty.fragment.PasswordListFragment;
import dev.chsr.vaulty.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainer, PasswordListFragment.class, bundle)
                    .commit();
        }

        fragmentManager = getSupportFragmentManager();

        passwordListButton.setOnClickListener(view -> changeFragment(new PasswordListFragment()));
        newPasswordButton.setOnClickListener(view -> changeFragment(new NewPasswordFragment()));
        settingsButton.setOnClickListener(view -> changeFragment(new SettingsFragment()));
    }

    void changeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .setReorderingAllowed(true)
                .commit();
    }
}