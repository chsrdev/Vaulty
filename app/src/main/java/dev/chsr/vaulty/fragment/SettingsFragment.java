package dev.chsr.vaulty.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import dev.chsr.vaulty.R;
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
        PasswordViewModel passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
        clearButton.setOnClickListener(v -> {
            passwordViewModel.clear();
            FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
        });

        return view;
    }
}