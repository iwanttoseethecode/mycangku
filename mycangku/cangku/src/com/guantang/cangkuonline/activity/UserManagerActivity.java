package com.guantang.cangkuonline.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.commonadapter.CommonAdapter;
import com.guantang.cangkuonline.commonadapter.ViewHolder;
import com.guantang.cangkuonline.helper.RightsHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserManagerActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton backImgBtn,addImgBtn;
	private ListView newlistview;
	private SharedPreferences mSharedPreferences;
	private List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
	private UserAdapter mUserAdapter;
	private ProgressDialog pro_Dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usermanager);
		mSharedPreferences=MyApplication.getInstance().getSharedPreferences();
		init();
	}
	
	public void init(){
		backImgBtn = (ImageButton) findViewById(R.id.back);
		addImgBtn = (ImageButton) findViewById(R.id.addImgBtn);
		newlistview = (ListView) findViewById(R.id.newlistview);
		
		backImgBtn.setOnClickListener(this);
		addImgBtn.setOnClickListener(this);
		
		mUserAdapter = new UserAdapter(this, mList, R.layout.lbchoseitem);
		newlistview.setAdapter(mUserAdapter);
		
		
	}

	@Override
	protected void onStart() {
		// TODO �Զ����ɵķ������
		super.onStart();
		if(WebserviceImport.isOpenNetwork(this)){
			pro_Dialog = ProgressDialog.show(this, null, "���ڼ���");
			new GetUserAsyncTask().execute();
		}else{
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.addImgBtn:
			if(mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1)==1){
				if(!mSharedPreferences.getString(ShareprefenceBean.DWNAME_LOGIN, "").equals("����") && mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0)!=1){
					intent.setClass(this, AddUserActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(this, "�����û����������ֿ�", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, "����ģʽ���������û�", Toast.LENGTH_SHORT).show();
			}
			break;	
		}
	}
	
	class GetUserAsyncTask extends AsyncTask<Void,Void,String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO �Զ����ɵķ������
			String json=WebserviceImport_more.Get_User_1_0();
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
					mList.clear();
					JSONArray dataJsonArray = jsonObject.getJSONArray("Data");
					int length=dataJsonArray.length();
					for(int i = 0;i<length;i++){
						JSONObject itemObject=(JSONObject) dataJsonArray.get(i);
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("id", itemObject.get("id"));
						map.put("name", itemObject.get("name"));
						map.put("pwd", itemObject.get("pwd"));
						map.put("gid", itemObject.get("gid"));
						map.put("eid",itemObject.get("eid"));
						map.put("ckidlist", itemObject.get("ckidlist"));
						map.put("hplxindex", itemObject.get("hplxindex"));
						mList.add(map);
					}
					mUserAdapter.setData(mList);
					break;
				case -1:
					Toast.makeText(UserManagerActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -2:
					Toast.makeText(UserManagerActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				case -3:
					Toast.makeText(UserManagerActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(UserManagerActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}finally{
				pro_Dialog.dismiss();
			}
		}
	}
	
	class UserAdapter extends CommonAdapter<Map<String,Object>>{

		public UserAdapter(Context mContext, List<Map<String, Object>> mList,
				int LayoutId) {
			super(mContext, mList, LayoutId);
			// TODO �Զ����ɵĹ��캯�����
		}

		@Override
		public void convert(ViewHolder holder, Map<String, Object> item) {
			// TODO �Զ����ɵķ������
			TextView mTextView = holder.getView(R.id.lbitem);
			mTextView.setText(item.get("name").toString());
		}
		
	}
	
}