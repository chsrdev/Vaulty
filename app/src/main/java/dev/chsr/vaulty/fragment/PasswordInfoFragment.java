package dev.chsr.vaulty.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import java.io.IOException;
import java.security.GeneralSecurityException;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.model.PasswordEntity;
import dev.chsr.vaulty.util.EncryptionUtils;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class PasswordInfoFragment extends Fragment {

    private static final String ARG_PASSWORD_ID = "password";
    private int passwordId;

    public PasswordInfoFragment() {
    }

    public static PasswordInfoFragment newInstance(int passwordId) {
        PasswordInfoFragment fragment = new PasswordInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PASSWORD_ID, passwordId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_left));
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        if (getArguments() != null) {
            passwordId = getArguments().getInt(ARG_PASSWORD_ID);
        } else {
            FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_info, container, false);
        PasswordViewModel passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
        PasswordEntity passwordEntity = passwordViewModel.getPasswordById(passwordId);
        if (passwordEntity == null) return view;

        EditText titleEditText = view.findViewById(R.id.passwordInfoFormTitleEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordInfoFormPasswordEditText);
        EditText emailEditText = view.findViewById(R.id.passwordInfoFormEmailEditText);
        EditText notesEditText = view.findViewById(R.id.passwordInfoFormNotesEditText);
        try {
            titleEditText.setText(passwordEntity.getTitle());
            passwordEditText.setText(passwordEntity.getPassword());
            emailEditText.setText(passwordEntity.getEmail());
            notesEditText.setText(passwordEntity.getNotes());
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        Button saveButton = view.findViewById(R.id.saveButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(v -> {
            try {
                passwordEntity.setTitle(titleEditText.getText().toString());
                passwordEntity.setPassword(passwordEditText.getText().toString());
                passwordEntity.setEmail(emailEditText.getText().toString());
                passwordEntity.setNotes(notesEditText.getText().toString());
                passwordViewModel.update(passwordEntity);
                FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
            } catch (GeneralSecurityException | IOException e) {
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            passwordViewModel.delete(passwordEntity);
            FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
        });

        return view;
    }
}