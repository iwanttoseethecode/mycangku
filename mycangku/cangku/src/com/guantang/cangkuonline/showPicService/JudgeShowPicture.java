package com.guantang.cangkuonline.showPicService;

import com.guantang.cangkuonline.showPicService.JudgeNotWIFILoadPicService.MyBinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.BaseAdapter;

public class JudgeShowPicture implements RefreshHPListViewListener{
	private Context context;
	private MyJudgePictureAdapter mBaseAdapter;
	
	
	
	public JudgeShowPicture(Context mContext, MyJudgePictureAdapter mBaseAdapter) {
		super();
		this.context = mContext;
		this.mBaseAdapter = mBaseAdapter;
		Intent intent = new Intent(context,JudgeNotWIFILoadPicService.class);
		context.bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
	}

	ServiceConnection myServiceConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			MyBinder binder = (MyBinder) service;
			binder.setRefreshHPListViewListener(JudgeShowPicture.this);
			binder.getService().setActivityContext(context);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}};

	@Override
	public void executeRefresh(boolean showpic) {
		// TODO Auto-generated method stub
		mBaseAdapter.setShowPic(showpic);
	}
	
	@Override
	public boolean getIsShowPic() {
		
		return mBaseAdapter.getShowPic();
	}
	
	/**
	 * activity 销毁的时候一定要调用此方法
	 * */
	public void unBinder(){
		context.unbindService(myServiceConnection);
	}


	
	
}
