package com.guantang.cangkuonline.activity;

import com.guantang.cangkuonline.showPicService.JudgeShowPicture;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.XListView.XListView;
import com.guantang.cangkuonline.XListView.XListView.IXListViewListener;
import com.guantang.cangkuonline.adapter.DemoAdapter;
import com.guantang.cangkuonline.adapter.HpListBaseAdapter;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseMethod;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.dialog.DiaoboAddNumDialog;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.showPicService.JudgeNotWIFILoadPicService;
import com.guantang.cangkuonline.showPicService.RefreshHPListViewListener;
import com.guantang.cangkuonline.showPicService.JudgeNotWIFILoadPicService.MyBinder;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;

public class HpManagerListActivity extends BaseActivity implements OnClickListener,TextWatcher, OnItemClickListener, IXListViewListener{
	
	private ImageButton backImgBtn, scanImgBtn;
	private ImageView searchDelBtn;
	private ImageView up_downImgView;
	private TextView titleTextView, titleTextView1, filterImgBtn;
	private EditText mEditText;
	private ExpandableListView title_listView1;
	private XListView mXListView;
	private RelativeLayout title_layout, title_changeLayout;
	private DataBaseMethod dm = new DataBaseMethod(this);
	private DataBaseOperateMethod dm_op = new DataBaseOperateMethod(this);
	private String[] str1 = { DataBaseHelper.ID,DataBaseHelper.HPMC, DataBaseHelper.HPBM,
			DataBaseHelper.HPTM,DataBaseHelper.GGXH, DataBaseHelper.CurrKC};
	private String[] str2 = { DataBaseHelper.ID, DataBaseHelper.HPMC,
			DataBaseHelper.HPBM, DataBaseHelper.HPTM, DataBaseHelper.GGXH,
			DataBaseHelper.CurrKC,DataBaseHelper.KCSL,"CompressImageURL" };
	private String[] str3 = { "id", "HPMC", "HPBM", "HPTBM",
			"GGXH", "CurrKc","kcsl","CompressImageURL"};
	private String[] str15 = { "ID", "CKMC", "FZR", "TEL", "ADDR", "inact",
			"outact", "BZ" };
	private String[] str_ck = { DataBaseHelper.ID, DataBaseHelper.CKMC,
			DataBaseHelper.FZR, DataBaseHelper.TEL, DataBaseHelper.ADDR,
			DataBaseHelper.INACT, DataBaseHelper.OUTACT, DataBaseHelper.BZ };
	private List<Map<String, Object>> ckmc_array = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> cklist = new ArrayList<Map<String, Object>>();
	private List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String, Object>>>();
	private List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
	private SharedPreferences mSharedPreferences;
	private DemoAdapter mDemoAdapter;
	private HpListBaseAdapter myHpListBaseAdapter;
	private SimpleDateFormat formatter;
	private String ckmcStr,hptagStr = "��Ʒ��Ϣ";
	private int ckid = -1,ScreenWidth;
	private String[] conditionArray = { "��Ʒ��Ϣ", "���ձ仯", "��治��", "������" };
	private PopupWindow mPopupWindow;
	
	private String sqlString = "";
	private int hasckkc =0; //�Ƿ��п�� �п��1��û�п��-1��0���޿�涼��ʾ
	
	private Semaphore semaphore = new Semaphore(1);
	
	/**
	 * �ж��б�ˢ��ʹ�����ֽӿ�
	 * 0 ��ʾѡ��title֮������ּ��ط�ʽ
	 * 1��ʾ��������������ݵļ��ط�ʽ
	 * 2��ʾ���ɨ��ļ��ط�ʽ
	 * 3��ʾɸѡҳ������ļ��ط�ʽ
	 * */
	private int loadFlag = 0;
	
