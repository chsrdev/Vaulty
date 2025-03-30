package dev.chsr.vaulty.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import dev.chsr.vaulty.MainActivity;
import dev.chsr.vaulty.MasterPasswordActivity;
import dev.chsr.vaulty.R;
import dev.chsr.vaulty.util.SecurePrefs;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button clearButton = view.findViewById(R.id.settingsClearButton);
        CheckBox customMasterPasswordCheckBox = view.findViewById(R.id.useCustomMasterPWDCheckbox);
        PasswordViewModel passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        customMasterPasswordCheckBox.setChecked(prefs.getBoolean("customMasterPassword", false));
        clearButton.setOnClickListener(v -> {
            passwordViewModel.clear();
            FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
        });
        customMasterPasswordCheckBox.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("customMasterPassword", customMasterPasswordCheckBox.isChecked());
            editor.apply();
            if (customMasterPasswordCheckBox.isChecked()) {
                startActivity(new Intent(getActivity(), MasterPasswordActivity.class));
                SecurePrefs.init(getContext());
                SecurePrefs.saveData(SecurePrefs.MASTER_PASSWORD_HASH, "0");
            }
        });

        return view;
    }
}