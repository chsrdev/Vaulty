package dev.chsr.vaulty.fragment;

import android.app.LocaleManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import java.util.HashMap;
import java.util.Locale;

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

        Spinner languagesSpinner = view.findViewById(R.id.languages_spinner);
        ArrayAdapter<CharSequence> languagesAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.languages_array,
                android.R.layout.simple_spinner_item
        );
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagesSpinner.setAdapter(languagesAdapter);

        HashMap<String, String> languageCodes = new HashMap<>();
        languageCodes.put("Russian", "ru");
        languageCodes.put("Русский", "ru");
        languageCodes.put("English", "en");
        languageCodes.put("Английский", "en");

        languagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LocaleManager localeManager = requireContext().getSystemService(LocaleManager.class);
                LocaleList localeList = LocaleList.forLanguageTags(languageCodes.get((String)adapterView.getItemAtPosition(i)));
                localeManager.setApplicationLocales(localeList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }
}