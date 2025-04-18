package com.btec.fpt.campus_expense_manager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.btec.fpt.campus_expense_manager.R;
import com.btec.fpt.campus_expense_manager.database.DatabaseHelper;
import com.btec.fpt.campus_expense_manager.models.BalanceInfor;

public class ViewInformationFragment extends Fragment {
    private EditText etFirstName, etLastName, etEmail;
    private ImageButton btnEditFirstName, btnEditLastName;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_information, container, false);

        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etEmail = view.findViewById(R.id.et_email);
        btnEditFirstName = view.findViewById(R.id.btn_edit_first_name);
        btnEditLastName = view.findViewById(R.id.btn_edit_last_name);
        btnSave = view.findViewById(R.id.btn_save_info);

        dbHelper = new DatabaseHelper(getContext());
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        email = prefs.getString("email", "");

        BalanceInfor info = dbHelper.getBalanceFromEmail(email);
        if (info != null) {
            etFirstName.setText(info.getFirstName());
            etLastName.setText(info.getLastName());
            etEmail.setText(email);
            etEmail.setEnabled(false);
        }

        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);

        btnEditFirstName.setOnClickListener(v -> etFirstName.setEnabled(true));
        btnEditLastName.setOnClickListener(v -> etLastName.setEnabled(true));

        btnSave.setOnClickListener(v -> {
            String newFirst = etFirstName.getText().toString().trim();
            String newLast = etLastName.getText().toString().trim();
            if (newFirst.isEmpty() || newLast.isEmpty()) {
                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean updated = dbHelper.updateUserInfo(email, newFirst, newLast);
            if (updated) {
                Toast.makeText(getContext(), "Information updated", Toast.LENGTH_SHORT).show();
                etFirstName.setEnabled(false);
                etLastName.setEnabled(false);
            } else {
                Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
