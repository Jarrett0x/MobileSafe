package com.jxust.mobilesafe.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.jxust.mobilesafe.R;
import com.jxust.mobilesafe.utils.AppInfoUtils;
import com.jxust.mobilesafe.utils.IntentUtils;
import com.jxust.mobilesafe.utils.StreamUtils;
import com.jxust.mobilesafe.utils.ToastUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final int NEED_UPDATE = 1;

	private TextView tv_sp_version;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch(msg.what){
			case NEED_UPDATE:
				// 弹出升级提醒对话框，通过builder创建
				UpdateInfo info = (UpdateInfo) msg.obj;
				// 第一步弹出对话框
				AlertDialog.Builder builder = new Builder(SplashActivity.this);//这里只可以用SplashActivity.this
				builder.setTitle("升级提醒");
				builder.setMessage(info.desc);
				// 取消按钮
				builder.setNegativeButton("下次再说", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						IntentUtils.startActivityAndFinish(SplashActivity.this, HomeActivity.class);
					}
				});
				// 确认按钮
				builder.setPositiveButton("立即升级", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 下载新版本的APK，替换安装
						// 下载一般都会用到多线程的断点下载
						dialog.dismiss();
					}
				});
				builder.show();
				break;
			}
		};
	};
	
	private RelativeLayout rl_sp_root;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_sp_version = (TextView) findViewById(R.id.tv_sp_version);
		tv_sp_version.setText(AppInfoUtils.getAppVersionName(this));
		rl_sp_root = (RelativeLayout) findViewById(R.id.rl_sp_root);
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);// 动画
		aa.setDuration(2000);
		rl_sp_root.startAnimation(aa);
		checkVersion();
	}

	private void checkVersion() {
		// 耗时的操作不能写在主线程上，会导致主线程无响应，导致应用卡顿
		new Thread(){
			public void run(){
				try {
					// 获取URL
					URL url = new URL(getString(R.string.serverurl));
					// 通过URL打开连接 获得服务器端返回的连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 定义好之后指定参数
					conn.setRequestMethod("GET");// 指定请求方式
					conn.setConnectTimeout(5000);// 指定超时时间
					
					int code = conn.getResponseCode();// 获得响应码
					if(code == 200){
						// 服务器配置的json文件对应的输入流
						InputStream is = conn.getInputStream();
						String json = StreamUtils.readStream(is);
						JSONObject jsonObj = new JSONObject(json);
						UpdateInfo info = new UpdateInfo();
						int serverVersion = jsonObj.getInt("version");
						info.serverVersion = serverVersion;
						info.downloadurl = jsonObj.getString("downloadurl");
						info.desc = jsonObj.getString("desc");
						// 判断服务器的版本号和本地的版本号是否一致。如果服务器的版本号大于本地的版本号，需要升级
						if(serverVersion > AppInfoUtils.getAppVersionCode(getApplicationContext())){
							System.out.println("有新版本，需要升级！！");
							// 弹出升级提醒的对话框(是一个UI界面，要在主线程中实现)
							Message msg = Message.obtain();
							msg.obj = info;// 将这个对象放到消息中，给send出去，在上面的handler里接受
							msg.what = NEED_UPDATE;
							//handler.sendMessage(msg);// 可以在此延迟发送消息，观看完毕动画
							handler.sendMessageDelayed(msg, 2000);
						}else{
							System.out.println("无需更新");
							//Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
							IntentUtils.startActivityForDelayAndFinish(SplashActivity.this, HomeActivity.class, 2000);
						}
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
					// 子线程上不可以直接打吐司
					ToastUtils.show(SplashActivity.this, "URL路径错误，错误代码2001");
				} catch (IOException e) {
					e.printStackTrace();
					ToastUtils.show(SplashActivity.this, "网络错误，错误代码2002");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.show(SplashActivity.this, "服务器端配置文件错误，错误代码2003");
				}
			}
		}.start();
	}
	
	class UpdateInfo{
		int serverVersion;
		String downloadurl;
		String desc;
	}

}
