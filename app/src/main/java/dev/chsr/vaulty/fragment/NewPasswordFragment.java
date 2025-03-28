package dev.chsr.vaulty.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.model.PasswordEntity;
import dev.chsr.vaulty.util.EncryptionUtils;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class NewPasswordFragment extends Fragment {
    boolean isCanAddPassword = false;

    public NewPasswordFragment() {
    }

    public static NewPasswordFragment newInstance(Fragment fromFragment) {
        TransitionInflater inflater = TransitionInflater.from(fromFragment.requireActivity());
        NewPasswordFragment fragment = new NewPasswordFragment();
        if (fromFragment instanceof PasswordListFragment || fromFragment instanceof PasswordInfoFragment) {
            fragment.setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
        } else {
            fragment.setEnterTransition(inflater.inflateTransition(R.transition.slide_left));
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        Button addPasswordBtn = view.findViewById(R.id.newPasswordAddButton);
        Button generateBtn = view.findViewById(R.id.generateBtn);
        EditText titleEditText = view.findViewById(R.id.newPasswordFormTitleEditText);
        EditText passwordEditText = view.findViewById(R.id.newPasswordFormPasswordEditText);
        EditText emailEditText = view.findViewById(R.id.newPasswordFormEmailEditText);
        EditText notesEditText = view.findViewById(R.id.newPasswordFormNotesEditText);
        PasswordViewModel passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCanAddPassword = (charSequence.length() > 0 && !charSequence.toString().isEmpty());
                addPasswordBtn.setTextColor(isCanAddPassword ?
                        Color.WHITE
                        : ContextCompat.getColor(requireActivity(), R.color.text_hint)
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addPasswordBtn.setOnClickListener(v -> {
            try {
                if (!isCanAddPassword) return;
                passwordViewModel.insert(new PasswordEntity(
                        titleEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        notesEditText.getText().toString()
                ));
                setExitTransition(TransitionInflater.from(requireContext()).inflateTransition(R.transition.slide_left));
                FragmentSwitcher.changeFragment(requireActivity().getSupportFragmentManager(), new PasswordListFragment());
            } catch (Exception e) {
                Toast.makeText(requireActivity(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });

        generateBtn.setOnClickListener(v -> {
            passwordEditText.setText(EncryptionUtils.generatePassword());
        });

        return view;
    }
}