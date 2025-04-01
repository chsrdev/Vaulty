package dev.chsr.vaulty.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.adapter.PasswordAdapter;
import dev.chsr.vaulty.model.PasswordEntity;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class PasswordListFragment extends Fragment {
    private static boolean isStart = true;

    public PasswordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_left));
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        fragmentActivity = requireActivity();
    }

    public void initRecyclerView() {
        if (passwords.isEmpty()) {
            passwordListView.setAdapter(new PasswordAdapter(passwords, fragmentActivity));
            return;
        }

        passwordListView.setAdapter(new PasswordAdapter(passwords.stream().filter(pwd -> {
            try {
                return pwd.getTitle().contains(searchText);
            } catch (GeneralSecurityException | IOException e) {
                Toast.makeText(fragmentActivity, "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }).collect(Collectors.toList()), fragmentActivity));
    }

    String searchText = "";
    EditText searchEditText;
    RecyclerView passwordListView;
    FragmentActivity fragmentActivity;
    PasswordViewModel passwordViewModel;
    List<PasswordEntity> passwords = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        searchEditText = view.findViewById(R.id.searchEditText);
        passwordListView = view.findViewById(R.id.passwordList);
        passwordListView.setLayoutManager(new LinearLayoutManager(getContext()));

        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(getViewLifecycleOwner(), passwordEntities -> {
            passwords = passwordEntities;
            initRecyclerView();
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence.toString();
                initRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
}