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
				// �����������ѶԻ���ͨ��builder����
				UpdateInfo info = (UpdateInfo) msg.obj;
				// ��һ�������Ի���
				AlertDialog.Builder builder = new Builder(SplashActivity.this);//����ֻ������SplashActivity.this
				builder.setTitle("��������");
				builder.setMessage(info.desc);
				// ȡ����ť
				builder.setNegativeButton("�´���˵", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						IntentUtils.startActivityAndFinish(SplashActivity.this, HomeActivity.class);
					}
				});
				// ȷ�ϰ�ť
				builder.setPositiveButton("��������", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �����°汾��APK���滻��װ
						// ����һ�㶼���õ����̵߳Ķϵ�����
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
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);// ����
		aa.setDuration(2000);
		rl_sp_root.startAnimation(aa);
		checkVersion();
	}

	private void checkVersion() {
		// ��ʱ�Ĳ�������д�����߳��ϣ��ᵼ�����߳�����Ӧ������Ӧ�ÿ���
		new Thread(){
			public void run(){
				try {
					// ��ȡURL
					URL url = new URL(getString(R.string.serverurl));
					// ͨ��URL������ ��÷������˷��ص�����
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// �����֮��ָ������
					conn.setRequestMethod("GET");// ָ������ʽ
					conn.setConnectTimeout(5000);// ָ����ʱʱ��
					
					int code = conn.getResponseCode();// �����Ӧ��
					if(code == 200){
						// ���������õ�json�ļ���Ӧ��������
						InputStream is = conn.getInputStream();
						String json = StreamUtils.readStream(is);
						JSONObject jsonObj = new JSONObject(json);
						UpdateInfo info = new UpdateInfo();
						int serverVersion = jsonObj.getInt("version");
						info.serverVersion = serverVersion;
						info.downloadurl = jsonObj.getString("downloadurl");
						info.desc = jsonObj.getString("desc");
						// �жϷ������İ汾�źͱ��صİ汾���Ƿ�һ�¡�����������İ汾�Ŵ��ڱ��صİ汾�ţ���Ҫ����
						if(serverVersion > AppInfoUtils.getAppVersionCode(getApplicationContext())){
							System.out.println("���°汾����Ҫ��������");
							// �����������ѵĶԻ���(��һ��UI���棬Ҫ�����߳���ʵ��)
							Message msg = Message.obtain();
							msg.obj = info;// ���������ŵ���Ϣ�У���send��ȥ���������handler�����
							msg.what = NEED_UPDATE;
							//handler.sendMessage(msg);// �����ڴ��ӳٷ�����Ϣ���ۿ���϶���
							handler.sendMessageDelayed(msg, 2000);
						}else{
							System.out.println("�������");
							//Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
							IntentUtils.startActivityForDelayAndFinish(SplashActivity.this, HomeActivity.class, 2000);
						}
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
					// ���߳��ϲ�����ֱ�Ӵ���˾
					ToastUtils.show(SplashActivity.this, "URL·�����󣬴������2001");
				} catch (IOException e) {
					e.printStackTrace();
					ToastUtils.show(SplashActivity.this, "������󣬴������2002");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.show(SplashActivity.this, "�������������ļ����󣬴������2003");
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
