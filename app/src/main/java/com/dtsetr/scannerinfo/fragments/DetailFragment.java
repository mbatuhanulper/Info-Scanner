package com.dtsetr.scannerinfo.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtsetr.scannerinfo.R;

import java.util.Objects;


public class DetailFragment extends Fragment {

    private TextView detailText;

    public DetailFragment() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            String setText = getArguments().getString("content");
            detailText.setText(setText);
            detailText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailText = Objects.requireNonNull(getActivity()).findViewById(R.id.detailTextview);
        detailText.setVisibility(View.GONE);
    }

}
