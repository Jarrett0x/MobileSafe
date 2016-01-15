package com.jxust.mobilesafe.utils;

import android.app.Activity;
import android.content.Intent;

public class IntentUtils {
	
	/**
	 * 开启一个Activity
	 * @param context 当前activity
	 * @param cls 要跳转的activity的字节码文件
	 */
	public static void startActivity(Activity context, Class<?> cls){
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}
	
	/**
	 * 开启一个Activity并结束当前Activity
	 * @param context 当前activity
	 * @param cls 要跳转的activity的字节码文件
	 */
	public static void startActivityAndFinish(Activity context, Class<?> cls){
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
		context.finish();
	}
	
	/**
	 * 开启一个Activity
	 * @param context 当前activity
	 * @param cls 要跳转的activity的字节码文件
	 * @param delayTime 延迟执行的时间毫秒
	 */
	public static void startActivityForDelay(final Activity context, final Class<?> cls, final long delayTime){
		
		new Thread(){
			public void run(){
				try {
					Thread.sleep(delayTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(context, cls);
				context.startActivity(intent);
			};
		}.start();
		
	}
	
	/**
	 * 开启一个Activity并且结束当前Activity
	 * @param context 当前activity
	 * @param cls 要跳转的activity的字节码文件
	 * @param delayTime 延迟执行的时间毫秒
	 */
	public static void startActivityForDelayAndFinish(final Activity context, final Class<?> cls, final long delayTime){
		
		new Thread(){
			public void run(){
				try {
					Thread.sleep(delayTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(context, cls);
				context.startActivity(intent);
				context.finish();
			};
		}.start();
		
	}
}
