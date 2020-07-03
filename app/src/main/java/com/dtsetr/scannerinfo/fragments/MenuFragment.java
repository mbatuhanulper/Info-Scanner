package com.dtsetr.scannerinfo.fragments;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dtsetr.scannerinfo.R;

import java.util.Objects;


public class MenuFragment extends Fragment {
    private static final int CAMERA_REQ_CODE = 111;


    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        final Button scanButton = Objects.requireNonNull(getActivity()).findViewById(R.id.scan_button);
        final Button map_button = Objects.requireNonNull(getActivity()).findViewById(R.id.map_button);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        Objects.requireNonNull(getActivity()),
                        new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        CAMERA_REQ_CODE);
            }
        });

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanButton.setVisibility(View.GONE);
                map_button.setVisibility(View.GONE);
                FragmentMap fragmentMap = new FragmentMap();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,fragmentMap);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }
}
