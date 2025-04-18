package com.btec.fpt.campus_expense_manager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.btec.fpt.campus_expense_manager.R;
import com.btec.fpt.campus_expense_manager.database.DatabaseHelper;
import com.btec.fpt.campus_expense_manager.entities.User;
import com.btec.fpt.campus_expense_manager.models.DataStatic;

public class ProfileFragment extends Fragment {
    private TextView tvEmail, tvFirstName, tvLastName;
    private Button btnEditProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvEmail = view.findViewById(R.id.tv_email);
        tvFirstName = view.findViewById(R.id.tv_first_name);
        tvLastName = view.findViewById(R.id.tv_last_name);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);

        // Lấy email từ SharedPreferences hoặc DataStatic
        String email = DataStatic.email;
        if (email == null || email.isEmpty()) {
            SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", 0);
            email = prefs.getString("email", "");
        }

        // Lấy thông tin user từ database
        DatabaseHelper db = new DatabaseHelper(getContext());
        User user = db.getUserByEmail(email);

        if (user != null) {
            tvEmail.setText("Email: " + user.getEmail());
            tvFirstName.setText("First Name: " + user.getFirstName());
            tvLastName.setText("Last Name: " + user.getLastName());
        } else {
            tvEmail.setText("Email: ");
            tvFirstName.setText("First Name: ");
            tvLastName.setText("Last Name: ");
        }

        btnEditProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ChangerProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
