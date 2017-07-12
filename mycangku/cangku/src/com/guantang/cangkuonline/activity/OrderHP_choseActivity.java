package com.guantang.cangkuonline.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.XListView.XListView;
import com.guantang.cangkuonline.XListView.XListView.IXListViewListener;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.commonadapter.CommonAdapter;
import com.guantang.cangkuonline.commonadapter.CommonAdapter2;
import com.guantang.cangkuonline.commonadapter.ViewHolder;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.dialog.DDAddNumberDialog;
import com.guantang.cangkuonline.eventbusBean.ObjectFive;
import com.guantang.cangkuonline.eventbusBean.ObjectSeven;
import com.guantang.cangkuonline.eventbusBean.ObjectSix;
import com.guantang.cangkuonline.helper.DecimalsHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.showPicService.JudgeShowPicture;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenuListView;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderHP_choseActivity extends BaseActivity implements OnClickListener,
		IXListViewListener, TextWatcher {
	private ImageButton backImageButton, scanImageButton;
	private TextView titleTextView, numbershowTxtView, comfirmBtn;
	private Button deleteBtn, searchBtn2;
	private FrameLayout jianhuokuangLayout;
	private ImageView searchDelBtn;
	private EditText mEditText1;
	private XListView mXListView1;
	private SwipeMenuListView mListView2;
	private LinearLayout mLinearLayout;
	private DataBaseOperateMethod dm_op = new DataBaseOperateMethod(this);
	private HPMyAdapter mMyAdapter;
	private List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
	private int dirc = 0;// 0 �ǲɹ�������1�����۶���
	private String ddid = "", ddh = "";
	// ��Ʒ��Ϣ�ֶ�
	private String str[] = { DataBaseHelper.ID, DataBaseHelper.HPMC,
			DataBaseHelper.HPBM, DataBaseHelper.HPTM, DataBaseHelper.GGXH,
			DataBaseHelper.CurrKC ,"CompressImageURL"};

	// ����˻�Ʒ��Ϣ�ֶ�
	private String str2[] = { "id", "HPMC", "HPBM", "HPTBM", "GGXH", "CurrKc" ,"CompressImageURL"};
	// ������ϸ���ֶ�
	private String[] str1 = { DataBaseHelper.HPID, DataBaseHelper.orderID,
			DataBaseHelper.HPMC, DataBaseHelper.HPBM, DataBaseHelper.GGXH,
			DataBaseHelper.SL, DataBaseHelper.DJ, DataBaseHelper.ZJ,
			DataBaseHelper.ddirc };
	// ����˶�����ϸ���ֶ�
	private String[] str3 = { "hpid", "orderId", "hpmc", "hpbm", "ggxh", "sl",
			"dj", "zj", "ddirc" };
	
	private Semaphore semaphore = new Semaphore(1);
	private JudgeShowPicture judgeShowPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hp_add);
		EventBus.getDefault().register(this);
		initControl();
		init();
	}

	@Override
	protected void onStart() {
		// TODO �Զ����ɵķ������
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		judgeShowPicture.unBinder();
		EventBus.getDefault().unregister(this);
	}

	public void initControl() {
		backImageButton = (ImageButton) findViewById(R.id.back);
		titleTextView = (TextView) findViewById(R.id.title);
		scanImageButton = (ImageButton) findViewById(R.id.scanImgBtn);
		mEditText1 = (EditText) findViewById(R.id.listtext1);
		mXListView1 = (XListView) findViewById(R.id.hplist1);
		jianhuokuangLayout = (FrameLayout) findViewById(R.id.add_hp);
		comfirmBtn = (TextView) findViewById(R.id.show_hp);
		mLinearLayout = (LinearLayout) findViewById(R.id.tabpager);
		numbershowTxtView = (TextView) findViewById(R.id.numbershow);
		searchDelBtn = (ImageView) findViewById(R.id.del_cha);

		searchDelBtn.setOnClickListener(this);
		backImageButton.setOnClickListener(this);
		scanImageButton.setOnClickListener(this);
		mXListView1.setPullLoadEnable(true);// ���ÿ��Լ��ظ���
		mXListView1.setPullRefreshEnable(true);// ���ÿ���ˢ��

		mXListView1.setXListViewListener(this);
		jianhuokuangLayout.setOnClickListener(this);
		comfirmBtn.setOnClickListener(this);
		mEditText1.addTextChangedListener(this);
		mEditText1.setOnClickListener(this);
		mEditText1
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

						}
						return false;
					}
				});

		Intent intent = getIntent();
		dirc = intent.getIntExtra("dirc", 0);
		ddh = intent.getStringExtra("ddh");
		ddid = intent.getStringExtra("ddid");

		// ���ر���������д�Ļ�Ʒ����
		Map<String, Object> map = dm_op.DDhp_Sum(ddid);
		String munstr = map.get("num").toString();
		if (!munstr.equals("0")) {
			numbershowTxtView.setVisibility(View.VISIBLE);
			if (Integer.parseInt(munstr) > 9) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip2);
			} else if (Integer.parseInt(munstr) > 99) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip3);
			} else {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip1);
			}
			if (Integer.parseInt(munstr) > 999) {
				numbershowTxtView.setText("999");
			} else {
				numbershowTxtView.setText(munstr);
			}
			comfirmBtn.setTextColor(Color.WHITE);
			comfirmBtn.setBackground(getResources().getDrawable(
					R.drawable.addcomplete));
			comfirmBtn.setClickable(true);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		} else {
			numbershowTxtView.setVisibility(View.GONE);
			comfirmBtn.setTextColor(R.color.ziti1);
			comfirmBtn.setBackgroundColor(getResources().getColor(
					R.color.transparent));
			comfirmBtn.setClickable(false);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		}

	}

	public void init() {
		mMyAdapter = new HPMyAdapter(this, myList, R.layout.listitem2);
		mXListView1.setAdapter(mMyAdapter);
		if (WebserviceImport.isOpenNetwork(this)) {
			new HpLoadAnyctask().execute("10", "0");
		} else {
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
		judgeShowPicture = new JudgeShowPicture(this, mMyAdapter);
	}

	public void onEventMainThread(ObjectFive obj) {
		ListIterator<Map<String, Object>> it = myList.listIterator();
		while (it.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) it.next();
			if (map.get(DataBaseHelper.ID).toString().equals(obj.getHpid())) {
				map.put("caozuoshu", obj.getNum());
				it.set(map);
				break;
			}
		}
		mMyAdapter.notifyDataSetChanged();
	}

	public void onEventMainThread(ObjectSix obj) {
		Map<String, Object> map = dm_op.DDhp_Sum(ddid);
		String munstr = map.get("num").toString();

		if (!munstr.equals("0")) {
			numbershowTxtView.setVisibility(View.VISIBLE);
			if (Integer.parseInt(munstr) > 9) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip2);
			} else if (Integer.parseInt(munstr) > 99) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip3);
			} else {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip1);
			}
			if (Integer.parseInt(munstr) > 999) {
				numbershowTxtView.setText("999");
			} else {
				numbershowTxtView.setText(munstr);
			}
			comfirmBtn.setTextColor(Color.WHITE);
			comfirmBtn.setBackground(getResources().getDrawable(
					R.drawable.addcomplete));
			comfirmBtn.setClickable(true);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		} else {
			numbershowTxtView.setVisibility(View.GONE);
			comfirmBtn.setTextColor(R.color.ziti1);
			comfirmBtn.setBackgroundResource(R.color.transparent);
			comfirmBtn.setClickable(false);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		}
	}

	public void onEventMainThread(ObjectSeven obj) {
		// map����������
		Map<String, Object> map = dm_op.DDhp_Sum(ddid);
		String munstr = map.get("num").toString();
		if (!munstr.equals("0")) {
			numbershowTxtView.setVisibility(View.VISIBLE);
			if (Integer.parseInt(munstr) > 9) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip2);
			} else if (Integer.parseInt(munstr) > 99) {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip3);
			} else {
				numbershowTxtView.setBackgroundResource(R.drawable.numtip1);
			}
			if (Integer.parseInt(munstr) > 999) {
				numbershowTxtView.setText("999");
			} else {
				numbershowTxtView.setText(munstr);
			}
			comfirmBtn.setTextColor(Color.WHITE);
			comfirmBtn.setBackground(getResources().getDrawable(
					R.drawable.addcomplete));
			comfirmBtn.setClickable(true);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		} else {
			numbershowTxtView.setVisibility(View.GONE);
			comfirmBtn.setTextColor(R.color.ziti1);
			comfirmBtn.setBackgroundResource(R.color.transparent);
			comfirmBtn.setClickable(false);// û�����ӳɹ���Ʒ�Ͳ��ܵ��
		}
		// ɾ����map2
		Map<String, Object> map2 = obj.getMap();

		for (int i = 0; i < myList.size(); i++) {
			if (myList.get(i).get(DataBaseHelper.ID)
					.equals(map2.get(DataBaseHelper.HPID).toString())) {
				myList.get(i).put("caozuoshu", "0");
				break;
			}
		}
		mMyAdapter.notifyDataSetChanged();
	}

	class HpLoadAnyctask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			String jsonString = WebserviceImport_more.GetHpInfo_search_1_0(
					"search", params[0], params[1], "1", "-1", mEditText1.getText()
							.toString().trim());
			return jsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				semaphore.release();
				switch (jsonObject.getInt("Status")) {
				case 1:
					parseJSON(jsonObject);
					break;
				case 2:
					parseJSON(jsonObject);
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -2:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -3:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -4:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			stopLoadface();
		}
	}

	public void parseJSON(JSONObject jsonObject) throws JSONException{
		JSONObject dataJSONObject = jsonObject
				.getJSONObject("Data");
		JSONArray dsJSONArray = dataJSONObject.getJSONArray("ds");
		for (int i = 0; i < dsJSONArray.length(); i++) {
			JSONObject myJSONObject = dsJSONArray.getJSONObject(i);
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < str.length; j++) {
				map.put(str[j], myJSONObject.getString(str2[j]));
			}
			List<Map<String, Object>> list2 = dm_op
					.Gt_OrderDetails(ddid,
							map.get(DataBaseHelper.ID).toString(),
							str1);
			if (!list2.isEmpty()) {
				map.put("caozuoshu",
						list2.get(0).get(DataBaseHelper.SL)
								.toString());
			} else {
				map.put("caozuoshu", "0");
			}
			myList.add(map);
		}

		mMyAdapter.setData(myList);
	}
	
	class SearchHPbyTMAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			String jsonString = WebserviceImport_more.GetHP_ByTM_1_0(params[0],
					false);
			return jsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				switch (jsonObject.getInt("Status")) {
				case 1:
					parseJSON(jsonObject);
					DDAddNumberDialog mAddNumberDialog = new DDAddNumberDialog(OrderHP_choseActivity.this, ddh, ddid,
							dirc, myList.get(0),R.style.ButtonDialogStyle);
					mAddNumberDialog.setCanceledOnTouchOutside(false);
					Window win = mAddNumberDialog.getWindow();
					win.getDecorView().setPadding(0, 0, 0, 0);
					win.setGravity(Gravity.BOTTOM);
					WindowManager.LayoutParams lp = win.getAttributes();
					lp.width = WindowManager.LayoutParams.MATCH_PARENT;
					lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
					win.setAttributes(lp);
					mAddNumberDialog.show();
					break;
				case -1:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -2:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				case -3:
					Toast.makeText(OrderHP_choseActivity.this,
							jsonObject.getString("Message"), Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					Toast.makeText(OrderHP_choseActivity.this,
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

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.scanImgBtn:
			intent.setClass(this, CaptureActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.add_hp:
			intent.setClass(this, OrderDetailActivity.class);
			intent.putExtra("ddh", ddh);
			intent.putExtra("ddid", ddid);
			intent.putExtra("dirc", dirc);
			startActivity(intent);
			break;
		case R.id.show_hp:
			finish();
			break;
		case R.id.listtext1:
			intent.setClass(this, SearchActivity.class);
//			intent.putExtra("HP_choseActivityStart", (Boolean) true);
			intent.putExtra("hint", 1);
			startActivityForResult(intent, 2);
			break;
		case R.id.del_cha:
			mEditText1.setText("");
			break;

		}
	}

	@Override
	public void onRefresh() {
		// TODO �Զ����ɵķ������
		onLoadTime();
		if (WebserviceImport.isOpenNetwork(this)) {
			myList.clear();
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new HpLoadAnyctask().execute("10", "0");
		} else {
			stopLoadface();
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLoadMore() {
		// TODO �Զ����ɵķ������
		onLoadTime();
		if (WebserviceImport.isOpenNetwork(this)) {
			if (myList.isEmpty()) {
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new HpLoadAnyctask().execute("10", "0");
			} else {
				new HpLoadAnyctask().execute("10", myList
						.get(myList.size() - 1).get(DataBaseHelper.ID)
						.toString());
			}
		} else {
			stopLoadface();
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
		}
	}

	public void onLoadTime() {
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
		mXListView1.setRefreshTime(refreshDate);
	}

	/**
	 * ֹͣ������ض���
	 * */
	public void stopLoadface() {
		mXListView1.stopRefresh();
		mXListView1.stopLoadMore();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			if (resultCode == 1) {
				if (data.getIntExtra("scanOrsearch", -1) == 1) {// �ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
					if (WebserviceImport.isOpenNetwork(this)) {
						mEditText1.setText(data.getStringExtra("searchString"));
						new HpLoadAnyctask().execute("10", "0");
					} else {
						Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT)
								.show();
					}
				} else if (data.getIntExtra("scanOrsearch", -1) == 2) {// �ж��Ƿ������ҳ����ת�����ģ�1�Ǵ�����ҳ����������ת�����ģ�2�Ǵ�����ҳ���ɨ����ת�����ģ�-1��ֱ�Ӵ��������
					if (WebserviceImport.isOpenNetwork(this)) {
						mEditText1.setText(data.getStringExtra("searchString"));
						new SearchHPbyTMAsyncTask().execute(mEditText1
								.getText().toString());
					} else {
						Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		} else if (requestCode == 1) {
			if (resultCode == 1) {
				if (WebserviceImport.isOpenNetwork(this)) {
					mEditText1.setText(data.getStringExtra("scan_tm"));
					new SearchHPbyTMAsyncTask().execute(mEditText1.getText()
							.toString());
				} else {
					Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO �Զ����ɵķ������
		s.toString().replace("'", "");
		myList.clear();
		mMyAdapter.notifyDataSetChanged();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO �Զ����ɵķ������
		if (s.length() > 0) {
			searchDelBtn.setVisibility(View.VISIBLE);
		} else {
			searchDelBtn.setVisibility(View.GONE);
		}
	}

	class HPMyAdapter extends CommonAdapter2<Map<String, Object>> {
		

		public HPMyAdapter(Context mContext, List<Map<String, Object>> mList,
				int LayoutId) {
			super(mContext, mList, LayoutId);
		}

		@Override
		public void convert(ViewHolder holder, final Map<String, Object> item) {
			// TODO �Զ����ɵķ������
			TextView itemnameTxtView = holder.getView(R.id.itemname);
			TextView itemcodeTxtView = holder.getView(R.id.itemcode);
			TextView itemggTxtView = holder.getView(R.id.itemgg);
			TextView itemnumTxtView = holder.getView(R.id.itemnum);
			TextView caozuoNameTxtView = holder.getView(R.id.caozuoNameText);
			TextView caozuoshuTxtView = holder.getView(R.id.caozuoshu);
			ImageButton ImgBtn = holder.getView(R.id.showBtn);
			LinearLayout caozuoshuLayout = holder.getView(R.id.caozuoshulayout);
			ImageView picImageView = holder.getView(R.id.picImgView);

			Object hpmcObject = item.get(DataBaseHelper.HPMC);
			itemnameTxtView.setText(hpmcObject == null || hpmcObject.equals("null") ? "" : hpmcObject
					.toString());

			Object hpbmObject = item.get(DataBaseHelper.HPBM);
			itemcodeTxtView.setText(hpbmObject == null || hpbmObject.equals("null")? "" : hpbmObject
					.toString());

			Object ggxhObject = item.get(DataBaseHelper.GGXH);
			itemggTxtView.setText(ggxhObject == null || ggxhObject.equals("null")? "" : ggxhObject
					.toString());

			Object hpzkcObject = item.get(DataBaseHelper.CurrKC);
			itemnumTxtView.setText(hpzkcObject == null || hpzkcObject.equals("null")? "" : DecimalsHelper
					.Transfloat(Double.parseDouble(hpzkcObject.toString()),
							MyApplication.getInstance().getNumPoint()));
			
			if(IS_LOGIN==1 && showPic){
				picImageView.setVisibility(View.VISIBLE);
				Object picurl =item.get("CompressImageURL");
				Glide.with(context).load(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+picurl.toString()).centerCrop().placeholder(R.drawable.pic_defalut)
				.crossFade().into(picImageView);
		        
			}else{
				picImageView.setVisibility(View.GONE);
			}
			
			

			if (dirc == 0) {
				caozuoNameTxtView.setText("�ɹ�����:");
			} else if (dirc == 1) {
				caozuoNameTxtView.setText("��������");
			}

			String numstr = item.get("caozuoshu").toString();

			if (numstr.equals("0")) {
				ImgBtn.setBackgroundResource(R.drawable.hpadd);
				caozuoshuLayout.setVisibility(View.INVISIBLE);
			} else {
				caozuoshuLayout.setVisibility(View.VISIBLE);
				ImgBtn.setBackgroundResource(R.drawable.hpmodify);
			}
			caozuoshuTxtView.setText(numstr);

			holder.getConvertView().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					Intent intent = new Intent(context,
							DDHpListDetailsWriteActivity.class);
					intent.putExtra("hpid", item.get(DataBaseHelper.ID)
							.toString());
					intent.putExtra("ddh", ddh);
					intent.putExtra("ddid", ddid);
					intent.putExtra("dirc", dirc);
					context.startActivity(intent);
				}
			});

			ImgBtn.setOnClickListener(new ListViewButtonOnClikListener(item));

		}

		class ListViewButtonOnClikListener implements OnClickListener {

			private Map<String, Object> item;

			public ListViewButtonOnClikListener(Map<String, Object> item) {
				this.item = item;
			}

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������

				DDAddNumberDialog mAddNumberDialog = new DDAddNumberDialog(
						context, ddh, ddid, dirc, item,
						R.style.ButtonDialogStyle);
				mAddNumberDialog.setCanceledOnTouchOutside(false);
				Window win = mAddNumberDialog.getWindow();
				win.getDecorView().setPadding(0, 0, 0, 0);
				win.setGravity(Gravity.BOTTOM);
				WindowManager.LayoutParams lp = win.getAttributes();
				lp.width = WindowManager.LayoutParams.MATCH_PARENT;
				lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				win.setAttributes(lp);
				mAddNumberDialog.show();

			}
		}

	}

}