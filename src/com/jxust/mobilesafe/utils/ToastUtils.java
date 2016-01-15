package com.jxust.mobilesafe.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtils {
	
	/**
	 * ��ʾ��˾�Ĺ����࣬��ȫ�Ĺ����࣬�����������߳�������ʾ
	 * @param activity
	 * @param text
	 */
	public static void show(final Activity activity, final String text){
		if("main".equalsIgnoreCase(Thread.currentThread().getName())){
			Toast.makeText(activity, text, 0).show();
		} else {
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(activity, text, 0).show();
				}
			});
		}
	}
	
	/**
	 * ��ʾ��˾�Ĺ����࣬��ȫ�Ĺ����࣬�����������߳�������ʾ
	 * @param activity
	 * @param text
	 * @param length ��ʾ��ʱ��
	 */
	public static void show(final Activity activity, final String text, final int length){
		if("main".equalsIgnoreCase(Thread.currentThread().getName())){
			Toast.makeText(activity, text, length).show();
		} else {
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(activity, text, length).show();
				}
			});
		}
	}
}
