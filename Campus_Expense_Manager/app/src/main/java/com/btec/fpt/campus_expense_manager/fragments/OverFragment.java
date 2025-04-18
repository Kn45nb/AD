package com.btec.fpt.campus_expense_manager.fragments;

import android.content.Context;
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

public class OverFragment extends Fragment {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_OV = "ov_percent";

    private EditText editOv;
    private Button btnSaveOv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_over, container, false);

        editOv = view.findViewById(R.id.edit_ov);
        btnSaveOv = view.findViewById(R.id.btn_save_ov);

        // Load current value
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int ov = prefs.getInt(KEY_OV, 100);
        editOv.setText(String.valueOf(ov));

        btnSaveOv.setOnClickListener(v -> {
            String input = editOv.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }
            int value;
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (value < 1 || value > 100) {
                Toast.makeText(getContext(), "Value must be between 1 and 100", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putInt(KEY_OV, value).apply();
            Toast.makeText(getContext(), "Saved OV: " + value + "%", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
