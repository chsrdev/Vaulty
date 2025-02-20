package dev.chsr.vaulty.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.fragment.PasswordInfoFragment;
import dev.chsr.vaulty.fragment.PasswordListFragment;
import dev.chsr.vaulty.util.EncryptionUtils;
import dev.chsr.vaulty.model.PasswordEntity;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private final List<PasswordEntity> passwordEntries;
    private final FragmentActivity fragmentActivity;

    public PasswordAdapter(List<PasswordEntity> passwordEntries, FragmentActivity fragmentActivity) {
        this.passwordEntries = passwordEntries;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public PasswordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password, parent, false);
        return new PasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasswordViewHolder holder, int position) {
        PasswordEntity passwordEntry = passwordEntries.get(position);
        try {
            holder.title.setText(EncryptionUtils.decrypt(passwordEntry.encryptedTitle, passwordEntry.titleIV));
            holder.itemView.setOnClickListener(view -> {
                fragmentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, PasswordInfoFragment.newInstance(passwordEntry.id))
                        .setReorderingAllowed(true)
                        .commit();
            });
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return passwordEntries.size();
    }

    public static class PasswordViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public PasswordViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