	private JudgeShowPicture judgeShowPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hpinfo_list_activity);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenWidth = dm.widthPixels;
		initView();
		init();
	}
	
	
	
	public void initView(){
		title_changeLayout = (RelativeLayout) this
				.findViewById(R.id.title_change);
		title_layout = (RelativeLayout) this.findViewById(R.id.titlelayout);
		up_downImgView = (ImageView) this.findViewById(R.id.up_down);
		titleTextView = (TextView) this.findViewById(R.id.title);
		titleTextView1 = (TextView) this.findViewById(R.id.title1);
		backImgBtn = (ImageButton) this.findViewById(R.id.back);
		filterImgBtn = (TextView) this.findViewById(R.id.filter1);
		scanImgBtn = (ImageButton) this.findViewById(R.id.scan);
		searchDelBtn = (ImageView) this.findViewById(R.id.del_cha);
		mEditText = (EditText) this.findViewById(R.id.listtext);
		mXListView = (XListView) this.findViewById(R.id.hplist_load);
		
		filterImgBtn.setOnClickListener(this);
		title_changeLayout.setOnClickListener(this);
		backImgBtn.setOnClickListener(this);
		scanImgBtn.setOnClickListener(this);
		searchDelBtn.setOnClickListener(this);
		mXListView.setXListViewListener(this);
		mXListView.setOnItemClickListener(this);
		mEditText.addTextChangedListener(this);
		mEditText.setOnClickListener(this);
		
		
	}
	
	public void init(){
		mSharedPreferences = MyApplication.getInstance().getSharedPreferences();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		myHpListBaseAdapter = new HpListBaseAdapter(this);
		mXListView.setAdapter(myHpListBaseAdapter);
		
		Intent intent = getIntent();
		if (intent.getBooleanExtra("itmeClick", false)) {// ��ҳ�������������activity
			ckmcStr = intent.getStringExtra("ckmc");
			hptagStr = intent.getStringExtra("hptag");
			ckid = intent.getIntExtra("ckid", -1);
			if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == 1) {
				if (WebserviceImport.isOpenNetwork(this)) {
					mXListView.setPullLoadEnable(true);// ���ÿ��Լ��ظ���
					mXListView.setPullRefreshEnable(true);// ���ÿ���ˢ��
					new GetCKListAsyncTask().execute();
				} else {
					Toast.makeText(this, "����δ����", Toast.LENGTH_LONG).show();
				}
			}else if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == -1) {
				mXListView.setPullLoadEnable(false);// ���ò����Լ��ظ���
				mXListView.setPullRefreshEnable(false);// ���ò�����ˢ��
				cklist = dm.getAllCK();
				addExpandableListElement();
			}
			titleHpInfoLoad();
		}else{
			if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == 1) {
				mXListView.setPullLoadEnable(true);// ���ÿ��Լ��ظ���
				mXListView.setPullRefreshEnable(true);// ���ÿ���ˢ��
				if (WebserviceImport.isOpenNetwork(this)) {// ������
					hptagStr = "��Ʒ��Ϣ";
					new GetCKListAsyncTask().execute();
					if(intent.getIntExtra("scanOrsearch", -1)==1){//�ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
						mEditText.setText(intent.getStringExtra("searchString"));
						new GetHpInfoSearchAsyncTask().execute("search","0",String.valueOf(ckid),mEditText.getText().toString());
					}else if(intent.getIntExtra("scanOrsearch", -1)==2){
						mEditText.setText(intent.getStringExtra("searchString"));
						new SearchHPbyTMAsyncTask().execute(mEditText.getText().toString());
					}else{
						new GetHpInfoAsyncTask().execute(getType(hptagStr),"0",String.valueOf(ckid));
					}
				} else {// û����
					Toast.makeText(this, "����δ����", Toast.LENGTH_LONG).show();
				}
			} else if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == -1) {
				mXListView.setPullLoadEnable(false);// ���ò����Լ��ظ���
				mXListView.setPullRefreshEnable(false);// ���ò�����ˢ��
				cklist = dm.getAllCK();
				addExpandableListElement();
				if(intent.getIntExtra("scanOrsearch", -1)==1){//�ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
					mEditText.setText(intent.getStringExtra("searchString"));
					if(ckid==-1){
						mList = dm.queryList(str1, mEditText.getText().toString(),ckid);
					}else{
						mList = dm.queryList(str2, mEditText.getText().toString(),ckid);
					}
					if(mList.isEmpty()){
						Toast.makeText(this, "������Ʒ������", Toast.LENGTH_LONG).show();
					}
				}else if(intent.getIntExtra("scanOrsearch", -1)==2){
					mEditText.setText(intent.getStringExtra("searchString"));
					mList = dm.Gethp_tmByCkid(str2, mEditText.getText().toString(),ckid);
					if (mList.isEmpty()) {
						Toast.makeText(this, "������ɨ��Ļ�Ʒ��Ϣ", Toast.LENGTH_LONG).show();
					}
				}else{
					mEditText.setText("");
					mList = dm.Gethp(str1);
				}
				myHpListBaseAdapter.setListData(mList,ckid);
			}
		}
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.popupwindow_titlelist, null);
		// popupwindowAdapter pup_adapter = new popupwindowAdapter(this,
		// ckmc_array);
		mDemoAdapter = new DemoAdapter(this);
		title_listView1 = (ExpandableListView) view
				.findViewById(R.id.popuplist1);
		title_listView1.setAdapter(mDemoAdapter);

		mPopupWindow = new PopupWindow(view, ScreenWidth * 3 / 5,
				LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ.
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// ʹ��۽�
		mPopupWindow.setFocusable(true);
		// ����������������ʧ
		mPopupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		mPopupWindow.update();
		
		judgeShowPicture = new JudgeShowPicture(this, myHpListBaseAdapter);
	}
	
	/**
	 * @return type����:all(��ȡ���л�Ʒ) today(���ճ����)  upper(��Ʒ����ɸѡ) lower(��Ʒ����ɸѡ)
	 * */
	public String getType(String str){
		if(str.equals("��治��")){
			return "lower";
		}else if(str.equals("������")){
			return "upper";
		}else if(str.equals("���ձ仯")){
			return "today";
		}else if(str.equals("��Ʒ��Ϣ")){
			return "all";
		}
		return "all";
	}
	
	/**
	 *  �� ExpandableList ����Ԫ��
	 * */
	public void addExpandableListElement(){
		for (int i = 0; i < conditionArray.length; i++) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ckmc", conditionArray[i]);
			ckmc_array.add(map1);
		}
		List<Map<String, Object>> littleList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i <= cklist.size(); i++) {
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			if (i == 0) {
				map2.put("ckmc", "���вֿ�");
				map2.put("ckid", "-1");
			} else {
				map2.put("ckmc",
						cklist.get(i - 1).get(DataBaseHelper.CKMC)
								.toString());
				map2.put("ckid",
						cklist.get(i - 1).get(DataBaseHelper.ID)
								.toString());
			}
			littleList.add(map2);
		}
		for (int i = 0; i <= ckmc_array.size(); i++) {
			childList.add(littleList);
		}
	}
	
	/**
	 * title ѡ����ػ�Ʒ
	 * */
	public void titleHpInfoLoad() {
		if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == 1) {
			if (WebserviceImport.isOpenNetwork(HpManagerListActivity.this)) {
				mEditText.setText("");
				new GetHpInfoAsyncTask().execute(getType(hptagStr),"0",String.valueOf(ckid));
				titleTextView1.setVisibility(View.VISIBLE);
				titleTextView.setText(hptagStr);
				titleTextView1.setText(ckmcStr);
			} else {
				Toast.makeText(HpManagerListActivity.this, "����δ����",
						Toast.LENGTH_SHORT).show();
			}
		} else if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == -1) {
			mEditText.setText("");
			if (hptagStr.equals("��治��")) {
				if (ckid == -1) {
					mList = dm.Gethp_xx(str1);
				} else {
					mList = dm.Gethp_xxByckid(ckid);
				}
				myHpListBaseAdapter.setListData(mList,ckid);
			} else if (hptagStr.equals("������")) {
				if (ckid == -1) {
					mList = dm.Gethp_sx(str1);
				} else {
					mList = dm.Gethp_sxByckid(ckid);
				}
				myHpListBaseAdapter.setListData(mList,ckid);
			} else if (hptagStr.equals("���ձ仯")) {
				String date = formatter.format(new Date(System
						.currentTimeMillis()));
				if (ckid == -1) {
					mList = dm.Gethp_todaychange(date);
				} else {
					mList = dm.Gethp_todaychangeByckid(ckid, date);
				}
				myHpListBaseAdapter.setListData(mList,ckid);
			} else if (hptagStr.equals("��Ʒ��Ϣ")) {
				if (ckid == -1) {
					mList = dm.Gethp(str1);
				} else {
					mList = dm.GethpByckid(ckid);
				}
				myHpListBaseAdapter.setListData(mList,ckid);
			}
			titleTextView1.setVisibility(View.VISIBLE);
			titleTextView.setText(hptagStr);
			titleTextView1.setText(ckmcStr);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.title_change:
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
				// up_downImgView.setImageResource(R.drawable.title_up);
			} else {
				mDemoAdapter.setData(ckmc_array, childList);
				mPopupWindow.showAsDropDown(title_layout, ScreenWidth / 4, -20);
				// up_downImgView.setImageResource(R.drawable.title_down);
			}
			title_listView1.setOnChildClickListener(new OnChildClickListener() {
				
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO �Զ����ɵķ������
					Map<String, Object> map = (Map<String, Object>) mDemoAdapter
							.getGroup(groupPosition);
					Map<String, Object> map2 = (Map<String, Object>) mDemoAdapter
							.getChild(groupPosition, childPosition);
					loadFlag = 0;
					mEditText.setText("");
					hptagStr = map.get("ckmc").toString();
					ckmcStr = map2.get("ckmc").toString();
					ckid = Integer.parseInt(map2.get("ckid").toString());
					titleHpInfoLoad();
					mPopupWindow.dismiss();
					return true;
				}
			});
			break;
		case R.id.scan:
			intent.setClass(this, CaptureActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.listtext:
			intent.setClass(this, SearchActivity.class);
			intent.putExtra("hint", 1);
			startActivityForResult(intent, 6);
			break;
		case R.id.del_cha:
			mEditText.setText("");
			break;
		case R.id.filter1:
			intent.setClass(this, HP_filterActivity.class);
			startActivityForResult(intent, 5);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if (resultCode == 1) {
				loadFlag = 2;
				String scan_tm = data.getStringExtra("scan_tm");
				mEditText.setText(scan_tm);
				titleTextView.setText("��Ʒ��Ϣ");
				titleTextView1.setVisibility(View.GONE);
				if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == 1) {
					if (WebserviceImport.isOpenNetwork(this)) {
//						cacheThreadpool.execute(search_tmRunnable);
						new SearchHPbyTMAsyncTask().execute(scan_tm);
					} else {
						Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
					}
				} else if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == -1) {
					mList = dm.Gethp_tmByCkid(str2, mEditText.getText().toString(),ckid);
					myHpListBaseAdapter.setListData(mList,ckid);
					if (mList.isEmpty()) {
						Toast.makeText(this, "������ɨ��Ļ�Ʒ��Ϣ", Toast.LENGTH_LONG).show();
					}
				}
			}
		}else if(requestCode == 5){
			if (resultCode == 1) {
				loadFlag = 3;
				sqlString = data.getStringExtra("sql");
				// �߼�������ckid
				hasckkc = data.getIntExtra("hasckkc", 0);
				ckid = data.getIntExtra("ckid", -1);
				String ckname = data.getStringExtra("ckname");
				if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == 1) {
					if (WebserviceImport.isOpenNetwork(this)) {
						titleTextView.setText("��Ʒ��Ϣ");
						if(ckname.equals("")){
							titleTextView1.setVisibility(View.GONE);
						}else{
							titleTextView1.setText(ckname);
						}
						mEditText.setText("");
						new GetHpInfoSearchComplexAsyncTask().execute("0",String.valueOf(ckid),sqlString,String.valueOf(hasckkc));
					} else {
						Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
					}
				} else if (mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1) == -1) {
					mEditText.setText("");
					titleTextView.setText("��Ʒ��Ϣ");
					if(ckname.equals("")){
						titleTextView1.setVisibility(View.GONE);
					}else{
						titleTextView1.setText(ckname);
					}
					mList = dm.GetHp_complex(sqlString, ckid, hasckkc);
					myHpListBaseAdapter.setListData(mList,ckid);
				}
			}
		}else if (requestCode == 6){
			if (resultCode == 1) {
				if(mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1)==1){
					mEditText.setText(data.getStringExtra("searchString"));
					loadFlag = 1;
					if(data.getIntExtra("scanOrsearch", -1)==1){//�ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
						new GetHpInfoSearchAsyncTask().execute("search","0",String.valueOf(ckid),mEditText.getText().toString());
					}else if(data.getIntExtra("scanOrsearch", -1)==2){
						new SearchHPbyTMAsyncTask().execute(mEditText.getText().toString());
					}
				}else{
					if(data.getIntExtra("scanOrsearch", -1)==1){//�ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
						mEditText.setText(data.getStringExtra("searchString"));
						if(ckid==-1){
							mList = dm.queryList(str1, data.getStringExtra("searchString"),ckid);
						}else{
							mList = dm.queryList(str2, data.getStringExtra("searchString"),ckid);
						}
						if(mList.isEmpty()){
							Toast.makeText(this, "������Ʒ������", Toast.LENGTH_LONG).show();
						}
						myHpListBaseAdapter.setListData(mList,ckid);
					}else if(data.getIntExtra("scanOrsearch", -1)==2){
						mEditText.setText(data.getStringExtra("searchString"));
						mList = dm.Gethp_tmByCkid(str2, data.getStringExtra("searchString"),ckid);
						if (mList.isEmpty()) {
							Toast.makeText(this, "������ɨ��Ļ�Ʒ��Ϣ", Toast.LENGTH_LONG)
									.show();
						}
						myHpListBaseAdapter.setListData(mList,ckid);
					}
				}
			}
		}
	}
	
	class GetCKListAsyncTask extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			cklist = WebserviceImport.GetCK(str_ck, str15, mSharedPreferences);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			addExpandableListElement();
		}
	}

	class GetHpInfoAsyncTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String jsonString = WebserviceImport_more.GetHpInfo_1_0(params[0], "10", params[1], "1", params[2]);
			return jsonString;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			stopLoad();
			try {
				JSONObject jsonObject = new JSONObject(result);
				semaphore.release();
				switch(jsonObject.getInt("Status")){
				case 1:
					parseJSON(jsonObject);
					break;
				case 2:
					parseJSON(jsonObject);
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(HpManagerListActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class GetHpInfoSearchAsyncTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String jsonString = WebserviceImport_more.GetHpInfo_search_1_0(params[0], "10", params[1], "1", params[2],params[3]);
			return jsonString;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			stopLoad();
			try {
				JSONObject jsonObject = new JSONObject(result);
				semaphore.release();
				switch(jsonObject.getInt("Status")){
				case 1:
					parseJSON(jsonObject);
					break;
				case 2:
					parseJSON(jsonObject);
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(HpManagerListActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class GetHpInfoSearchComplexAsyncTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String jsonString = WebserviceImport_more.GetHpInfo_top_search_complex_2_0( "10", params[0], "1", params[1],params[2],params[3]);
			return jsonString;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			stopLoad();
			try {
				JSONObject jsonObject = new JSONObject(result);
				semaphore.release();
				switch(jsonObject.getInt("Status")){
				case 1:
					parseJSON(jsonObject);
					break;
				case 2:
					parseJSON(jsonObject);
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(HpManagerListActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class SearchHPbyTMAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			String jsonString = WebserviceImport_more.GetHP_ByTM_2_0(params[0],ckid,
					false);
			return jsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				semaphore.release();
				switch (jsonObject.getInt("Status")) {
				case 1:
					parseJSON(jsonObject);
					break;
				case -1:
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -2:
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -3:
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(HpManagerListActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void parseJSON(JSONObject jsonObject) throws JSONException{
		JSONObject dataJSONObject = jsonObject.getJSONObject("Data");
		JSONArray dsJSONObject = dataJSONObject.getJSONArray("ds");
		int length = dsJSONObject.length();
		for(int i = 0; i<length; i++ ){
			JSONObject myJsonObject = dsJSONObject.getJSONObject(i);
			Map<String,Object> map = new HashMap<String,Object>();
			for(int j=0;j<str3.length;j++){
				map.put(str2[j], myJsonObject.get(str3[j]));
			}
			mList.add(map);
		}
		myHpListBaseAdapter.setListData(mList,ckid);
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if(WebserviceImport.isOpenNetwork(this)){
			onLoad();
			mList.clear();
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(loadFlag == 0){
				new GetHpInfoAsyncTask().execute(getType(hptagStr),"0",String.valueOf(ckid));
			}else if(loadFlag == 1){
				new GetHpInfoSearchAsyncTask().execute("search","0",String.valueOf(ckid),mEditText.getText().toString().trim());
			}else if(loadFlag == 2){
				new SearchHPbyTMAsyncTask().execute(mEditText.getText().toString());
			}else if(loadFlag == 3){
				new GetHpInfoSearchComplexAsyncTask().execute("0",String.valueOf(ckid),sqlString,String.valueOf(hasckkc));
			}
		}else{
			stopLoad();
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if(WebserviceImport.isOpenNetwork(this)){
			onLoad();
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(mList.isEmpty()){
				if(loadFlag == 0){
					new GetHpInfoAsyncTask().execute(getType(hptagStr),"0",String.valueOf(ckid));
				}else if(loadFlag == 1){
					new GetHpInfoSearchAsyncTask().execute("search","0",String.valueOf(ckid),mEditText.getText().toString().trim());
				}else if(loadFlag == 2){
					stopLoad();
				}else if(loadFlag == 3){
					new GetHpInfoSearchComplexAsyncTask().execute("0",String.valueOf(ckid),sqlString,String.valueOf(hasckkc));
				}
			}else{
				if(loadFlag == 0){
					new GetHpInfoAsyncTask().execute(getType(hptagStr),mList.get(mList.size()-1).get(DataBaseHelper.ID).toString(),String.valueOf(ckid));
				}else if(loadFlag == 1){
					new GetHpInfoSearchAsyncTask().execute("search",mList.get(mList.size()-1).get(DataBaseHelper.ID).toString(),String.valueOf(ckid),mEditText.getText().toString());
				}else if(loadFlag == 2){
					stopLoad();
				}else if(loadFlag == 3){
					new GetHpInfoSearchComplexAsyncTask().execute(mList.get(mList.size()-1).get(DataBaseHelper.ID).toString(),String.valueOf(ckid),sqlString,String.valueOf(hasckkc));
				}
			}
			
		}else{
			stopLoad();
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void onLoad() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(new DecimalFormat("00").format(c
				.get(Calendar.MONTH) + 1));
		String day = String.valueOf(new DecimalFormat("00").format(c
				.get(Calendar.DAY_OF_MONTH)));
		String hour = String.valueOf(new DecimalFormat("00").format(c
				.get(Calendar.HOUR_OF_DAY)));
		String minute = String.valueOf(new DecimalFormat("00").format(c
				.get(Calendar.MINUTE)));
		String refreshDate = year + "-" + month + "-" + day + " " + hour + ":"
				+ minute;
		mXListView.setRefreshTime(refreshDate);
	}
	
	private void stopLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HpInformationActivity.class);
		Map<String, Object> ms = (Map<String, Object>) arg0.getAdapter()
				.getItem(arg2);
		intent.putExtra("id", ms.get(DataBaseHelper.ID).toString());
		intent.putExtra("hpmc", ms.get(DataBaseHelper.HPMC).toString());
		intent.putExtra("hpbm", ms.get(DataBaseHelper.HPBM).toString());
		intent.putExtra("ggxh", ms.get(DataBaseHelper.GGXH).toString());
		startActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		s.toString().replace("'", "");
		mList.clear();
		myHpListBaseAdapter.notifyDataSetChanged();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (s.length() > 0) {
			searchDelBtn.setVisibility(View.VISIBLE);
		} else {
			searchDelBtn.setVisibility(View.GONE);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		judgeShowPicture.unBinder();
	}
	
}