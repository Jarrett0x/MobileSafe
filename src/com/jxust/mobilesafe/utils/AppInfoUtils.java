package com.jxust.mobilesafe.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfoUtils {
	
	/**
	 * ��ȡapp�汾����
	 * @param context ������
	 * @return
	 */
	public static String getAppVersionName(Context context){
		
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);// flagΪ���ӿ�ѡ�Ĳ���ʱ��������0
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// �����ܷ���
			// con't catch
			return "";
		}
	}
	
	/**
	 * ��ȡapp�汾��
	 * @param context ������
	 * @return
	 */
	public static int getAppVersionCode(Context context){
		
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);// flagΪ���ӿ�ѡ�Ĳ���ʱ��������0
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// �����ܷ���
			// con't catch
			return 0;
		}
	}
	
}
