package com.guantang.cangkuonline.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.dialog.UserNumDialog;
import com.guantang.cangkuonline.dialog.UserNumDialog.onButtonClick;
import com.guantang.cangkuonline.dialog.UserNumDialog.onItemClick;
import com.guantang.cangkuonline.eventbusBean.ObjectOne;
import com.guantang.cangkuonline.helper.CodeHelper;
import com.guantang.cangkuonline.helper.EditHelper;
import com.guantang.cangkuonline.helper.TimeCount;
import com.guantang.cangkuonline.helper.TimeCount.finishImport;
import com.guantang.cangkuonline.helper.TimeCount.tickImport;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import de.greenrobot.event.EventBus;

public class AddRegActivity extends BaseActivity implements OnClickListener,
		TextWatcher {
	private LinearLayout lin_layout1;
	private EditText dw, lxr, phone, code,numEdit;
	private TextView serTextView,bt_code;
	private Button ok;
	private ImageView back;
	private ImageView img;
	private RadioGroup mRadiogroup;
	private RadioButton radio4;
	private ProgressDialog pro_dialog;
	private int flag, number=0;
	private AlertDialog.Builder builder;
	private AlertDialog Dialog;
	private SharedPreferences mSharePreferences;
	private String[] url_array;
	private String[] ser_array;
	/**
	 * ������������±꣬����±����Զ�Ӧ��������ַ�±�
	 */
	private int url_subid = -1;
	/**
	 * �Ƿ�ѡ�������
	 */
	private Boolean clickflag = false;
	private TimeCount timeCount;
	private String iscode, icode;

	private String selectServerUrl = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addreg);
		initControl();
		timeCount = new TimeCount(60000, 1000);
		mSharePreferences = MyApplication.getInstance().getSharedPreferences();
		url_array = new String[] {
				mSharePreferences.getString(ShareprefenceBean.SERVICE_IDN_URL1,
						"www3.gtcangku.com"),
				mSharePreferences.getString(ShareprefenceBean.SERVICE_IDN_URL2,
						"www.gtcangku.com"),
				mSharePreferences.getString(ShareprefenceBean.SERVICE_IDN_URL3,
						"www2.gtcangku.com"),
				mSharePreferences.getString(ShareprefenceBean.IDN_ALONE_URL,
						"gd.gtcangku.com") };
		ser_array = new String[] {mSharePreferences.getString(ShareprefenceBean.SERVICE_NAME1,
				"�Ϻ�������"),
		mSharePreferences.getString(ShareprefenceBean.SERVICE_NAME2,
				"�㶫������"),
		mSharePreferences.getString(ShareprefenceBean.SERVICE_NAME3,
				"����������")};
	}

	private void initControl() {
		dw = (EditText) this.findViewById(R.id.dw);
		lxr = (EditText) this.findViewById(R.id.lxr);
		phone = (EditText) this.findViewById(R.id.phone);
		code = (EditText) this.findViewById(R.id.code);
//		usernum = (TextView) this.findViewById(R.id.usernum);
		back = (ImageView) this.findViewById(R.id.back);
		img = (ImageView) this.findViewById(R.id.img_state);
		ok = (Button) this.findViewById(R.id.ok);
		bt_code = (TextView) this.findViewById(R.id.bt_code);
		serTextView = (TextView) this.findViewById(R.id.server_txtleft);
		lin_layout1 = (LinearLayout) this.findViewById(R.id.lin_layout1);
		mRadiogroup = (RadioGroup) this.findViewById(R.id.radiogroup);
		radio4 = (RadioButton) findViewById(R.id.radio4);
		numEdit = (EditText) findViewById(R.id.numEdit);

		back.setOnClickListener(this);
		ok.setOnClickListener(this);
		lin_layout1.setOnClickListener(this);
		bt_code.setOnClickListener(this);
		img.setVisibility(View.INVISIBLE);
		code.addTextChangedListener(this);
		
		mRadiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO �Զ����ɵķ������
				switch(checkedId){
				case R.id.radio1:
					radio4.setVisibility(View.VISIBLE);
					numEdit.setVisibility(View.GONE);
					numEdit.setFocusable(false);
					number = 1;
					break;
				case R.id.radio2:
					radio4.setVisibility(View.VISIBLE);
					numEdit.setVisibility(View.GONE);
					numEdit.setFocusable(false);
					number = 3;
					break;
				case R.id.radio3:
					radio4.setVisibility(View.VISIBLE);
					numEdit.setVisibility(View.GONE);
					numEdit.setFocusable(false);
					number = 10;
					break;
				case R.id.radio4:
					radio4.setVisibility(View.GONE);
					numEdit.setVisibility(View.VISIBLE);
					numEdit.setFocusable(true);
					numEdit.setFocusableInTouchMode(true);
					numEdit.requestFocus();
					numEdit.requestFocusFromTouch();
					break;
				}
			}
		});
		
		numEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO �Զ����ɵķ������
				if(!s.toString().isEmpty()){
					number = Integer.parseInt(s.toString().trim());
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.bt_code:
			if(url_subid>-1){//��ʾ�Ѿ�ѡ���˷�����
				if (!phone.getText().toString().equals("")) {
					if(WebserviceImport.isOpenNetwork(this)){
						bt_code.setClickable(false);
						bt_code.setOnClickListener(AddRegActivity.this);
						bt_code.setText("���ڻ�ȡ");
//						bt_code.setTextColor(getResources().getColor(R.color.white));
//						bt_code.setBackgroundColor(getResources()
//								.getColor(R.color.blue));
						new Thread(downloadRun2).start();
					}else{
						Toast.makeText(AddRegActivity.this, "����δ����",Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(AddRegActivity.this, "�������ֻ���",Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(AddRegActivity.this, "��ѡ�������",Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.lin_layout1:
			builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			View view = inflater.inflate(R.layout.popupwindow_list, null);
			ListView myListView = (ListView) view.findViewById(R.id.popuplist);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.popupwindow_list_textview, ser_array);
			myListView.setAdapter(adapter);
			myListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO �Զ����ɵķ������
					String str = (String) arg0.getAdapter().getItem(arg2)
							.toString();
					serTextView.setText(str);
					selectServerUrl = EditHelper.CheckHttp(url_array[arg2])
							+ url_array[arg2] + "/";
					url_subid = arg2;
					clickflag = true;
					Dialog.dismiss();
				}
			});
			builder.setView(view);
			Dialog = builder.show();
			break;

		case R.id.ok:
			if (!code.getText().toString().equals("")
					&& code.getText().toString().equals(icode)) {
				if (clickflag == true) {
					String str = "";
					if (dw.getText().toString().equals("")) {
						str = str + "��λ����";
					}
					if (lxr.getText().toString().equals("")) {
						if (str.equals("")) {
							str = str + "��ϵ��";
						} else {
							str = str + "����ϵ��";
						}
					}
					if (phone.getText().toString().equals("")) {
						if (str.equals("")) {
							str = str + "�ֻ���";
						} else {
							str = str + "���ֻ���";
						}
					}
					if (str.equals("")) {
						if(number<=0){
							Toast.makeText(this, "��ѡ���û���", Toast.LENGTH_SHORT).show();
						}else{
							if (WebserviceImport.isOpenNetwork(this)) {
								AlertDialog.Builder builder = new AlertDialog.Builder(this);
								builder.setTitle("ȷ��ע����Ϣ!");
								builder.setMessage("��λ����:"
										+ dw.getText().toString().trim() + "\n" + "��ϵ��:"
										+lxr.getText().toString().trim()  + "\n" + "��ϵ�绰:"
										+phone.getText().toString().trim()  + "\n"
										+ "�û���:" + number);
								builder.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method stub
												pro_dialog = ProgressDialog.show(
														AddRegActivity.this, null,
														"����ע��");
												if (WebserviceImport
														.isOpenNetwork(AddRegActivity.this)) {
													new Thread(downloadRun).start();
												} else {
													Toast.makeText(
															AddRegActivity.this,
															"����δ����",
															Toast.LENGTH_SHORT)
															.show();
												}
											}

										});
								builder.setNegativeButton("ȡ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method stub
												dialog.dismiss();
											}
											
										});
								builder.create().show();
							} else {
								Toast.makeText(this, "����δ���ӣ�����������",
										Toast.LENGTH_SHORT).show();
							}
						}

					} else {
						Toast.makeText(this, str + "����Ϊ��", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					Toast.makeText(this, "��ѡ�������", Toast.LENGTH_SHORT).show();
				}
			} else if (code.getText().toString().equals("")) {
				Toast.makeText(this, "��������֤��", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "��֤�����", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			pro_dialog.dismiss();
			switch (msg.what) {
			case -201:
				Toast.makeText(AddRegActivity.this, "��λ�����Ѵ���",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				EventBus.getDefault().post(
						new ObjectOne(dw.getText().toString().trim() +"\t"+serTextView.getText().toString().trim()+"\t"
						+selectServerUrl));
				AlertDialog.Builder builder = new AlertDialog.Builder(
						AddRegActivity.this);
				builder.setTitle("ע��ɹ���");
				builder.setMessage("С��ʾ������ҳ�˿���ͨ��excel���������Ʒ��Ϣ��������λ��Ա����ϢŶ��\n��ҳ�˵�ַ��\n"
				+EditHelper.CheckHttp(url_array[url_subid])+ url_array[url_subid]+"/");
				builder.setPositiveButton("֪����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
								dialog.dismiss();
								finish();
							}
						});
				builder.create().show();
				break;
			default:
				Toast.makeText(AddRegActivity.this, "ע��ʧ��,������",Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	Runnable downloadRun = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();

			flag = WebserviceImport.AddReg_1_0(dw.getText().toString().trim() , number, number,
					lxr.getText().toString().trim(),"Android",phone.getText().toString().trim(),selectServerUrl,
					mSharePreferences);

			msg.what = flag;
			msg.setTarget(mHandler);
			mHandler.sendMessage(msg);
		}

	};
	Runnable downloadRun2 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			if (null == icode || icode.equals("")) {
				icode = CodeHelper.createCode();
			}
			iscode = WebserviceImport_more.sendCode(phone.getText().toString().trim(),
					icode, 1, mSharePreferences);
			if (null != iscode && iscode.equals("1")) {
				msg.what = 1;
			} else {
				msg.what = 1;
			}
			msg.setTarget(mHandler2);
			mHandler2.sendMessage(msg);
		}

	};
	private Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -1:
				bt_code.setClickable(true);
				bt_code.setOnClickListener(AddRegActivity.this);
				bt_code.setText("��ȡ��֤��");
				bt_code.setTextColor(getResources().getColor(R.color.white));
				bt_code.setBackground(getResources().getDrawable(R.drawable.yellow_big_btn));
				break;
			case 1:
				timeCount.setTick(new tickImport() {

					@Override
					public void onTick(long millisInFuture) {
						// TODO Auto-generated method stub
						bt_code.setClickable(false);
						bt_code.setText(millisInFuture / 1000 + "��");
						bt_code.setTextColor(getResources().getColor(
								R.color.white));
						bt_code.setBackground(getResources().getDrawable(R.drawable.grey_big_btn));
					}

				});
				timeCount.setFinish(new finishImport() {
				
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						bt_code.setClickable(true);
						bt_code.setOnClickListener(AddRegActivity.this);
						bt_code.setText("��ȡ��֤��");
						bt_code.setTextColor(getResources().getColor(
								R.color.white));
						bt_code.setBackground(getResources().getDrawable(R.drawable.yellow_big_btn));
					}
				});
				timeCount.start();
				break;
			}
		}
	};

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (s.toString().equals("")) {
			img.setVisibility(View.INVISIBLE);
		} else if (s.toString().equals(icode)) {
			img.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.mini_check_selected);
		} else {
			img.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.mini_fault);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}
}