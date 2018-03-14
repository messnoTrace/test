package com.notrace;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mess on 2018/3/14.
 */
public class AppInfoAdapter extends ArrayAdapter<PackageInfo> {


    private List<AppInfo>list;
    private PackageManager packageManager;

    public AppInfoAdapter(Context context,List<AppInfo>list) {
        super(context, R.layout.item_appinfo);
        this.list=list;
        packageManager=context.getPackageManager();

    }

    static class ViewHoder{
        ImageView ivAppIcon;
        TextView tvAppName,tvAppVersion;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder viewHoder=null;
        if(convertView==null){
            viewHoder=new ViewHoder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appinfo,null);
            viewHoder.ivAppIcon=convertView.findViewById(R.id.iv_item_appinfo_icon);
            viewHoder.tvAppName=convertView.findViewById(R.id.tv_item_appinfo_appname);
            viewHoder.tvAppVersion=convertView.findViewById(R.id.tv_item_appinfo_version);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        PackageInfo info=list.get(position).getPackageInfo();
        viewHoder.tvAppName.setText(info.applicationInfo.loadLabel(packageManager));
        viewHoder.tvAppVersion.setText(info.versionName);
        try {
            viewHoder.ivAppIcon.setImageDrawable(packageManager.getApplicationIcon(info.packageName));

        }catch (Exception e){
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();

    }
}
