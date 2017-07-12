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
	 * ������ƷͼƬ�Ƿ���ʾ�� true��ʾ��false����ʾ
	 * */
	protected boolean showPic;
	protected int IS_LOGIN = -1; //�Ƿ����ߵ�½��  1�����ߵ�½  -1���ߵ�½
	
	
	
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
