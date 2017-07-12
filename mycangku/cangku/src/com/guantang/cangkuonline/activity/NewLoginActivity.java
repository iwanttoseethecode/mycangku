package com.guantang.cangkuonline.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.Jpushhlper.AliasAndTagsSetting;
import com.guantang.cangkuonline.Jpushhlper.CompanyAlias;
import com.guantang.cangkuonline.Jpushhlper.JpushReceiverService;
import com.guantang.cangkuonline.Jpushhlper.LoginTags;
import com.guantang.cangkuonline.Jpushhlper.TagsBean;
import com.guantang.cangkuonline.adapter.ItemCanDeleteAdapter;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;
import com.guantang.cangkuonline.downloadservice.UpdateVersion_2;
import com.guantang.cangkuonline.eventbusBean.ObjectOne;
import com.guantang.cangkuonline.helper.EditHelper;
import com.guantang.cangkuonline.helper.PwdHelper;
import com.guantang.cangkuonline.helper.UpdateVersion;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.static_constant.PathConstant;
import com.guantang.cangkuonline.webservice.WebserviceHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import de.greenrobot.event.EventBus;

public class NewLoginActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	
	private TextView serverTxt;
	private EditText companyEdit,usernameAutoCompleteTextView,passwordAutoCompleteTextView;
	private Button loginBtn,lixianloginBtn;
	private CheckBox eyeCheckBox;
	private LinearLayout serverlayout,tiyanLinearLayout,registerlayout;
	private ImageView downImgView;
	private SharedPreferences mSharedPreferences;
	private String[] url_array;
	private String[] ser_array;
	private ProgressDialog pro_dialog;
	private PopupWindow mserverPopupWindow,downPopupWindow;
	private Boolean isdateup = true;// �Ƿ����
	private String username="",password="",dwname="";
	private PwdHelper pwdhelper = new PwdHelper();
	private ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
	private DataBaseOperateMethod dm_op = new DataBaseOperateMethod(this);
	private boolean tiyanflag = false;//����ϴε�¼��tiyan�˻� true,�ϴε�¼���������˻� false;
	/**
	 * ����������������Ƿ�������popupwindow�������ݵ�������
	 * */
	private ItemCanDeleteAdapter mItemCanDeleteAdapter;
	/**
	 * �Ƿ���ע��ҳ�淵�ص�����½����
	 * */
	private Boolean registerstartFlagBoolean = false;
	/**
	 * Ȩ���ַ���
	 * */
	private String rightString = "";
	/**
	 * �����������һ���޸�ʱ��
	 * */
	private String serConfLastModifyTime = "";
	
	private int selectServerNum=0;
	
	private Intent JpushIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newlogin);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
		
		EventBus.getDefault().register(this);
		mSharedPreferences = MyApplication.getInstance().getSharedPreferences();
		initView();
		
		JpushIntent = new Intent(this,JpushReceiverService.class);
		startService(JpushIntent);
	}
	
	@Override
	public void onBackPressed() {
		// TODO �Զ����ɵķ������
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}
	
	public void initView(){
		serverTxt=(TextView) findViewById(R.id.serverTxt);
		companyEdit = (EditText) findViewById(R.id.companyEdit);
		usernameAutoCompleteTextView = (EditText) findViewById(R.id.usernameEdit);
		passwordAutoCompleteTextView = (EditText) findViewById(R.id.passwordEdit);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		lixianloginBtn = (Button) findViewById(R.id.lixianloginBtn);
		eyeCheckBox = (CheckBox) findViewById(R.id.eye);
		tiyanLinearLayout = (LinearLayout) findViewById(R.id.tiyanlayout);
		registerlayout = (LinearLayout) findViewById(R.id.registerlayout);
		serverlayout = (LinearLayout) findViewById(R.id.serverlayout);
		downImgView = (ImageView) findViewById(R.id.downImgView);
		
		
		serverlayout.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		lixianloginBtn.setOnClickListener(this);
		tiyanLinearLayout.setOnClickListener(this);
		registerlayout.setOnClickListener(this);
		downImgView.setOnClickListener(this);
		eyeCheckBox.setOnCheckedChangeListener(this);
		
		if(mSharedPreferences.getBoolean(ShareprefenceBean.SHOWPASS, true)){
			eyeCheckBox.setChecked(true);
		}else{
			eyeCheckBox.setChecked(false);
		}
	}
	
	public void init(){
//		serverTxt.setText(mSharedPreferences.getString(
//				ShareprefenceBean.SER_NAME, ""));
//		String serTextString = serverTxt.getText().toString();
		int num = mSharedPreferences.getInt(ShareprefenceBean.SERVER_NUM, -1);
		selectServerNum = num;
		if(num>=0 && num<=3){
			serverTxt.setText(ser_array[num]);
			WebserviceHelper.URL = EditHelper.CheckHttp(url_array[num])+ url_array[num] + "/";
			if(num==3){
				companyEdit.setText(mSharedPreferences.getString(ShareprefenceBean.IDN_ALONE_URL, ""));
				WebserviceHelper.loginflag = 0;
			}else{
				companyEdit.setText(mSharedPreferences.getString(ShareprefenceBean.DWNAME_LOGIN, ""));
				WebserviceHelper.loginflag = 1;
			}
		}
//		if (serTextString.equals(mSharedPreferences.getString(ShareprefenceBean.ALONE_SERVICE_NAME, "���������û�"))) {// ���������û���������ַ�����Ǿ����ù�˾
//			companyEdit.setText(mSharedPreferences.getString(ShareprefenceBean.IDN_ALONE_URL, ""));
//			WebserviceHelper.URL = EditHelper.CheckHttp(url_array[3])
//					+ url_array[3] + "/";
//		} else {
//			for (int i = 0; i < 4; i++) {
//				if (serTextString.equals(ser_array[i])) {
//					WebserviceHelper.URL = EditHelper.CheckHttp(url_array[i])
//							+ url_array[i] + "/";
//					break;
//				}
//			}
//			companyEdit.setText(mSharedPreferences.getString(ShareprefenceBean.DWNAME_LOGIN, ""));
//		}
		usernameAutoCompleteTextView.setText(mSharedPreferences.getString(ShareprefenceBean.USERNAME, ""));
		passwordAutoCompleteTextView.setText(mSharedPreferences.getString(ShareprefenceBean.PASSWORD, ""));

//		WebserviceHelper.loginflag = mSharedPreferences.getInt(ShareprefenceBean.LOGIN_FLAG, 1);
		
	}

	@Override
	protected void onStart() {
		// TODO �Զ����ɵķ������
		super.onStart();
		
//		SQLiteDatabase db = MyApplication.getInstance().getDataBaseImport().getReadableDatabase();
//		Cursor c = db.rawQuery("select count(id) num from tb_login where company='����' and username = 'admin' and password = 'admin' and "+DataBaseHelper.miwenpassword+" = '"+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase()+"'",null);
//		if(c.moveToFirst()){
//			int num = c.getInt(c.getColumnIndex("num"));
//			if(num<1){
//				db.execSQL("insert into tb_login (company,username,password,miwenpassword)values (����','admin','admin','"+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase()+"')");
//			}
//		}
//		c.close();
//		db.close();
		
		url_array = new String[] {
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_IDN_URL1,
						"www3.gtcangku.com"),
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_IDN_URL2,
						"www.gtcangku.com"),
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_IDN_URL3,
						"www2.gtcangku.com"),
				mSharedPreferences.getString(ShareprefenceBean.IDN_ALONE_URL,
						"test3.gtcangku.com") };

		ser_array = new String[] {
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_NAME1,
						"�Ϻ�������"),
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_NAME2,
						"�㶫������"),
				mSharedPreferences.getString(ShareprefenceBean.SERVICE_NAME3,
						"����������"),
				mSharedPreferences.getString(
						ShareprefenceBean.ALONE_SERVICE_NAME, "����������") };
		if (registerstartFlagBoolean == false) {// �������ע��ҳ�淵��
			init();
		} else {
			registerstartFlagBoolean = false;
		}
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		if (System.currentTimeMillis() - mSharedPreferences.getLong(ShareprefenceBean.NOT_UPDATA, 0) > 3
				* 24 * 60 * 60 * 1000) {
//			if (isdateup) {
//				UmengUpdateAgent.setUpdateOnlyWifi(false);
//				UmengUpdateAgent.update(this);
//			}
//			UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
//
//				@Override
//				public void onClick(int status) {
//					switch (status) {
//					case UpdateStatus.Update:
//						mSharedPreferences.edit().putLong(
//								ShareprefenceBean.NOT_UPDATA, 0);
//						isdateup = true;
//						break;
//					case UpdateStatus.Ignore:
//						isdateup = false;
//						break;
//					case UpdateStatus.NotNow:
//						isdateup = false;
//						mSharedPreferences
//								.edit()
//								.putLong(ShareprefenceBean.NOT_UPDATA,
//										System.currentTimeMillis()).commit();
//						break;
//					}
//				}
//			});
			if(WebserviceImport.isOpenNetwork(this)){
				new ApkUpdateAsyncTesk().execute();
			}
		}
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		stopService(JpushIntent);
	}
	
	public void onEventMainThread(ObjectOne objectOne) {
		// TODO �Զ����ɵķ������
		Log.v("tag", 1 + "");
		String[] strArray = new String[3];
		strArray = objectOne.getMsg().split("\t");
		companyEdit.setText(strArray[0]);
		serverTxt.setText(strArray[1]);
		WebserviceHelper.URL = strArray[2];
		WebserviceHelper.loginflag = 1;
		usernameAutoCompleteTextView.setText("admin");
		passwordAutoCompleteTextView.setText("admin");
		registerstartFlagBoolean = true;
	}
	
	public void initServerPopupWindow(){
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.popupwindow_list, null);
		ListView myListView = (ListView) view.findViewById(R.id.popuplist);
		String[] littileArray = {"�Ϻ�   "+url_array[0],"�㶫   "+url_array[1],"����   "+url_array[2],"����   "+url_array[3]};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.popupwindow_list_textview,littileArray);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO �Զ����ɵķ������
				serverTxt.setText(ser_array[arg2]);
				selectServerNum = arg2;
				if(arg2 > -1 && arg2 < 3){
					WebserviceHelper.loginflag = 1;
					WebserviceHelper.URL = EditHelper.CheckHttp(url_array[arg2])+ url_array[arg2] + "/";
					//�����˾��һ���Ƕ���������������ַ������ա�
					if(companyEdit.getText().toString().equals(url_array[3])){
						companyEdit.setText("");
						companyEdit.setHint("�����뵥λ����");
					}
				}else if(arg2==3){//��������
					companyEdit.setHint("���������������ַ");
					companyEdit.setText(url_array[arg2]);
					WebserviceHelper.loginflag = 0;
				}
				mserverPopupWindow.dismiss();
			}
		});
		myListView.setAdapter(adapter);
