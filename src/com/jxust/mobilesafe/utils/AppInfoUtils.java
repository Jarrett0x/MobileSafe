package com.jxust.mobilesafe.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfoUtils {
	
	/**
	 * 获取app版本名称
	 * @param context 上下文
	 * @return
	 */
	public static String getAppVersionName(Context context){
		
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);// flag为附加可选的参数时，可以填0
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// 不可能发生
			// con't catch
			return "";
		}
	}
	
	/**
	 * 获取app版本号
	 * @param context 上下文
	 * @return
	 */
	public static int getAppVersionCode(Context context){
		
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);// flag为附加可选的参数时，可以填0
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// 不可能发生
			// con't catch
			return 0;
		}
	}
	
}
