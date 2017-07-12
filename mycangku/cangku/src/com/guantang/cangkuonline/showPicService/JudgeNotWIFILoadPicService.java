package com.guantang.cangkuonline.showPicService;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.webservice.WebserviceImport;

import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class JudgeNotWIFILoadPicService extends Service {
	
	private RefreshHPListViewListener mRefreshHPListViewListener;
	private MyBinder myBinder;
	private Context activityContext;
	private DIALOG_PROMPT dialog_Prompt = DIALOG_PROMPT.NONE;
	
	BroadcastReceiver connectionReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(MyApplication.getInstance().getSharedPreferences().getBoolean(ShareprefenceBean.NOTWIFILOADPIC, true)
					|| MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.IS_LOGIN, 1)==-1){
				return;
			}
			if(!WebserviceImport.isOpenNetwork(context)){
				return;
			}
			if(activityContext==null){
				return;
			}
			
			if(dialog_Prompt == DIALOG_PROMPT.EXIST){
				return;
			}
			
			
			if(!isWIFIAvailable(context)){
				if(!mRefreshHPListViewListener.getIsShowPic()){
					return;
				}
				
				dialog_Prompt = DIALOG_PROMPT.EXIST;
				
				showDialog(activityContext, "  wifi不可用，是否查看无图片的货品列表？",false);
			}else{
				if(mRefreshHPListViewListener.getIsShowPic()){
					return;
				}
				
				dialog_Prompt = DIALOG_PROMPT.EXIST;
				
				showDialog(activityContext, "  wifi可用，是否查看有图片的货品列表？",true);
			}
		}
		
	};
	
	// showpic true查看图片，false 不查看图片
	private void showDialog(Context context,String content,final Boolean showpic){
		CommonDialog comDialog = new CommonDialog(context, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
		comDialog.setDialogContentListener(new DialogContentListener() {
			
			@Override
			public void contentExecute(View parent, final Dialog dialog, Object[] objs) {
				TextView titleTextView = (TextView) parent.findViewById(R.id.title);
				TextView contextTextView = (TextView) parent.findViewById(R.id.content_txtview);
				TextView cancelTextView = (TextView) parent.findViewById(R.id.cancel);
				TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
				titleTextView.setVisibility(View.GONE);
				contextTextView.setText(objs[0].toString());
				cancelTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						dialog_Prompt = DIALOG_PROMPT.NONE;
						
						dialog.dismiss();
					}
				});
				confirmTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mRefreshHPListViewListener.executeRefresh(showpic);
						
						dialog_Prompt = DIALOG_PROMPT.NONE;
						
						dialog.dismiss();
					}
				});
			}
		},new String[]{content});
		comDialog.show();
	}
	
	private boolean isWIFIAvailable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.getType()==ConnectivityManager.TYPE_WIFI && networkInfo.isConnected() && networkInfo.isAvailable());
	}
	
	public class MyBinder extends Binder{
		
		public void setRefreshHPListViewListener(RefreshHPListViewListener refreshHPListViewListener){
			mRefreshHPListViewListener = refreshHPListViewListener;
		}
		
		
		public JudgeNotWIFILoadPicService getService()
		{
			return JudgeNotWIFILoadPicService.this;

		}
	}
	
	public void setActivityContext(Context context){
		this.activityContext=context;

	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myBinder = new MyBinder();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
//		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		registerReceiver(connectionReceiver, intentFilter);
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		myBinder = null;
		unregisterReceiver(connectionReceiver);
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	enum DIALOG_PROMPT{ //提示dialog是否存在
		EXIST,NONE;
	}
	
}
