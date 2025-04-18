package com.btec.fpt.campus_expense_manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.btec.fpt.campus_expense_manager.R;

public class InfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView tvInfo = view.findViewById(R.id.tv_info);
        tvInfo.setText("Campus Expense Manager App\nVersion: rev 2.0\nCopyright © 2024");
        return view;
    }
}
