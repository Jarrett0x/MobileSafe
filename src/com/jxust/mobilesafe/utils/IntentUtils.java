package com.jxust.mobilesafe.utils;

import android.app.Activity;
import android.content.Intent;

public class IntentUtils {
	
	/**
	 * ����һ��Activity
	 * @param context ��ǰactivity
	 * @param cls Ҫ��ת��activity���ֽ����ļ�
	 */
	public static void startActivity(Activity context, Class<?> cls){
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}
	
	/**
	 * ����һ��Activity��������ǰActivity
	 * @param context ��ǰactivity
	 * @param cls Ҫ��ת��activity���ֽ����ļ�
	 */
	public static void startActivityAndFinish(Activity context, Class<?> cls){
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
		context.finish();
	}
	
	/**
	 * ����һ��Activity
	 * @param context ��ǰactivity
	 * @param cls Ҫ��ת��activity���ֽ����ļ�
	 * @param delayTime �ӳ�ִ�е�ʱ�����
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
	 * ����һ��Activity���ҽ�����ǰActivity
	 * @param context ��ǰactivity
	 * @param cls Ҫ��ת��activity���ֽ����ļ�
	 * @param delayTime �ӳ�ִ�е�ʱ�����
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
