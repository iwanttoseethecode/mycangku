package com.guantang.cangkuonline.Jpushhlper;

import java.util.Set;

import com.guantang.cangkuonline.webservice.WebserviceImport;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class AliasAndTagsSetting {
	
	private Set<String> TagSet;
	private String Alias;
	private Context context;
	private Handler mHandler;
	
	private String TAG = "AliasAndTag";
	
	
	public AliasAndTagsSetting(Set<String> tagSet, String alias,Context mContext,Looper mLooper) {
		super();
		TagSet = tagSet;
		Alias = alias;
		if(mContext instanceof Activity){
			this.context = mContext.getApplicationContext();
		}else{
			this.context = mContext;
		}
		
		initHandler(mLooper);
	}
	
	
	
	public AliasAndTagsSetting(Context mContext,Looper mLooper) {
		super();
		if(mContext instanceof Activity){
			this.context = mContext.getApplicationContext();
		}else{
			this.context = mContext;
		}
		initHandler(mLooper);
	}

	public void sendAliasAndTags(){
		if(!TextUtils.isEmpty(Alias)){
			mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, Alias));
		}else{
			throw new RuntimeException("没有打别名");
		}
		if(!TagSet.isEmpty()){
			mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, TagSet));
		}else{
			throw new RuntimeException("没有打标签");
		}
		Log.v(TAG, Alias);
		Log.v(TAG, TagSet.toString());
		
	}

	public void setTagSet(Set<String> tagSet) {
		TagSet = tagSet;
		
	}
	public void setAlias(String alias) {
		Alias = alias;
		
	}
	
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	
	public void initHandler(Looper mLooper){
		mHandler = new Handler(mLooper) {
			@Override
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case MSG_SET_ALIAS:
						JPushInterface.setAliasAndTags(context, (String) msg.obj, null, mAliasCallback);
						break;

					case MSG_SET_TAGS:
						JPushInterface.setAliasAndTags(context, null, (Set<String>) msg.obj, mTagsCallback);
						break;

					default:
						break;
				}
			}
		};
	}
	
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
				case 0:
					logs = "Set tag and alias success";
					break;

				case 6002:
					logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
					if (WebserviceImport.isOpenNetwork(context)) {
						mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 30);
					}
					break;

				default:
					logs = "Failed with errorCode = " + code;
			}
			Log.v(TAG, logs);
		}

	};

	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
				case 0:
					logs = "Set tag and alias success";
					break;

				case 6002:
					logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
					if (WebserviceImport.isOpenNetwork(context)) {
						mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 30);
					} else {
						Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					logs = "Failed with errorCode = " + code;
			}
			Log.v(TAG, logs);
		}

	};

}
