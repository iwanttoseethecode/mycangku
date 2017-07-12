package com.guantang.cangkuonline.activity;

import java.io.File;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseImport;
import com.guantang.cangkuonline.database.DataBaseManager;
import com.guantang.cangkuonline.database.DataBaseMethod;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.static_constant.PathConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ToolManagerActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;
	private ImageButton backImgBtn;
	private SharedPreferences mSharedPreferences;
	private Switch mySwitch;
//	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toolmanager);
		mSharedPreferences = MyApplication.getInstance().getSharedPreferences();
		init();
	}

	public void init() {
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout3 = (LinearLayout) findViewById(R.id.layout3);
		layout4 = (LinearLayout) findViewById(R.id.layout4);
		layout5 = (LinearLayout) findViewById(R.id.layout5);
		layout6 = (LinearLayout) findViewById(R.id.layout6);
		mySwitch = (Switch) findViewById(R.id.mySwitch);
		backImgBtn = (ImageButton) findViewById(R.id.back);
		
		backImgBtn.setOnClickListener(this);
		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
		layout6.setOnClickListener(this);
		
		if(MyApplication.getInstance().getSharedPreferences().getBoolean(ShareprefenceBean.NOTWIFILOADPIC, true)){
			mySwitch.setChecked(true);
		}else{
			mySwitch.setChecked(false);
		}
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				MyApplication.getInstance().getSharedPreferences().edit().putBoolean(ShareprefenceBean.NOTWIFILOADPIC, isChecked).commit();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
//		LayoutInflater inflater = LayoutInflater.from(this);
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.layout1:
			final CommonDialog commonDialog1 = new CommonDialog(this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
			commonDialog1.setCancelable(false);
			commonDialog1.setDialogContentListener(new DialogContentListener() {
				
				@Override
				public void contentExecute(View parent, Dialog dialog,Object[] objs) {
					// TODO �Զ����ɵķ������
					TextView mTextView = (TextView) parent.findViewById(R.id.content_txtview);
					TextView cancelTextView = (TextView) parent.findViewById(R.id.cancel);
					TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
					
					mTextView.setText("ȷ����ջ�Ʒ�б���");
					cancelTextView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO �Զ����ɵķ������
							commonDialog1.dismiss();
						}
					});
					confirmTextView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO �Զ����ɵķ������
							DataBaseMethod dm=new DataBaseMethod(ToolManagerActivity.this);
							dm.del_hp();
							mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_HP, "δ��������").commit();
							Toast.makeText(ToolManagerActivity.this, "��ճɹ�", Toast.LENGTH_SHORT).show();
							commonDialog1.dismiss();
						}
					});
				}
			},null);
			commonDialog1.show();
			
//			AlertDialog.Builder builder1=new AlertDialog.Builder(this,R.style.yuanjiao_activity);
//			View view = inflater.inflate(R.layout.prompt_dialog_layout, null);
//			TextView mTextView = (TextView) view.findViewById(R.id.content_txtview);
//			TextView cancelTextView = (TextView) view.findViewById(R.id.cancel);
//			TextView confirmTextView = (TextView) view.findViewById(R.id.confirm);
//			
//			mTextView.setText("ȷ����ջ�Ʒ�б���");
//			cancelTextView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO �Զ����ɵķ������
//					dialog.dismiss();
//				}
//			});
//			confirmTextView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO �Զ����ɵķ������
//					DataBaseMethod dm=new DataBaseMethod(ToolManagerActivity.this);
//					dm.del_hp();
//					mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_HP, "δ��������").commit();
//					Toast.makeText(ToolManagerActivity.this, "��ճɹ�", Toast.LENGTH_SHORT).show();
//					dialog.dismiss();
//				}
//			});
//			builder1.setView(view);
//			dialog=builder1.create();
//			dialog.show();
			
			break;
		case R.id.layout2:
			
			final CommonDialog commonDialog2 = new CommonDialog(this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
			commonDialog2.setCancelable(false);
			commonDialog2.setDialogContentListener(new DialogContentListener() {
				
				@Override
				public void contentExecute(View parent, Dialog dialog,Object[] objs) {
					// TODO �Զ����ɵķ������
					TextView mTextView2 = (TextView) parent.findViewById(R.id.content_txtview);
					TextView cancelTextView2 = (TextView) parent.findViewById(R.id.cancel);
					TextView confirmTextView2 = (TextView) parent.findViewById(R.id.confirm);
					mTextView2.setText("ȷ����ձ������ݿ⣿");
					cancelTextView2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO �Զ����ɵķ������
							commonDialog2.dismiss();
						}
					});
					confirmTextView2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO �Զ����ɵķ������
							deleteDatabase(DataBaseHelper.DB);
							mSharedPreferences.edit().putString("update_time_hp", "δ��������").commit();
							mSharedPreferences.edit().putString("update_time_lb", "δ��������").commit();
							mSharedPreferences.edit().putString("update_time_dw", "δ��������").commit();
							mSharedPreferences.edit().putString("update_time_type", "δ��������").commit();
							mSharedPreferences.edit().putString("update_time_ck", "δ��������").commit();
							Toast.makeText(ToolManagerActivity.this, "��ճɹ�", Toast.LENGTH_SHORT).show();
							commonDialog2.dismiss();
						}
					});
				}
			},null);
			commonDialog2.show();
			
