package com.ayuan.permission.api;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.ayuan.permission.BaseActivity;

import java.util.ArrayList;

public class PermissionManager {
    public BaseActivity baseActivity;
    /**
     * 不被允许的权限集合
     */
    private String[] unauthorizedPermission;

    /**
     * 请求获取权限
     *
     * @param baseActivity
     */
    public void requestPermission(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        RequestPermission permission = baseActivity.getClass().getAnnotation(RequestPermission.class);
        //如果permission为空，则没有权限申请
        if (permission == null) {
            return;
        }

        //如果当前Android版本>=Android6.0版本并且有未被允许的权限则重新申请（haveBoolean方法有无不被允许的权限）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !haveBoolean(permission.value())) {
            baseActivity.requestPermissions(unauthorizedPermission, 1);
        }
    }

    /**
     * 检查已经申请的权限,将为申请的权限保存起来
     *
     * @param permissions
     * @return true 表示所有权限已经申请 false 表示部分权限被拒绝
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean haveBoolean(String... permissions) {
        //没有被允许的权限集合
        ArrayList<String> permissionList = new ArrayList<>();
        //循环检查每个权限是否申请
        for (String permission : permissions) {
            // 检查需要申请的权限在此应用中是否存在
            if (baseActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                //如果权限没有被允许，则添加到没有被允许的权限集合中
                permissionList.add(permission);
            }
        }

        // 如果存在没有被允许的权限则将List转换成array数组
        if (permissionList.size() > 0) {
            this.unauthorizedPermission = permissionList.toArray(new String[permissionList.size()]);
            return false;
        }
        return true;
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