//		serverTxt.measure(0, 0);
//		int width = serverTxt.getMeasuredWidth();
		int width = serverTxt.getWidth();
		mserverPopupWindow = new PopupWindow(view,width,LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ.
		mserverPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// ʹ��۽�
		mserverPopupWindow.setFocusable(true);
		// ����������������ʧ
		mserverPopupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		mserverPopupWindow.update();
		mserverPopupWindow.showAsDropDown(serverTxt);
	}
	
	public void initDownPopupWindow(){
		List<Map<String,Object>> mlist = dm_op.getLoginInfo_byCompany(companyEdit.getText().toString().trim());
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.popupwindow_list, null);
		ListView myListView = (ListView) view.findViewById(R.id.popuplist);
		mItemCanDeleteAdapter = new ItemCanDeleteAdapter(this,mlist);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO �Զ����ɵķ������
				Map<String,Object> map =  (Map<String, Object>) arg0.getAdapter().getItem(arg2);
				usernameAutoCompleteTextView.setText(map.get(DataBaseHelper.username).toString());
				passwordAutoCompleteTextView.setText(map.get(DataBaseHelper.password).toString());
				downPopupWindow.dismiss();
			}
		});
		myListView.setAdapter(mItemCanDeleteAdapter);
//		usernameAutoCompleteTextView.measure(0, 0);
		int width = usernameAutoCompleteTextView.getMeasuredWidth();
		downPopupWindow = new PopupWindow(view,width,LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ.
		downPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// ʹ��۽�
		downPopupWindow.setFocusable(true);
		// ����������������ʧ
		downPopupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		downPopupWindow.update();
		downPopupWindow.showAsDropDown(usernameAutoCompleteTextView);
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch(v.getId()){
		case R.id.serverlayout:
			initServerPopupWindow();
			break;
		case R.id.loginBtn:
			if (serverTxt.getText().toString().equals("")) {
				Toast.makeText(this, "��ѡ�������", Toast.LENGTH_SHORT).show();
			} else {
				if (WebserviceImport.isOpenNetwork(this)) {
					mSharedPreferences.edit().putInt(ShareprefenceBean.TIYANZHANGHAO, 0).commit();
					if(selectServerNum==3){
						WebserviceHelper.URL =EditHelper.CheckHttp(companyEdit.getText().toString().trim())+ companyEdit.getText().toString().trim() + "/";
					}
					pro_dialog = ProgressDialog.show(this, null, "���ڵ�¼����");
					username = usernameAutoCompleteTextView.getText().toString().trim();
					password = passwordAutoCompleteTextView.getText().toString().trim();
					dwname = companyEdit.getText().toString().trim();
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("dwname", dwname);
					map.put("UserName", username);
					map.put("password", pwdhelper.createMD5(password + "#cd@guantang").toUpperCase());
					map.put("IMEI", MyApplication.getInstance().getIEMI());
					map.put("Versions", MyApplication.getInstance().getVisionCode());
					map.put("PhoneSystem","Android");
					JSONObject jsonObject = new JSONObject(map);
					new LoginAsyncTask().execute(jsonObject.toString());
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("����δ���ӣ��Ƿ�������ߵ�¼��");
					builder.setNegativeButton("��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO �Զ����ɵķ������
									dialog.dismiss();
								}
							});
					builder.setPositiveButton("��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO �Զ����ɵķ������
									lixianlogin();
									dialog.dismiss();
								}
							});
					builder.create().show();
				}
			}
			break;
		case R.id.downImgView:
			initDownPopupWindow();
			break;
		case R.id.lixianloginBtn:
			mSharedPreferences.edit().putInt(ShareprefenceBean.TIYANZHANGHAO, 0).commit();
			lixianlogin();
			break;
		case R.id.tiyanlayout:
			if (WebserviceImport.isOpenNetwork(this)) {
				if(mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){
					tiyanflag = true;
				}else{
					tiyanflag = false;
				}
				pro_dialog = ProgressDialog.show(this, null, "���ڵ�¼����");
				mSharedPreferences.edit().putInt(ShareprefenceBean.TIYANZHANGHAO, 1).commit();
				WebserviceHelper.URL = "http://sh.gtcangku.com/";
				WebserviceHelper.loginflag=1;
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("dwname", "����");
				map.put("UserName", "admin");
				map.put("password", pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				map.put("IMEI", MyApplication.getInstance().getIEMI());
				map.put("Versions", MyApplication.getInstance().getVisionCode());
				map.put("PhoneSystem","Android");
				JSONObject jsonObject = new JSONObject(map);
				new LoginAsyncTask().execute(jsonObject.toString());
			} else {
				Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.registerlayout:
			Intent intent = new Intent(this, AddRegActivity.class);
			startActivity(intent);
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
					
					CommonDialog myDialog = new CommonDialog(NewLoginActivity.this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
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
									mSharedPreferences.edit().putLong(ShareprefenceBean.NOT_UPDATA,System.currentTimeMillis()).commit();
									dialog.dismiss();
								}
							});
							confirmTextView.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO �Զ����ɵķ������
									new UpdateVersion_2(NewLoginActivity.this, objs[1].toString(), objs[2].toString());
									dialog.dismiss();
								}
							});
						}
					}, new Object[]{UpdateText,apkName,apkUrlString});
					myDialog.show();
					break;
				case -1:
