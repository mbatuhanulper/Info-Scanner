package com.dtsetr.scannerinfo.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dtsetr.scannerinfo.R;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import java.util.Objects;


public class FragmentMap extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapViewDemoActivity";
    private static final int REQUEST_CODE = 100;
    private SupportMapFragment mSupportMapFragment;
    private static final LatLng LAT_LNG = new LatLng(31.2304, 121.4737);

    public FragmentMap() {

    }

    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewer);
        Objects.requireNonNull(mSupportMapFragment).getMapAsync(this);
        if (!hasPermissions(getContext(), RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),  RUNTIME_PERMISSIONS, REQUEST_CODE);
        }

    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.d(TAG, "onMapReady: ");
        huaweiMap.setMyLocationEnabled(true);
        CameraPosition build = new CameraPosition.Builder().target(new LatLng(41, 29 )).zoom(7).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
        huaweiMap.animateCamera(cameraUpdate);
        huaweiMap.setMaxZoomPreference(50);
        huaweiMap.setMinZoomPreference(2);

        Marker mMarker = huaweiMap.addMarker(new MarkerOptions().position(LAT_LNG)
                .alpha(0.5f)
                .infoWindowAnchor(0.5f, 0.5f)
                .clusterable(true));

        mMarker.setAnchor(0, -1);
        mMarker.showInfoWindow();
    }
    @Override
    public void onStart() {
        super.onStart();
        mSupportMapFragment.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mSupportMapFragment.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSupportMapFragment.onDestroy();
    }
    @Override
    public void onPause() {
        mSupportMapFragment.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportMapFragment.onResume();
    }
}
