package com.guantang.cangkuonline.Jpushhlper;

import com.guantang.cangkuonline.R;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class JpushReceiverService extends Service {
	

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		setStyleBasic();
		setStyleCustom();
	}
	
	/**
	 * ����֪ͨ��ʾ��ʽ - ��������
	 */
	private void setStyleBasic() {
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(getApplicationContext());
		builder.statusBarDrawable = R.drawable.logo;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //����Ϊ������Զ���ʧ
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //����Ϊ������ Notification.DEFAULT_SOUND�������𶯣� Notification.DEFAULT_VIBRATE��
		JPushInterface.setPushNotificationBuilder(1, builder);
//		Toast.makeText(getApplicationContext(), "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	}


	/**
	 * ����֪ͨ����ʽ - ����֪ͨ��Layout
	 */
	private void setStyleCustom() {
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(getApplicationContext(), R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
		builder.layoutIconDrawable = R.drawable.logo;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(2, builder);
//		Toast.makeText(getApplicationContext(), "Custom Builder - 2", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
