package com.guantang.cangkuonline.application;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.activity.ChildLoginActivity;
import com.guantang.cangkuonline.activity.NewLoginActivity;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity{
	
	/**
	 * 标记是否发了强迫下线通知，true 发了，false 没有发
	 * 
	 */
	private boolean LoginDownFlag = false;
	
	/**
	 * 推送的消息
	 * */
	private String ContentMessage;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		MyApplication.getInstance().pushActivityToStack(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(LoginDownFlag){
			loginDownDialog(getContentMessage());
			setLoginDownFlag(false);
		}

		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.getInstance().removeActivityToStack(this);
	}
	
	public String getContentMessage() {
		return TextUtils.isEmpty(ContentMessage)?"":ContentMessage;
	}

	public void setContentMessage(String contentMessage) {
		ContentMessage = contentMessage;
	}

	public boolean isLoginDownFlag() {
		return LoginDownFlag;
	}

	public void setLoginDownFlag(boolean loginDownFlag) {
		LoginDownFlag = loginDownFlag;
	}

	public void loginDownDialog(final String content){
		CommonDialog commonDialog = new CommonDialog(this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
		commonDialog.setCancelable(false);
		commonDialog.setDialogContentListener(new DialogContentListener() {
			
			@Override
			public void contentExecute(View parent, final Dialog dialog, Object[] objs) {
				// TODO Auto-generated method stub
				TextView titleTextView = (TextView) parent.findViewById(R.id.title);
				TextView contentTxtview = (TextView) parent.findViewById(R.id.content_txtview);
				TextView cancelTxtview = (TextView) parent.findViewById(R.id.cancel);
				
				titleTextView.setText("下线通知");
				titleTextView.setVisibility(View.VISIBLE);
				contentTxtview.setText(content);
				cancelTxtview.setText("确定");
				cancelTxtview.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent activityIntent = new Intent(BaseActivity.this,ChildLoginActivity.class);
			        	startActivity(activityIntent);
						dialog.dismiss();
					}
				});
				
			}
		}, content);
		commonDialog.show();
	}
	
}
