package dev.chsr.vaulty.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import dev.chsr.vaulty.R;
import dev.chsr.vaulty.data.EncryptionUtils;
import dev.chsr.vaulty.model.PasswordEntity;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private List<PasswordEntity> passwordEntries;

    public PasswordAdapter(List<PasswordEntity> passwordEntries) {
        this.passwordEntries = passwordEntries;
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
            Log.i("pwdID", String.valueOf(passwordEntry.id));
            holder.title.setText(EncryptionUtils.decrypt(passwordEntry.encryptedTitle, passwordEntry.titleIV));
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
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
