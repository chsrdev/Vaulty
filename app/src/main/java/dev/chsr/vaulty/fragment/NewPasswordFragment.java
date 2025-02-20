package dev.chsr.vaulty.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.model.PasswordEntity;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class NewPasswordFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    boolean isCanAddPassword = false;

    public NewPasswordFragment() {
    }

    public static NewPasswordFragment newInstance(String param1, String param2) {
        NewPasswordFragment fragment = new NewPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        Button addPasswordBtn = view.findViewById(R.id.newPasswordAddButton);
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
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new PasswordListFragment())
                        .setReorderingAllowed(true)
                        .commit();
            } catch (Exception e) {
                Toast.makeText(requireActivity(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}