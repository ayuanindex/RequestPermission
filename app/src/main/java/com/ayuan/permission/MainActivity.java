package com.ayuan.permission;

import android.Manifest;
import android.os.Bundle;

import com.ayuan.permission.api.RequestPermission;

@RequestPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS})
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

