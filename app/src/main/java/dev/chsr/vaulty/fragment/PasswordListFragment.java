package dev.chsr.vaulty.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.adapter.PasswordAdapter;
import dev.chsr.vaulty.viewmdel.PasswordViewModel;

public class PasswordListFragment extends Fragment {

    public PasswordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);
        PasswordViewModel passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(getViewLifecycleOwner(), passwords -> {
            if (!passwords.isEmpty()) {
                RecyclerView passwordListView = view.findViewById(R.id.passwordList);
                passwordListView.setLayoutManager(new LinearLayoutManager(getContext()));
                passwordListView.setAdapter(new PasswordAdapter(passwords, requireActivity()));
            }
        });

        return view;
    }
}