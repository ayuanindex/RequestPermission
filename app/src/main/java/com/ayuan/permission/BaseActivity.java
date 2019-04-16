package com.ayuan.permission;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.ayuan.permission.api.PermissionManager;

public class BaseActivity extends FragmentActivity {
    private static final String TAG = "BaseActivity";
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionManager = new PermissionManager();
        permissionManager.requestPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       /* permissionManager.onRequestPermissionResult(requestCode, permissions, grantResults);*/
    }

    /**
     * 权限拒绝时的回调
     *
     * @param permission
     */
    /*public void onPermissionRefuse(String permission) {
        Log.i(TAG, "哈哈:" + permission);
    }*/

}