//					Toast.makeText(NewLoginActivity.this, "�������°汾������Ҫ����", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		
	}
	
	class LoginAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			if(JPushInterface.isPushStopped(getApplicationContext())){
				JPushInterface.resumePush(getApplicationContext());
			}
			//���������ʹ�����ͱ�ǩ
			jpushRegister();
			String jsonString = WebserviceImport_more.Login_Validate_2_0(params[0]);
			Log.v("AliasAndTag", jsonString);
			return jsonString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				switch(jsonObject.getInt("Status")){
				case 1:
					JSONObject dataObJsonObject = jsonObject.getJSONObject("Data");
					serConfLastModifyTime=dataObJsonObject.getString("GtLastupdate");
					WebserviceHelper.serid = dataObJsonObject.getString("ServerId");
					rightString = dataObJsonObject.getString("Gt_Rights");
					if(mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==0){
						dm_op.saveLoginInfo(dwname, username, password,pwdhelper.createMD5(password + "#cd@guantang").toUpperCase());	
					}else{
						dm_op.saveLoginInfo("����", "admin", "admin",pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
					}
					afterLogin();
					break;
				case -1:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -2:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -3:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -4:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -5:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -6:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -7:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -8:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -9:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -10:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -11:
					AlertDialog.Builder builder = new AlertDialog.Builder(
							NewLoginActivity.this);
					builder.setCancelable(false);
					builder.setMessage(jsonObject.getString("Message"));
					builder.setNegativeButton("�˳�����",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO �Զ����ɵķ������
									// �ر�app����
									finish();
									android.os.Process
											.killProcess(android.os.Process
													.myPid());
									System.exit(0);
								}
							});
					builder.setPositiveButton("��������",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO �Զ����ɵķ������
									afterLogin();
									
								}
							});
					builder.create().show();
					break;
				case -12:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -13:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -14:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -15:
					Toast.makeText(NewLoginActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
				pro_dialog.dismiss();
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				pro_dialog.dismiss();
			}
		}
	}
	
	public void afterLogin() {
		if (!mSharedPreferences
				.getBoolean(ShareprefenceBean.ISFIRST_LOGIN, true)) {
			if (mSharedPreferences.getString(ShareprefenceBean.NET_URL, "").equals(WebserviceHelper.URL)
					&& mSharedPreferences.getString(ShareprefenceBean.DWNAME_LOGIN, "").equals(dwname) 
					&& mSharedPreferences.getString(ShareprefenceBean.SERID, "").equals(WebserviceHelper.serid)) {
				saveLoginMessage();
				startFinalMainActivity2();
				finish();
			} else {
				
				CommonDialog myDialog = new CommonDialog(this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
				myDialog.setCancelable(false);
				myDialog.setDialogContentListener(new DialogContentListener() {
					
					@Override
					public void contentExecute(View parent, final Dialog dialog, Object[] objs) {
						// TODO �Զ����ɵķ������
						TextView titleTextView = (TextView) parent.findViewById(R.id.title);
						TextView contentTextView = (TextView) parent.findViewById(R.id.content_txtview);
						TextView cancelTextView = (TextView) parent.findViewById(R.id.cancel);
						TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
						
						titleTextView.setText("�������л�");
						contentTextView.setText("���׸ı�,��ֵ�¼�������ݿ�ᱻ��ա�");
						confirmTextView.setText("ȷ�ϵ�¼");
						cancelTextView.setText("��������");
						cancelTextView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO �Զ����ɵķ������
								cacheThreadPool.execute(ExitloadRun);
								dialog.dismiss();
							}
						});
						confirmTextView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO �Զ����ɵķ������
								deletePic();
								deleteDatabase(DataBaseHelper.DB);
								saveLoginMessage();
								clearSomeShareprefence();
								
								startFinalMainActivity1();
								
								finish();
								dialog.dismiss();
							}
						});
					}
				});
				myDialog.show();
				
			}
		} else {
			saveLoginMessage();
			startFinalMainActivity3();
			finish();
		}
	}
	
	protected void startFinalMainActivity1(){
		Intent intent = new Intent(NewLoginActivity.this,
				FinalMainActivity.class);
		if(tiyanflag){
			intent.putExtra("isSYNC", false);
		}else{
			intent.putExtra("isSYNC", true);
		}
		intent.putExtra("serConfLastModifyTime", serConfLastModifyTime);
		startActivity(intent);
	}
	
	protected void startFinalMainActivity2(){
		Intent intent = new Intent(NewLoginActivity.this,
				FinalMainActivity.class);
		intent.putExtra("isSYNC", false);
		intent.putExtra("serConfLastModifyTime", serConfLastModifyTime);
		startActivity(intent);
	}
	
	protected void startFinalMainActivity3(){
		Intent intent = new Intent(NewLoginActivity.this,
				FinalMainActivity.class);
		intent.putExtra("isSYNC", true);
		intent.putExtra("serConfLastModifyTime", serConfLastModifyTime);
		startActivity(intent);
	}
	
	public void deletePic() {
		File file = new File(PathConstant.PATH_photo);
		File[] fileList = file.listFiles();
		if (fileList!=null && fileList.length > 0) {
			for (File f : fileList){
				if (f.exists()) {
					f.delete();
				}
			}
		}
	}
	
	/**
	 * ���л��û�ʱ��Ҫ���һЩShareprefences,���ô˷���
	 * */
	public void clearSomeShareprefence(){
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_HP, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_CKKC, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_LB, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_DW, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_TYPE, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_CK, null).commit();
		mSharedPreferences.edit().putString(ShareprefenceBean.SHOUYE_CKMC, null).commit();
		mSharedPreferences.edit().putInt(ShareprefenceBean.SHOUYE_CKID, -1).commit();
