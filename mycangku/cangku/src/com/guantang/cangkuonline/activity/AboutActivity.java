package com.guantang.cangkuonline.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;
import com.guantang.cangkuonline.downloadservice.UpdateVersion_2;
import com.guantang.cangkuonline.helper.UpdateVersion;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layout1, layout2, layout3, layout4;
	private ImageButton backImgBtn;
	private FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void init() {
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout3 = (LinearLayout) findViewById(R.id.layout3);
		layout4 = (LinearLayout) findViewById(R.id.layout4);
		backImgBtn = (ImageButton) findViewById(R.id.back);

		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		backImgBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.layout1:
			intent.setClass(this, PayNoticeActivity.class);
			startActivity(intent);
			break;
		case R.id.layout2:
			intent.setClass(this, Helper.class);
			startActivity(intent);
			break;
		case R.id.layout3:
			if (WebserviceImport.isOpenNetwork(this)) {
//				agent = new FeedbackAgent(this);
//				agent.sync();
//				agent.startFeedbackActivity();
				FeedbackAPI.openFeedbackActivity();
			} else {
				Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.layout4:
//			UmengUpdateAgent.setUpdateAutoPopup(false);
//			UmengUpdateAgent.setUpdateOnlyWifi(false);
//			UmengUpdateAgent.forceUpdate(this);
//			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//
//					@Override
//					public void onUpdateReturned(int arg0, UpdateResponse arg1) {
//						// TODO �Զ����ɵķ������
//						switch (arg0) {
//			            case 0: // has update
//			                UmengUpdateAgent.showUpdateDialog(AboutActivity.this, arg1);
//			                break;
//			            case 1: // has no update
//			            	Toast.makeText(AboutActivity.this, "û�и���", Toast.LENGTH_SHORT).show();
//			                break;
//			            case 2: // none wifi
//			            	Toast.makeText(AboutActivity.this, "û��wifi���ӣ� ֻ��wifi�¸���", Toast.LENGTH_SHORT).show();
//			                break;
//			            case 3: // time out
//			            	Toast.makeText(AboutActivity.this, "��ʱ", Toast.LENGTH_SHORT).show();
//			                break;
//			            }
//					}
//
//			});
//			UmengUpdateAgent.update(this);
			
			if(WebserviceImport.isOpenNetwork(this)){
				new ApkUpdateAsyncTesk().execute();
			}else{
				Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	class ApkUpdateAsyncTesk extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO �Զ����ɵķ������
			String json = WebserviceImport_more.AndroidApkUpdate(MyApplication.getInstance().getVisionCode());
			return json;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				switch(jsonObject.getInt("Status")){
				case 1:
					JSONObject DatajsonObject = new JSONObject(jsonObject.getString("Data"));
					String apkUrlString=DatajsonObject.getString("ApkUrl");
					String UpdateText = DatajsonObject.getString("UpdateTxt");
					String apkName = (String) apkUrlString.subSequence(apkUrlString.lastIndexOf("/"), apkUrlString.length());
					
					CommonDialog myDialog = new CommonDialog(AboutActivity.this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
					myDialog.setDialogContentListener(new DialogContentListener() {
						
						@Override
						public void contentExecute(View parent, final Dialog dialog, final Object[] objs) {
							// TODO �Զ����ɵķ������
							TextView titleTextView = (TextView) parent.findViewById(R.id.title);
							TextView contentTextView = (TextView) parent.findViewById(R.id.content_txtview);
							TextView cancelTextView = (TextView) parent.findViewById(R.id.cancel);
							TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
							
							titleTextView.setVisibility(View.VISIBLE);
							titleTextView.setText("������ʾ");
							contentTextView.setText(objs[0].toString());
							cancelTextView.setText("ȡ��");
							confirmTextView.setText("����");
							
							cancelTextView.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO �Զ����ɵķ������
									dialog.dismiss();
								}
							});
							confirmTextView.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO �Զ����ɵķ������
									new UpdateVersion_2(AboutActivity.this, objs[1].toString(), objs[2].toString());
									dialog.dismiss();
								}
							});
						}
					}, new Object[]{UpdateText,apkName,apkUrlString});
					myDialog.show();
					
//					AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
//					builder.setTitle("������ʾ");
//					builder.setMessage(UpdateText);
//					builder.setPositiveButton("����", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO �Զ����ɵķ������
//							new UpdateVersion(AboutActivity.this, apkName, apkUrlString);
//							dialog.dismiss();
//						}
//					});
//					builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO �Զ����ɵķ������
//							dialog.dismiss();
//						}
//					});
//					builder.create().show();
					break;
				case -1:
					Toast.makeText(AboutActivity.this, "�������°汾������Ҫ����", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(AboutActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	
}