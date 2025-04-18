package com.btec.fpt.campus_expense_manager.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.btec.fpt.campus_expense_manager.MainActivity;
import com.btec.fpt.campus_expense_manager.R;
import com.btec.fpt.campus_expense_manager.database.DatabaseHelper;

public class DeleteAccountFragment extends Fragment {
    private EditText etPassword;
    private Button btnDelete;
    private DatabaseHelper dbHelper;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);

        etPassword = view.findViewById(R.id.et_password);
        btnDelete = view.findViewById(R.id.btn_delete_account);
        dbHelper = new DatabaseHelper(getContext());
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        email = prefs.getString("email", "");

        btnDelete.setOnClickListener(v -> {
            String pwd = etPassword.getText().toString();
            if (TextUtils.isEmpty(pwd)) {
                etPassword.setError("Password required");
                return;
            }
            if (dbHelper.signIn(email, pwd)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete your account? This cannot be undone.")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            dbHelper.deleteUserAccount(email);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.apply();
                            Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
