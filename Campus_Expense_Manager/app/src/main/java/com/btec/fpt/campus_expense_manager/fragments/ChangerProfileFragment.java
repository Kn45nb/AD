package com.btec.fpt.campus_expense_manager.fragments;

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

import com.btec.fpt.campus_expense_manager.R;
import com.btec.fpt.campus_expense_manager.database.DatabaseHelper;
import com.btec.fpt.campus_expense_manager.entities.User;
import com.btec.fpt.campus_expense_manager.models.DataStatic;

public class ChangerProfileFragment extends Fragment {

    private EditText edtFirstName, edtLastName, edtEmail;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changer_profile, container, false);

        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtEmail = view.findViewById(R.id.edt_email);
        btnSave = view.findViewById(R.id.btn_save_profile);

        // Lấy email hiện tại
        String email = DataStatic.email;
        if (email == null || email.isEmpty()) {
            SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", 0);
            email = prefs.getString("email", "");
        }

        DatabaseHelper db = new DatabaseHelper(getContext());
        User user = db.getUserByEmail(email);

        // Hiển thị dữ liệu hiện tại (nếu có)
        if (user != null) {
            edtFirstName.setHint(user.getFirstName());
            edtLastName.setHint(user.getLastName());
            edtEmail.setHint(user.getEmail());
        }

        String finalEmail = email;
        btnSave.setOnClickListener(v -> {
            String newFirstName = edtFirstName.getText().toString().trim();
            String newLastName = edtLastName.getText().toString().trim();
            String newEmail = edtEmail.getText().toString().trim();

            // Nếu không nhập gì thì không update
            if (TextUtils.isEmpty(newFirstName) && TextUtils.isEmpty(newLastName) && TextUtils.isEmpty(newEmail)) {
                Toast.makeText(getContext(), "No changes to update!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy lại user để lấy id và các trường cũ
            User currentUser = db.getUserByEmail(finalEmail);
            if (currentUser == null) {
                Toast.makeText(getContext(), "User not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = currentUser.getId();
            String updateFirstName = TextUtils.isEmpty(newFirstName) ? currentUser.getFirstName() : newFirstName;
            String updateLastName = TextUtils.isEmpty(newLastName) ? currentUser.getLastName() : newLastName;
            String updateEmail = TextUtils.isEmpty(newEmail) ? currentUser.getEmail() : newEmail;
            String password = currentUser.getPassword();

            boolean ok = db.updateUser(userId, updateFirstName, updateLastName, updateEmail, password);
            if (ok) {
                Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                // Nếu đổi email thì cập nhật lại DataStatic và SharedPreferences
                if (!TextUtils.isEmpty(newEmail)) {
                    DataStatic.email = newEmail;
                    SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("email", newEmail);
                    editor.apply();
                }
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(getContext(), "Update failed!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
