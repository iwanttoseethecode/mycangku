package com.guantang.cangkuonline.showPicService;

import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class  MyJudgePictureAdapter extends BaseAdapter {
	/**
	 * 决定货品图片是否显示， true显示，false不显示
	 * */
	protected boolean showPic;
	protected int IS_LOGIN = -1; //是否在线登陆过  1是在线登陆  -1离线登陆
	
	
	
	public MyJudgePictureAdapter() {
		super();
		IS_LOGIN = MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.IS_LOGIN, -1);
		boolean notwifiloadpic=MyApplication.getInstance().getSharedPreferences().getBoolean(ShareprefenceBean.NOTWIFILOADPIC, true);
		if(!notwifiloadpic){
			showPic = (IS_LOGIN==1&&isWIFIAvailable(MyApplication.getInstance().getApplicationContext()))?true:false;
		}else{
			showPic = IS_LOGIN==1?true:false;
		}
		
	}
	
	private boolean isWIFIAvailable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected() && networkInfo.getType()==ConnectivityManager.TYPE_WIFI);
	}

	@Override
	public abstract int getCount();

	@Override
	public abstract Object getItem(int position);

	@Override
	public abstract long getItemId(int position);

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	public void setShowPic(boolean showPic){
		this.showPic = showPic;
		notifyDataSetChanged();
	}
	
	public boolean getShowPic(){
		return showPic;
	}
}
