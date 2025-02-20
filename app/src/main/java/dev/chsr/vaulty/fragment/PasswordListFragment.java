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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private PasswordViewModel passwordViewModel;

    public PasswordListFragment() {
    }

    public static PasswordListFragment newInstance(String param1, String param2) {
        PasswordListFragment fragment = new PasswordListFragment();
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
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);
        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(getViewLifecycleOwner(), passwords -> {
            if (!passwords.isEmpty()) {
                RecyclerView passwordListView = view.findViewById(R.id.passwordList);
                passwordListView.setLayoutManager(new LinearLayoutManager(getContext()));
                passwordListView.setAdapter(new PasswordAdapter(passwords));
            }
        });

        return view;
    }
}