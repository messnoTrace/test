package com.notrace;

import android.content.pm.PackageInfo;


/**
 * Created by mess on 2018/3/14.
 */
public class AppInfo extends PackageInfo implements Comparable<AppInfo> {

    private PackageInfo packageInfo;

    public AppInfo(PackageInfo packageInfo){
        this.packageInfo=packageInfo;

    }


    @Override
    public int compareTo(AppInfo appInfo) {
        if(appInfo.getPackageInfo().lastUpdateTime>packageInfo.lastUpdateTime){
            return 1;

        }else if(appInfo.getPackageInfo().lastUpdateTime==packageInfo.lastUpdateTime){
            return 0;
        }else {
            return -1;
        }
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
