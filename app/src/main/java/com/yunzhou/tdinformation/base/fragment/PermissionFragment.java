package com.yunzhou.tdinformation.base.fragment;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.base.fragment
 *  @文件名:   PermissionFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/21 15:31
 *  @描述：
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class PermissionFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{
    public static final int Permission_Request_Code = 1000;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 检测权限
     */
    private void checkPermissions(){
        String[] permissions = getPermissions();
        if(null == permissions)
            return;
        boolean result = EasyPermissions.hasPermissions(getContext(),permissions);
        if (!result)
            EasyPermissions.requestPermissions(PermissionFragment.this,getPermissionsTip(),Permission_Request_Code,getPermissions());
        else
            onPermissionsGranted(Permission_Request_Code, Arrays.asList(permissions));
    }

    public void onPermissionsGranted(int requestCode, List<String> perms){
    }

    public void onPermissionsDenied(int requestCode, List<String> perms){
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 获取该fragment需要用到的权限
     * @return 权限列表
     */
    public String[] getPermissions(){
        return null;
    }

    /**
     * 获取该fragment需要用到的权限告知用户的提示
     * @return 弹出权限检查前提示
     */
    public String getPermissionsTip(){
        return null;
    }
}
