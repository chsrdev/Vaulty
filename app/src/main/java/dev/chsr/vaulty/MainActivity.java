package dev.chsr.vaulty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.chsr.vaulty.fragment.FragmentSwitcher;
import dev.chsr.vaulty.fragment.NewPasswordFragment;
import dev.chsr.vaulty.fragment.PasswordListFragment;
import dev.chsr.vaulty.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentSwitcher.changeFragment(fragmentManager, new PasswordListFragment());
        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.password_list) {
                FragmentSwitcher.changeFragment(fragmentManager, new PasswordListFragment());
                return true;
            } else if (item.getItemId() == R.id.new_password_btn) {
                FragmentSwitcher.changeFragment(fragmentManager, NewPasswordFragment.newInstance(fragmentManager.findFragmentByTag("CURRENT")));
                return true;
            } else if (item.getItemId() == R.id.settings) {
                FragmentSwitcher.changeFragment(fragmentManager, new SettingsFragment());
                return true;
            }
            return false;
        });

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        FragmentSwitcher.changeFragment(fragmentManager, new PasswordListFragment());
                        bottomNavigationView.setSelectedItemId(0);
                    }
                });
    }
}