//			AlertDialog.Builder builder2=new AlertDialog.Builder(this,R.style.yuanjiao_activity);
//			View view2 = inflater.inflate(R.layout.prompt_dialog_layout, null);
//			TextView mTextView2 = (TextView) view2.findViewById(R.id.content_txtview);
//			TextView cancelTextView2 = (TextView) view2.findViewById(R.id.cancel);
//			TextView confirmTextView2 = (TextView) view2.findViewById(R.id.confirm);
//			
//			mTextView2.setText("ȷ����ձ������ݿ⣿");
//			cancelTextView2.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO �Զ����ɵķ������
//					dialog.dismiss();
//				}
//			});
//			confirmTextView2.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO �Զ����ɵķ������
//					deleteDatabase(DataBaseHelper.DB);
//					mSharedPreferences.edit().putString("update_time_hp", "δ��������").commit();
//					mSharedPreferences.edit().putString("update_time_lb", "δ��������").commit();
//					mSharedPreferences.edit().putString("update_time_dw", "δ��������").commit();
//					mSharedPreferences.edit().putString("update_time_type", "δ��������").commit();
//					mSharedPreferences.edit().putString("update_time_ck", "δ��������").commit();
//					Toast.makeText(ToolManagerActivity.this, "��ճɹ�", Toast.LENGTH_SHORT).show();
//					dialog.dismiss();
//				}
//			});
//			builder2.setView(view2);
//			dialog=builder2.create();
//			dialog.show();
			break;
		case R.id.layout3:
			intent.setClass(this, BackUpActivity.class);
			startActivity(intent);
			break;
		case R.id.layout4:
			intent.setClass(this, RecoverActivity.class);
			startActivity(intent);
			break;
		case R.id.layout5:
			intent.setClass(this, ServerPref.class);
			startActivity(intent);
			break;
		case R.id.layout6:
			new DeletePicAsyncTask().execute();
			
			break;
		}
	}
	
	class DeletePicAsyncTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			deletePic("");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(ToolManagerActivity.this, "����ͼƬɾ���ɹ�", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void deletePic(String fileName){
    	File file = new File(PathConstant.PATH_photo+fileName);
    	File[] fileList = file.listFiles();
    	DataBaseManager dataBaseManager = DataBaseManager.getInstance(this);
    	SQLiteDatabase db = dataBaseManager.openDataBase();
    	
    	for(File f : fileList){
    		if(f.isDirectory()){
    			deletePic(f.getName());//ͼƬ�ļ�����û���ļ��У�һ�㲻��ִ�е�
    		}else{
    			db.execSQL("delete from "+DataBaseHelper.TB_PIC+" where "+DataBaseHelper.ImageURL+" = '"+f.getName()+"'");
    			f.delete();
    		}
    	}
    	dataBaseManager.closeDataBase();
    }
	
	
}