//		mSharedPreferences.edit().putString(ShareprefenceBean.CUSTOM_DW, "").commit();
	}
	
	public void saveLoginMessage() {
		if(mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==0){//�������˻���������
			mSharedPreferences.edit().putString(ShareprefenceBean.USERNAME, username).commit();
			mSharedPreferences.edit().putString(ShareprefenceBean.MIWENPASSWORD,pwdhelper.createMD5(password + "#cd@guantang")
					.toUpperCase()).commit();
			mSharedPreferences.edit().putString(ShareprefenceBean.PASSWORD, password).commit();
			if (serverTxt.getText().toString().equals(mSharedPreferences.getString(
					ShareprefenceBean.ALONE_SERVICE_NAME, "����������"))) { // �����û�����Ķ���������ַ
				mSharedPreferences.edit()
				.putString(ShareprefenceBean.IDN_ALONE_URL, dwname)
				.commit();
				mSharedPreferences.edit()
				.putString(ShareprefenceBean.DWNAME_LOGIN, dwname).commit();
			} else {
				mSharedPreferences.edit()
				.putString(ShareprefenceBean.DWNAME_LOGIN, dwname).commit();
			}
			mSharedPreferences
			.edit()
			.putInt(ShareprefenceBean.SERVER_NUM,
					selectServerNum).commit();
			
		}
		
		mSharedPreferences.edit()
				.putString(ShareprefenceBean.NET_URL, WebserviceHelper.URL)
				.commit();
		mSharedPreferences
				.edit()
				.putInt(ShareprefenceBean.LOGIN_FLAG,
						WebserviceHelper.loginflag).commit();
		mSharedPreferences.edit()
				.putString(ShareprefenceBean.SERID, WebserviceHelper.serid)
				.commit();
		mSharedPreferences.edit()
				.putBoolean(ShareprefenceBean.ISFIRST_LOGIN, false).commit();

		mSharedPreferences.edit().putInt(ShareprefenceBean.IS_LOGIN, 1).commit();
		mSharedPreferences
				.edit()
				.putString(ShareprefenceBean.RIGHTS,rightString)
				.commit();
		if (eyeCheckBox.isChecked()) {
			mSharedPreferences.edit()
					.putBoolean(ShareprefenceBean.SHOWPASS, true).commit();
		} else {
			mSharedPreferences.edit()
					.putBoolean(ShareprefenceBean.SHOWPASS, false).commit();
		}
		
		
	}
	
	private void jpushRegister(){
		CompanyAlias companyAlias = new CompanyAlias(dwname,mSharedPreferences);
		
		TagsBean tagsBean=new TagsBean();
		tagsBean.setIMEI(MyApplication.getInstance().getIEMI());
		tagsBean.setUsername(username);
		
		LoginTags loginTags = new LoginTags(tagsBean);
		
		AliasAndTagsSetting aliasAndTagsSetting = new AliasAndTagsSetting(loginTags.returnTags(),companyAlias.returnAlias(),this,getMainLooper());
		aliasAndTagsSetting.sendAliasAndTags();
	}
	
	Runnable ExitloadRun = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			WebserviceImport.delMEI(MyApplication.getInstance().getIEMI(),
					mSharedPreferences);
			JPushInterface.stopPush(getApplicationContext());
		}
	};
	
	/**
	 * ���ߵ�¼
	 * */
	public void lixianlogin() {
		if (!mSharedPreferences.getBoolean(ShareprefenceBean.ISFIRST_LOGIN, true)) {// û�����ߵ�½�����޷����ߵ�¼
			username = usernameAutoCompleteTextView.getText().toString().trim();
			password = passwordAutoCompleteTextView.getText().toString().trim();
			dwname = companyEdit.getText().toString().trim();
			if (mSharedPreferences.getString(ShareprefenceBean.NET_URL, "").equals(WebserviceHelper.URL)
					&& mSharedPreferences.getString(ShareprefenceBean.DWNAME_LOGIN, "").equals(dwname)){
				int userflag=0; //1����ƥ�䵽�û�����0����û�ҵ��û���
				int passwordflag=0;//1����ƥ�䵽���룬0����û�ҵ�����
				List<Map<String,Object>> mlist = dm_op.getLoginInfo_byCompany(dwname);
				Iterator<Map<String,Object>> iterator = mlist.iterator();
				String miwenString = pwdhelper.createMD5(password + "#cd@guantang").toUpperCase();
				while(iterator.hasNext()){
					Map<String,Object> map = iterator.next();
					if(map.get(DataBaseHelper.username).toString().equals(username)){
						if(miwenString.equals(map.get(DataBaseHelper.miwenpassword))){
							mSharedPreferences.edit().putString(ShareprefenceBean.PASSWORD, password).commit();
							mSharedPreferences.edit().putInt(ShareprefenceBean.IS_LOGIN, -1).commit();
							if (eyeCheckBox.isChecked()) {
								mSharedPreferences.edit()
										.putBoolean(ShareprefenceBean.SHOWPASS, true).commit();
							} else {
								mSharedPreferences.edit()
										.putBoolean(ShareprefenceBean.SHOWPASS, false).commit();
							}
							passwordflag=1;
							userflag=1;
							liXianStartFinalMainActivity();
							finish();
							break;
						}
						userflag=1;
					}
				}
				if(userflag==0){
					Toast.makeText(this, "δ�ҵ���ǰ��˾���û��ڱ��ֻ���½��", Toast.LENGTH_LONG).show();
				}
				if(userflag==1&&passwordflag==0){
					Toast.makeText(this, "���벻��ȷ", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "���ϴ����ߵ�¼��˾��Ϣ��һ�£��޷����ߵ�¼", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "ȷ�����ߵ�¼֮�������ߵ�¼", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void liXianStartFinalMainActivity(){
		Intent intent = new Intent(NewLoginActivity.this,
				FinalMainActivity.class);
		intent.putExtra("isSYNC", false);
		intent.putExtra("serConfLastModifyTime", serConfLastModifyTime);
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO �Զ����ɵķ������
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// �ر�app����
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO �Զ����ɵķ������
		switch(buttonView.getId()){
		case R.id.eye:
			if (isChecked) {
				passwordAutoCompleteTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				eyeCheckBox.setBackground(getResources().getDrawable(R.drawable.eye_org));
			} else {
				passwordAutoCompleteTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				eyeCheckBox.setBackground(getResources().getDrawable(R.drawable.eye_gray));
			}
			break;
		}
	}
	
}