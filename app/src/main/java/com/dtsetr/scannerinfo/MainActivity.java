package com.dtsetr.scannerinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dtsetr.scannerinfo.fragments.DetailFragment;
import com.dtsetr.scannerinfo.fragments.MenuFragment;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.vision.scan.HmsMLVisionScan;
import com.huawei.hms.ml.vision.scan.HmsMLVisionScanDetectorOptions;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQ_CODE = 111;
    private static final int REQUEST_CODE_SCAN = 0X01;
    private static final String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();
        openMenuFragment();
       }

    private void getToken() {
        Log.i(TAG, "get token: begin");

        new Thread() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(MainActivity.this).getString("client/app_id");
                    String pushToken = HmsInstanceId.getInstance(MainActivity.this).getToken(appId, "HCM");
                    if(!TextUtils.isEmpty(pushToken)) {
                        Log.i(TAG, "get token:" + pushToken);
                    }
                    else
                        Log.i(TAG, "Null token");
                } catch (Exception e) {
                    Log.i(TAG,"getToken failed, " + e);
                }
            }
        }.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQ_CODE && grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            ScanUtil.startScan(this, REQUEST_CODE_SCAN, new HmsMLVisionScanDetectorOptions.Builder().build());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null)
        {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsMLVisionScan) {
                if (!TextUtils.isEmpty(((HmsMLVisionScan) obj).rawValue)) {
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle args = new Bundle();
                    args.putString("content",((HmsMLVisionScan) obj).rawValue);
                    detailFragment.setArguments(args);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container,detailFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        }
    }

    private void openMenuFragment(){
        MenuFragment menuFragment = new MenuFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,menuFragment);
        ft.commit();
    }

}
