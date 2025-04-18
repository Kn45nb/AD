package com.btec.fpt.campus_expense_manager.fragments;

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

public class NotificationSettingFragment extends Fragment {
    private Switch switchNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_setting, container, false);
        switchNotification = view.findViewById(R.id.switch_notification);

        SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", getContext().MODE_PRIVATE);
        boolean enabled = prefs.getBoolean("notification_enabled", true);
        switchNotification.setChecked(enabled);

        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("notification_enabled", isChecked).apply();
            Toast.makeText(getContext(), isChecked ? "Notifications enabled" : "Notifications disabled", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
