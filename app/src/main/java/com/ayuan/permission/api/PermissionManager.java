package com.ayuan.permission.api;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.ayuan.permission.BaseActivity;

import java.util.ArrayList;

public class PermissionManager {
    public BaseActivity baseActivity;
    private String[] unauthorizedPermission;

    /**
     * 检查已经申请的权限,将为申请的权限保存起来
     *
     * @param permissions
     * @return true 表示所有权限已经申请 false 表示部分权限被拒绝
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean haveBoolean(String... permissions) {
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            // 检查需要申请的权限在此应用中是否存在
            if (baseActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        // 将List转换成array数组
        if (permissionList.size() > 0) {
            this.unauthorizedPermission = permissionList.toArray(new String[permissionList.size()]);
            return false;
        }
        return true;
    }

    /**
     * 请求获取权限
     *
     * @param baseActivity
     */
    public void requestPermission(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        RequestPermission permission = baseActivity.getClass().getAnnotation(RequestPermission.class);
        if (permission == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !haveBoolean(permission.value())) {
            baseActivity.requestPermissions(unauthorizedPermission, 1);
        }
    }

    /**
     * 权限申请结果通知,需要在activity onRequestPermissionResult方法中调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    /*public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                baseActivity.onPermissionRefuse(permissions[i]);
            }
        }
    }*/
}
