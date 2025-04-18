package com.btec.fpt.campus_expense_manager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.btec.fpt.campus_expense_manager.R;

public class NoiFragment extends Fragment {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_NOTIFICATION = "notification_enabled";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noi, container, false);

        Switch switchNotification = view.findViewById(R.id.switch_notification);

        // Load trạng thái từ SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean enabled = prefs.getBoolean(KEY_NOTIFICATION, true);
        switchNotification.setChecked(enabled);

        switchNotification.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_NOTIFICATION, isChecked);
            editor.apply();
            Toast.makeText(getContext(),
                    isChecked ? "Notifications enabled" : "Notifications disabled",
                    Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
