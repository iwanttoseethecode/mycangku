package com.guantang.cangkuonline.activity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseImport;
import com.guantang.cangkuonline.database.DataBaseManager;
import com.guantang.cangkuonline.database.DataBaseMethod;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.dialog.CommonDialog;
import com.guantang.cangkuonline.dialog.CommonDialog.DialogContentListener;
import com.guantang.cangkuonline.eventbusBean.EventObject8;
import com.guantang.cangkuonline.fragment.Analytic_StatisticsFragment;
import com.guantang.cangkuonline.fragment.CangKuFirstFragment;
import com.guantang.cangkuonline.fragment.ConsumerFragment;
import com.guantang.cangkuonline.fragment.NewSettingFragment;
import com.guantang.cangkuonline.fragment.New_Analytic_StatisticsFragment;
import com.guantang.cangkuonline.fragment.OrderFragment;
import com.guantang.cangkuonline.helper.RightsHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.webservice.WebserviceHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;

import de.greenrobot.event.EventBus;

public class FinalMainActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout homepagerLayout,orderPageLayout,consumerPageLayout,analytic_statisticsLayout,
			settingLayout;
	private ImageView imgView1, imgView2, imgView3,imgView4,imgView5;
	private TextView textView1, textView2, textView3,textView4,textView5;
	private FrameLayout contentpagerlayout;
	private SharedPreferences mSharedPreferences;
	private LayoutInflater inflater;
	private int ScreenWidth = 0;
	private ProgressDialog pro_dialog;
	private CommonDialog mpDialog;
	private boolean hpxinxiflag = false,ckkcxinxiflag=false, hpleixinflag = true,
			dwxinxiflag = true, canshuxinxiflag = true,cangkuxinxiflag = true;
	/**
	 * tabNumber������ǰ����tab״̬
	 * */
	private int tabNumber = 1;
	private List<Map<String, Object>> ls;
	private Thread thread;
	private DataBaseOperateMethod dm_op = new DataBaseOperateMethod(this);
	private DataBaseMethod dm = new DataBaseMethod(this);
	private SimpleDateFormat formatter;
	private int hp_num = 0, lb_num = 0, dw_num = 0, conf_num = 0, ck_num = 0,
			ckkc_num = 0;
	private String[] str = { DataBaseHelper.ID, DataBaseHelper.HPMC,
			DataBaseHelper.HPBM, DataBaseHelper.HPTM, DataBaseHelper.GGXH,
			DataBaseHelper.CurrKC, DataBaseHelper.JLDW, DataBaseHelper.HPSX,
			DataBaseHelper.HPXX, DataBaseHelper.SCCS, DataBaseHelper.BZ,
			DataBaseHelper.RKCKJ, DataBaseHelper.CKCKJ, DataBaseHelper.CKCKJ2,
			DataBaseHelper.JLDW2, DataBaseHelper.BigNum, DataBaseHelper.RES1,
			DataBaseHelper.RES2, DataBaseHelper.RES3, DataBaseHelper.RES4,
			DataBaseHelper.RES5, DataBaseHelper.RES6, DataBaseHelper.LBS,
			DataBaseHelper.KCJE, DataBaseHelper.LBID, DataBaseHelper.LBIndex,
			DataBaseHelper.ImagePath,DataBaseHelper.YXQ };
	private String[] str1 = { "id", "HPMC", "HPBM", "HPTBM", "GGXH", "CurrKc",
			"JLDW", "HPSX", "HPXX", "SCCS", "BZ", "RKCKJ", "CKCKJ", "CKCKJ2",
			"JLDW2", "BigNum", "res1", "res2", "res3", "res4", "res5", "res6",
			"LBS", "kcje", "LBID", "LBIndex", "ImageUrl","yxq" };
	private String[] str2 = { DataBaseHelper.ID, DataBaseHelper.Name,
			DataBaseHelper.Lev, DataBaseHelper.PID, DataBaseHelper.Ord,
			DataBaseHelper.Sindex };
	private String[] str3 = { "ID", "name", "lev", "PID", "ord", "sindex" };
	private String[] str4 = { DataBaseHelper.ID, DataBaseHelper.DWName,
			DataBaseHelper.ADDR, DataBaseHelper.FAX, DataBaseHelper.YB,
			DataBaseHelper.Net, DataBaseHelper.LXR, DataBaseHelper.TEL,
			DataBaseHelper.Email, DataBaseHelper.BZ, DataBaseHelper.DWtype };
	private String[] str5 = { "id", "dwName", "addr", "fax", "yb", "www",
			"lxr", "tel", "email", "bz", "dwType" };
	private String[] str6 = { DataBaseHelper.GID, DataBaseHelper.ItemID,
			DataBaseHelper.ItemV, DataBaseHelper.Ord };
	private String[] str7 = { "GID", "ItemID", "ItemV", "ord" };
	private String[] str10 = { DataBaseHelper.GID, DataBaseHelper.ItemV };
	private String[] str11 = { "dirc", "name" };
	private String[] str12 = { DataBaseHelper.ID, DataBaseHelper.HPID,
			DataBaseHelper.CKID, DataBaseHelper.KCSL };
	private String[] str13 = { "id", "hpid", "ckid", "kcsl" };
	private String[] str14 = { DataBaseHelper.ID, DataBaseHelper.CKMC,
			DataBaseHelper.FZR, DataBaseHelper.TEL, DataBaseHelper.ADDR,
			DataBaseHelper.INACT, DataBaseHelper.OUTACT, DataBaseHelper.BZ };
	private String[] str15 = { "ID", "CKMC", "FZR", "TEL", "ADDR", "inact",
			"outact", "BZ" };
	private String[] str16 = { "ID", "name", "dwlevel", "PID", "dwOrder",
			"dwIndex" };
	private String[] str17 = { DataBaseHelper.ID, DataBaseHelper.Name,
			DataBaseHelper.depLevel, DataBaseHelper.PID,
			DataBaseHelper.depOrder, DataBaseHelper.depindex };
	
	private boolean stopflag = false; // true��ʾֹͣ���أ�false��ʾ��������
	private LinkedList<Runnable> loadRunnableList = new LinkedList<Runnable>();
	private Handler runnableHandler;
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
	/**
	 * ����runnableHandler��ʼ�����
	 * */
	private volatile Semaphore runnableHandlerSemaphore = new Semaphore(1);
	/**
	 * �����߳�һ��һ��ִ��
	 * */
	private volatile Semaphore mSemaphore = new Semaphore(1);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ȥ���»��ƽ���
		// getWindow().setSoftInputMode(
		// WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.activity_finalmain);
		mSharedPreferences = this.getSharedPreferences(
				ShareprefenceBean.SHAREPREFENCE_NAME, Context.MODE_PRIVATE);
		EventBus.getDefault().register(this);
		inflater = LayoutInflater.from(this);

		initView();
		init();
		Intent intent = getIntent();
		String serConfLastModifyTime = intent
				.getStringExtra("serConfLastModifyTime");

		if (intent.getBooleanExtra("isSYNC", false)) {// �Ƿ�ͬ��
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO �Զ����ɵķ������
					onMyThread(false);
				}
			}, 400);
			mSharedPreferences.edit().putString(ShareprefenceBean.SerConfLastModifyTime,serConfLastModifyTime).commit();
		} else {
			if (WebserviceImport.isOpenNetwork(this)) {
				if (!serConfLastModifyTime.equals("")&& !serConfLastModifyTime.equals(mSharedPreferences.getString(ShareprefenceBean.SerConfLastModifyTime,""))) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO �Զ����ɵķ������
							try {
								runnableHandlerSemaphore.acquire();
							} catch (InterruptedException e) {
								// TODO �Զ����ɵ� catch ��
								e.printStackTrace();
							}
							new Thread(handleRunnable).start();
							mayLoadData();
							Toast.makeText(FinalMainActivity.this, "����Ϊ��ͬ�����޸ĵĲ���", Toast.LENGTH_LONG).show();
						}
					}, 400);
					mSharedPreferences.edit().putString(ShareprefenceBean.SerConfLastModifyTime,serConfLastModifyTime).commit();
				}
			} else {
				if (!serConfLastModifyTime.equals("")&& !serConfLastModifyTime.equals(mSharedPreferences.getString(ShareprefenceBean.SerConfLastModifyTime,""))) {
					Toast.makeText(this, "ϵͳ�������޸ģ�ע�⼰ʱͬ������", Toast.LENGTH_LONG)
							.show();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void initView() {
		imgView1 = (ImageView) findViewById(R.id.img1);
		imgView2 = (ImageView) findViewById(R.id.img2);
		imgView3 = (ImageView) findViewById(R.id.img3);
		imgView4 = (ImageView) findViewById(R.id.img4);
		imgView5 = (ImageView) findViewById(R.id.img5);
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);
		textView3 = (TextView) findViewById(R.id.text3);
		textView4 = (TextView) findViewById(R.id.text4);
		textView5 = (TextView) findViewById(R.id.text5);
		homepagerLayout = (LinearLayout) findViewById(R.id.HomePage);
		orderPageLayout = (LinearLayout) findViewById(R.id.orderPage);
		consumerPageLayout = (LinearLayout) findViewById(R.id.consumerPage);
		analytic_statisticsLayout = (LinearLayout) findViewById(R.id.analytic_statistics);
		settingLayout = (LinearLayout) findViewById(R.id.setting);
		contentpagerlayout = (FrameLayout) findViewById(R.id.contentpager);
		homepagerLayout.setOnClickListener(this);
		orderPageLayout.setOnClickListener(this);
		consumerPageLayout.setOnClickListener(this);
		analytic_statisticsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public void init() {
		imgView1.setImageDrawable(getResources().getDrawable(
				R.drawable.storage_orange_btn));
		textView1.setTextColor(getResources().getColor(R.color.theme_orange));
		imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_grey_btn));
		textView2.setTextColor(getResources().getColor(R.color.ziti666666));
		imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_grey_btn));
		textView3.setTextColor(getResources().getColor(R.color.ziti666666));
		imgView4.setImageDrawable(getResources().getDrawable(R.drawable.pie_chart_grey_btn));
		textView4.setTextColor(getResources().getColor(R.color.ziti666666));
		imgView5.setImageDrawable(getResources().getDrawable(R.drawable.setting_grey_btn));
		textView5.setTextColor(getResources().getColor(R.color.ziti666666));
		dongtaiFirst();
		tabNumber = 1;
	}
	
	public void onEventMainThread(EventObject8 eo8) {
		onMyThread(true);
	}
	
	/**
	 * ͬ�����¶Ի���
	 * @param option ��ѡͬ�����Ƿ�ͬ����true Ҫͬ����false ��ͬ����
	 * 
	 * */
	public void onMyThread(final boolean option) {
		
		CommonDialog myDialog = new CommonDialog(this, R.layout.update_info, R.style.yuanjiao_dialog);
		myDialog.setCancelable(true);
		myDialog.setDialogContentListener(new DialogContentListener() {

			@Override
			public void contentExecute(View parent, final Dialog dialog,Object... obj) {
				// TODO �Զ����ɵķ������
				final CheckBox hpxinxiCheckBox = (CheckBox) parent
						.findViewById(R.id.ck_hp);
				final CheckBox ckkcxinxiCheckBox = (CheckBox) parent.findViewById(R.id.ck_ckkc);
				final CheckBox hpleixinCheckBox = (CheckBox) parent
						.findViewById(R.id.ck_lb);
				final CheckBox dwxinxiCheckBox = (CheckBox) parent
						.findViewById(R.id.ck_dw);
				final CheckBox canshuxinxiCheckBox = (CheckBox) parent
						.findViewById(R.id.ck_type);
				final CheckBox cangkuxinxiCheckBox = (CheckBox) parent
						.findViewById(R.id.ck_ck);
				
				TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
				confirmTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						if (mSharedPreferences.getInt(
								ShareprefenceBean.IS_LOGIN, -1) == 1) {
							if (WebserviceImport
									.isOpenNetwork(FinalMainActivity.this)) {
								hpxinxiflag = hpxinxiCheckBox.isChecked();
								ckkcxinxiflag = ckkcxinxiCheckBox.isChecked();
								hpleixinflag = hpleixinCheckBox.isChecked();
								dwxinxiflag = dwxinxiCheckBox.isChecked();
								canshuxinxiflag = canshuxinxiCheckBox
										.isChecked();
								cangkuxinxiflag = cangkuxinxiCheckBox
										.isChecked();
								try {
									runnableHandlerSemaphore.acquire();
								} catch (InterruptedException e) {
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								}
								new Thread(handleRunnable).start();
								mayLoadData();
							} else {
								Toast.makeText(FinalMainActivity.this,
										"����δ���ӣ���������״��", Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(FinalMainActivity.this, "���¼�˺�",
									Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
					}
				});
				
				hpxinxiCheckBox.setChecked(option);
				ckkcxinxiCheckBox.setChecked(option);
			}
		},new Object[]{});
		myDialog.show();
		
//		View view = inflater.inflate(R.layout.update_info, null);
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		// builder.setTitle("ͬ������");
//		// builder.setMessage("���ݿ�Ϊ��,Ϊ��֤����׼ȷ,��ӷ����ͬ�����ݣ�");
//		builder.setView(view);
//		builder.setPositiveButton("ͬ������",
//				new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO �Զ����ɵķ������
//						dialog.dismiss();
//						if (mSharedPreferences.getInt(
//								ShareprefenceBean.IS_LOGIN, -1) == 1) {
//							if (WebserviceImport
//									.isOpenNetwork(FinalMainActivity.this)) {
//								hpxinxiflag = hpxinxiCheckBox.isChecked();
//								ckkcxinxiflag = ckkcxinxiCheckBox.isChecked();
//								hpleixinflag = hpleixinCheckBox.isChecked();
//								dwxinxiflag = dwxinxiCheckBox.isChecked();
//								canshuxinxiflag = canshuxinxiCheckBox
//										.isChecked();
//								cangkuxinxiflag = cangkuxinxiCheckBox
//										.isChecked();
//								try {
//									runnableHandlerSemaphore.acquire();
//								} catch (InterruptedException e) {
//									// TODO �Զ����ɵ� catch ��
//									e.printStackTrace();
//								}
//								new Thread(handleRunnable).start();
//								mayLoadData();
//							} else {
//								Toast.makeText(FinalMainActivity.this,
//										"����δ���ӣ���������״��", Toast.LENGTH_SHORT)
//										.show();
//							}
//						} else {
//							Toast.makeText(FinalMainActivity.this, "���¼�˺�",
//									Toast.LENGTH_SHORT).show();
//						}
//
//					}
//				});
//		// builder.setNegativeButton("�Ժ�ͬ��", new
//		// DialogInterface.OnClickListener() {
//		//
//		// @Override
//		// public void onClick(DialogInterface dialog, int which) {
//		// // TODO �Զ����ɵķ������
//		// dialog.dismiss();
//		//
//		// }
//		// });
//		AlertDialog syncDialog = builder.create();
//		syncDialog.setCancelable(true);
//		syncDialog.show();
	}

	/**
	 * �ֿ�Ķ�̬����
	 **/
	public void dongtaiFirst() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
//		HomePagerFragment homePagerFragment = new HomePagerFragment();
		CangKuFirstFragment cangkuFirstFragment = new CangKuFirstFragment();
		fragmentTransaction.replace(R.id.contentpager, cangkuFirstFragment);
		fragmentTransaction.commit();
	}

	public void dongtaiTwo(){
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		OrderFragment orderFragment = new OrderFragment();
		fragmentTransaction.replace(R.id.contentpager, orderFragment);
		fragmentTransaction.commit();
	}
	
	/**
	 * �ֿ�Ķ�̬����
	 **/
	public void dongtaiThree(){
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		ConsumerFragment consumerFragment = new ConsumerFragment();
		fragmentTransaction.replace(R.id.contentpager, consumerFragment);
		fragmentTransaction.commit();
	}
	
	/**
	 * ����ͳ�ƵĶ�̬����
	 */
	public void dongtaiFour() {
		// LinearLayout contentTwo = (LinearLayout) inflater.inflate(
		// R.layout.contenttwo, null);
		// contentpagerlayout.removeAllViews();
		// contentpagerlayout.addView(contentTwo);
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
//		Analytic_StatisticsFragment analytic_StatisticsFragment = new Analytic_StatisticsFragment();
		New_Analytic_StatisticsFragment new_Analytic_StatisticsFragment = new New_Analytic_StatisticsFragment();
		fragmentTransaction.replace(R.id.contentpager,
				new_Analytic_StatisticsFragment);
		fragmentTransaction.commit();
		
	}

	/**
	 * ���õĶ�̬����
	 */
	public void dongtaiFive() {
//		String Imei = ((TelephonyManager) this
//				.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		NewSettingFragment settingFragment = new NewSettingFragment();
		fragmentTransaction.replace(R.id.contentpager, settingFragment);
		fragmentTransaction.commit();

	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.HomePage:
			if (tabNumber != 1) {
				imgView1.setImageDrawable(getResources().getDrawable(
						R.drawable.storage_orange_btn));
				textView1.setTextColor(getResources().getColor(R.color.theme_orange));
				imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_grey_btn));
				textView2.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_grey_btn));
				textView3.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView4.setImageDrawable(getResources().getDrawable(R.drawable.pie_chart_grey_btn));
				textView4.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView5.setImageDrawable(getResources().getDrawable(R.drawable.setting_grey_btn));
				textView5.setTextColor(getResources().getColor(R.color.ziti666666));
				dongtaiFirst();
				tabNumber = 1;
			}
			break;
		case R.id.orderPage:
			if(tabNumber !=2){
				imgView1.setImageDrawable(getResources().getDrawable(
						R.drawable.storage_grey_btn));
				textView1.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_orange_btn));
				textView2.setTextColor(getResources().getColor(R.color.theme_orange));
				imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_grey_btn));
				textView3.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView4.setImageDrawable(getResources().getDrawable(R.drawable.pie_chart_grey_btn));
				textView4.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView5.setImageDrawable(getResources().getDrawable(R.drawable.setting_grey_btn));
				textView5.setTextColor(getResources().getColor(R.color.ziti666666));
				dongtaiTwo();
				tabNumber = 2;
			}
			break;
		case R.id.consumerPage:
			if(tabNumber !=3){
				imgView1.setImageDrawable(getResources().getDrawable(R.drawable.storage_grey_btn));
				textView1.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_grey_btn));
				textView2.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_orange_btn));
				textView3.setTextColor(getResources().getColor(R.color.theme_orange));
				imgView4.setImageDrawable(getResources().getDrawable(R.drawable.pie_chart_grey_btn));
				textView4.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView5.setImageDrawable(getResources().getDrawable(R.drawable.setting_grey_btn));
				textView5.setTextColor(getResources().getColor(R.color.ziti666666));
				dongtaiThree();
				tabNumber = 3;
			}
			break;
		case R.id.analytic_statistics:
			if (tabNumber != 4) {
				if (RightsHelper.isHaveRight(RightsHelper.tj,
						mSharedPreferences) == true) {
					imgView1.setImageDrawable(getResources().getDrawable(
							R.drawable.storage_grey_btn));
					textView1.setTextColor(getResources().getColor(
							R.color.ziti666666));
					imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_grey_btn));
					textView2.setTextColor(getResources().getColor(R.color.ziti666666));
					imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_grey_btn));
					textView3.setTextColor(getResources().getColor(R.color.ziti666666));
					imgView4.setImageDrawable(getResources().getDrawable(
							R.drawable.pie_chart_orange_btn));
					textView4.setTextColor(getResources().getColor(R.color.theme_orange));
					imgView5.setImageDrawable(getResources().getDrawable(
							R.drawable.setting_grey_btn));
					textView5.setTextColor(getResources().getColor(
							R.color.ziti666666));
					dongtaiFour();
				} else {
					Toast.makeText(this, "�Բ�����û�з���ͳ��Ȩ�ޡ�", Toast.LENGTH_SHORT).show();
				}
				tabNumber = 4;
			}
			break;
		case R.id.setting:
			if (tabNumber != 5) {
				imgView1.setImageDrawable(getResources().getDrawable(
						R.drawable.storage_grey_btn));
				textView1.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView2.setImageDrawable(getResources().getDrawable(R.drawable.order_grey_btn));
				textView2.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView3.setImageDrawable(getResources().getDrawable(R.drawable.customer_grey_btn));
				textView3.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView4.setImageDrawable(getResources().getDrawable(
						R.drawable.pie_chart_grey_btn));
				textView4.setTextColor(getResources().getColor(R.color.ziti666666));
				imgView5.setImageDrawable(getResources().getDrawable(
						R.drawable.setting_orange_btn));
				textView5.setTextColor(getResources().getColor(R.color.theme_orange));
				dongtaiFive();
				tabNumber = 5;
			}
			break;
		}
	}

	Runnable ExitloadRun = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = WebserviceImport.delMEI(MyApplication.getInstance()
					.getIEMI(), mSharedPreferences);
			JPushInterface.stopPush(getApplicationContext());
			msg.setTarget(mHandler3);
			mHandler3.sendMessage(msg);
		}

	};
	@SuppressLint("HandlerLeak")
	Handler mHandler3 = new Handler() {
		public void handleMessage(Message msg) {
			pro_dialog.dismiss();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	};
	
	private Runnable handleRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Looper.prepare();
			runnableHandler = new Handler(){
				public void handleMessage(Message msg) {
					stopflag = false;
					switch(msg.what){
					case 0:
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.custom_progressbar_layout, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView titleTextView = mpDialog.getView(R.id.title);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								TextView percentTextView = mpDialog.getView(R.id.percentTextView);
								ProgressBar pregressbar = mpDialog.getView(R.id.mybar);
								
								titleTextView.setText("���ڸ��»�Ʒ��Ϣ");
								pregressbar.setMax(100);
								pregressbar.setProgress(0);
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										stopflag = true;
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();

//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//						mpDialog.setTitle("���ڸ��»�Ʒ��Ϣ");
//						mpDialog.setMax(100);
//						mpDialog.setProgress(0);
//						mpDialog.setIndeterminate(false);
//						mpDialog.setCancelable(false);
//						mpDialog.setButton(-2, "ȡ��",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										stopflag = true;
//										dialog.cancel();
//									}
//								});
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 1:
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.custom_progressbar_layout, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView titleTextView = mpDialog.getView(R.id.title);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								TextView percentTextView = mpDialog.getView(R.id.percentTextView);
								ProgressBar pregressbar = mpDialog.getView(R.id.mybar);
								
								titleTextView.setText("���ڸ��²ֿ�����Ϣ");
								pregressbar.setMax(100);
								pregressbar.setProgress(0);
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										stopflag = true;
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();
						
//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//						mpDialog.setTitle("���ڸ��²ֿ�����Ϣ");
//						mpDialog.setMax(100);
//						mpDialog.setProgress(0);
//						mpDialog.setIndeterminate(false);
//						mpDialog.setCancelable(false);
//						mpDialog.setButton(-2, "ȡ��",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										stopflag = true;
//										dialog.cancel();
//
//									}
//
//								});
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 2:
						
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView contentTextView = mpDialog.getView(R.id.content_txtview);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								contentTextView.setText("���ڸ��»�Ʒ����");
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										stopflag = true;
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();
						
//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setMessage("���ڸ��»�Ʒ����");
//						mpDialog.setCancelable(false);
//						mpDialog.setButton(-2, "ȡ��",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										stopflag = true;
//										dialog.cancel();
//									}
//
//								});
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 3:
						
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView contentTextView = mpDialog.getView(R.id.content_txtview);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								contentTextView.setText("���ڸ���������λ");
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										stopflag = true;
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();
						
//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setMessage("���ڸ���������λ");
//						mpDialog.setCancelable(false);
//						mpDialog.setButton(-2, "ȡ��",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										stopflag = true;
//										dialog.cancel();
//									}
//
//								});
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 4:
						
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView contentTextView = mpDialog.getView(R.id.content_txtview);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								contentTextView.setText("���ڸ���������λ");
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();
						
//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setMessage("���ڸ��²�������");
//						mpDialog.setCancelable(true);
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 5:
						
						mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
						mpDialog.setCancelable(false);
						mpDialog.setDialogContentListener(new DialogContentListener() {
							
							@Override
							public void contentExecute(View parent, Dialog dialog,Object[] objs) {
								// TODO �Զ����ɵķ������
								TextView contentTextView = mpDialog.getView(R.id.content_txtview);
								TextView cancelTextView = mpDialog.getView(R.id.cancel);
								contentTextView.setText("���ڸ��²ֿ���Ϣ");
								cancelTextView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO �Զ����ɵķ������
										
										mpDialog.dismiss();
									}
								});
							}
						},null);
						mpDialog.setCancelable(false);
						mpDialog.show();
						
//						mpDialog = new ProgressDialog(FinalMainActivity.this,R.style.yuanjiao_dialog);
//						mpDialog.setMessage("���ڸ��²ֿ���Ϣ");
//						mpDialog.setCancelable(true);
//						mpDialog.show();
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 6:
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					case 7:
						fixedThreadPool.execute(loadRunnableList.removeFirst());
						break;
					}
				};
			};
			runnableHandlerSemaphore.release();
			Looper.loop();
		}
	};
	
	private void mayLoadData() {
		try {
			if(runnableHandler==null){
			runnableHandlerSemaphore.acquire();
			}
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		if(hpxinxiflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(hpxinxiRunnable);
			msg.what=0;
			runnableHandler.sendMessage(msg);
		}
		if(ckkcxinxiflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(ckkcRunnable);
			msg.what=1;
			runnableHandler.sendMessage(msg);
		}
		if(hpleixinflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(hpleixiRunnable);
			msg.what=2;
			runnableHandler.sendMessage(msg);
		}
		if(dwxinxiflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(dwxinxiRunnable);
			msg.what=3;
			runnableHandler.sendMessage(msg);
		}
		if(canshuxinxiflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(canshuxinxiRunnable);
			msg.what=4;
			runnableHandler.sendMessage(msg);
		}
		if(cangkuxinxiflag){
			Message msg = Message.obtain();
			try {
				mSemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			loadRunnableList.add(cangkuxinxiRunnable);
			msg.what=5;
			runnableHandler.sendMessage(msg);
		}
		
		Message msg = Message.obtain();
		try {
			mSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		loadRunnableList.add(tongjiRunnable);
		msg.what=6;
		runnableHandler.sendMessage(msg);
		runnableHandlerSemaphore.release();
	}
	
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_HP,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "��Ʒ��Ϣ�������", Toast.LENGTH_SHORT).show();
				break;
			case -2:
				Toast.makeText(FinalMainActivity.this, "��ȡ��Ʒ�����쳣", Toast.LENGTH_SHORT)
						.show();
				break;
			case -3:
				Toast.makeText(FinalMainActivity.this, "��Ʒ��Ϣ�����쳣", Toast.LENGTH_SHORT)
						.show();
				break;
			case 1:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_CKKC,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "�ֿ�����Ϣ�������", Toast.LENGTH_SHORT).show();
				break;
			case -9:
				Toast.makeText(FinalMainActivity.this, "��ȡ�ֿ�����Ϣ�����쳣", Toast.LENGTH_SHORT).show();
				break;
			case -7:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_CKKC,formatter.format(new Date(System.currentTimeMillis()))).commit();
				Toast.makeText(FinalMainActivity.this, "�ֿ�����Ϣ�����쳣", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_LB,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "��Ʒ���͸������", Toast.LENGTH_SHORT).show();
				break;
			case -4:
				Toast.makeText(FinalMainActivity.this, "��Ʒ���������쳣", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_DW,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "������λ�������", Toast.LENGTH_SHORT).show();
				break;
			case -5:
				Toast.makeText(FinalMainActivity.this, "������λ�����쳣", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_TYPE,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "�����������", Toast.LENGTH_SHORT).show();
				break;
			case -6:
				Toast.makeText(FinalMainActivity.this, "���������쳣", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				mSharedPreferences.edit().putString(ShareprefenceBean.UPDATE_TIME_CK,formatter.format(new Date(System.currentTimeMillis()))).commit();
//				Toast.makeText(FinalMainActivity.this, "�ֿ���Ϣ�������", Toast.LENGTH_SHORT).show();
				break;
			case -8:
				Toast.makeText(FinalMainActivity.this, "�ֿ���Ϣ�����쳣", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	private Runnable hpxinxiRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try {
				db.beginTransaction();
				dm_op.del_tableContent(DataBaseHelper.TB_HP);
				int total = WebserviceImport.Get_Total(
						WebserviceHelper.GetHp_Total, -1, mSharedPreferences);
				int leave = WebserviceImport.GetHp_Total_Leave("-1",
						mSharedPreferences);
				String id = "0", date = "";
				hp_num = 0;
				String[] values = new String[str.length];
				if (total >= 0 && (leave > 0 || leave == 0)) {
						while (leave != 0) {
							if (stopflag == true) {
								break;
							}
							if (total == leave) {
								ls = WebserviceImport.GetHpInfo_top(200, "0", 1,
										-1, str, str1, mSharedPreferences);
								if (!ls.isEmpty()) {
									id = (String) ls.get(ls.size() - 1).get(
											DataBaseHelper.ID);
									leave = WebserviceImport.GetHp_Total_Leave(id,
											mSharedPreferences);
//									mpDialog.setProgress(GtProgress(leave, total));
									((ProgressBar) mpDialog.getView(R.id.mybar)).setProgress(GtProgress(leave, total));
//									((TextView) mpDialog.getView(R.id.percentTextView)).setText(GtProgress(leave, total)+"%");
									date = formatter.format(new Date(System
											.currentTimeMillis()));
									for (int i = 0; i < ls.size(); i++) {
										for (int j = 0; j < str.length; j++) {

											if (RightsHelper.isHaveRight(
													RightsHelper.system_cw,
													mSharedPreferences) == true) {
												values[j] = (String) ls.get(i).get(
														str[j]);
											} else {
												if (str[j]
														.equals(DataBaseHelper.RKCKJ)
														|| str[j]
																.equals(DataBaseHelper.CKCKJ)
														|| str[j]
																.equals(DataBaseHelper.CKCKJ2)
														|| str[j]
																.equals(DataBaseHelper.KCJE)) {
													values[j] = "";
												} else {
													values[j] = (String) ls.get(i)
															.get(str[j]);
												}
											}
										}
										dm_op.insert_into_fromfile(
												DataBaseHelper.TB_HP, str, values,
												db, date);
										hp_num++;
									}
								}
							} else {
								ls = WebserviceImport.GetHpInfo_top(200, id, 1, -1,
										str, str1, mSharedPreferences);
								if (!ls.isEmpty()) {
									id = (String) ls.get(ls.size() - 1).get(
											DataBaseHelper.ID);
									leave = WebserviceImport.GetHp_Total_Leave(id,
											mSharedPreferences);
//									mpDialog.setProgress(GtProgress(leave, total));
									((ProgressBar) mpDialog.getView(R.id.mybar)).setProgress(GtProgress(leave, total));
//									((TextView) mpDialog.getView(R.id.percentTextView)).setText(GtProgress(leave, total)+"%");
									date = formatter.format(new Date(System
											.currentTimeMillis()));
									for (int i = 0; i < ls.size(); i++) {
										for (int j = 0; j < str.length; j++) {
											if (RightsHelper.isHaveRight(
													RightsHelper.system_cw,
													mSharedPreferences) == true) {
												values[j] = (String) ls.get(i).get(
														str[j]);
											} else {
												if (str[j]
														.equals(DataBaseHelper.RKCKJ)
														|| str[j]
																.equals(DataBaseHelper.CKCKJ)
														|| str[j]
																.equals(DataBaseHelper.CKCKJ2)
														|| str[j]
																.equals(DataBaseHelper.KCJE)) {
													values[j] = "";
												} else {
													values[j] = (String) ls.get(i)
															.get(str[j]);
												}
											}
										}
										dm_op.insert_into_fromfile(
												DataBaseHelper.TB_HP, str, values,
												db, date);
										hp_num++;
									}
								}
							}
						}
					msg.what = 0;
				} else if (total <= -1 || leave < 0) {
					msg.what = -2;
				} else {
					msg.what = 0;
				}
			} catch (Exception e) {
				msg.what = -3;
			} finally {
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
			
		}
	};

	private Runnable ckkcRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try{
				db.beginTransaction();
				dm_op.del_tableContent(DataBaseHelper.TB_CKKC);
				int total = WebserviceImport
						.GetCKKC_Total(mSharedPreferences);
				int leave = WebserviceImport.GetCKKC_Total_Leave("-1",
						mSharedPreferences);
				String id = "0";
				String[] values = new String[str12.length];
				if (total >= 0 && (leave > 0 || leave == 0)) {
						while (leave != 0) {
							if (stopflag == true) {
								break;
							}
							if (total == leave) {
								ls = WebserviceImport.GetCKKC_top(200,
										"0", str12, str13,
										mSharedPreferences);
								if (!ls.isEmpty()) {
									id = (String) ls.get(ls.size() - 1)
											.get(DataBaseHelper.ID);
									leave = WebserviceImport
											.GetCKKC_Total_Leave(id,
													mSharedPreferences);
//									mpDialog.setProgress(GtProgress(
//											leave, total));
									((ProgressBar) mpDialog.getView(R.id.mybar)).setProgress(GtProgress(leave, total));
//									((TextView) mpDialog.getView(R.id.percentTextView)).setText(GtProgress(leave, total)+"%");
									for (int i = 0; i < ls.size(); i++) {
										for (int j = 0; j < str12.length; j++) {
											values[j] = (String) ls
													.get(i).get(
															str12[j]);
										}
										dm_op.insert_into_fromfile(
												DataBaseHelper.TB_CKKC,
												str12, values, db);
										ckkc_num++;
									}
								}
							} else {
								ls = WebserviceImport.GetCKKC_top(200,
										id, str12, str13,
										mSharedPreferences);
								if (!ls.isEmpty()) {
									id = (String) ls.get(ls.size() - 1)
											.get(DataBaseHelper.ID);
									leave = WebserviceImport
											.GetCKKC_Total_Leave(id,
													mSharedPreferences);
//									mpDialog.setProgress(GtProgress(
//											leave, total));
									((ProgressBar) mpDialog.getView(R.id.mybar)).setProgress(GtProgress(leave, total));
//									((TextView) mpDialog.getView(R.id.percentTextView)).setText(GtProgress(leave, total)+"%");
									for (int i = 0; i < ls.size(); i++) {
										for (int j = 0; j < str12.length; j++) {
											values[j] = (String) ls
													.get(i).get(
															str12[j]);
										}
//										dm_op.Del_CKKC(
//												(String) ls
//														.get(i)
//														.get(DataBaseHelper.HPID),
//												(String) ls
//														.get(i)
//														.get(DataBaseHelper.CKID),
//												db);
										dm_op.insert_into_fromfile(
												DataBaseHelper.TB_CKKC,
												str12, values, db);
										ckkc_num++;
									}

								}
							}
						}
						msg.what=1;
				} else {
					msg.what = -9;
				}
			} catch (Exception e) {
				msg.what = -7;
			} finally {
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
		}
	};
	
	private Runnable hpleixiRunnable= new Runnable() {
		public void run() {
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try {
				int maxid = WebserviceImport.GetMaxID_LB(mSharedPreferences);
				String id = "0";
				lb_num = 0;
				String[] values = new String[str2.length];
				db.beginTransaction();
				dm_op.del_tableContent(DataBaseHelper.TB_hplbTree);
				while (!id.equals(String.valueOf(maxid))) {
					if (stopflag == true) {
						break;
					}
					ls = WebserviceImport.GetHPLB(200, id, str2,
							str3, mSharedPreferences);
					if (!ls.isEmpty()) {
						for (int i = 0; i < ls.size(); i++) {
							for (int j = 0; j < str2.length; j++) {
								values[j] = (String) ls.get(i).get(
										str2[j]);
							}
							dm_op.insert_into_fromfile(
									DataBaseHelper.TB_hplbTree,
									str2, values, db);
							lb_num++;
						}
						id = (String) ls.get(ls.size() - 1).get(
								DataBaseHelper.ID);
					} else {
						break;
					}
				}
				msg.what=2;
			} catch (Exception e) {
				msg.what = -4;
			} finally {
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
		}
	};
	
	private Runnable dwxinxiRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try {
			int maxid = WebserviceImport
					.GetMaxID_DW(mSharedPreferences);
			String id = "0";
			dw_num = 0;
			String[] values = new String[str4.length];
			db.beginTransaction();
			dm_op.del_tableContent(DataBaseHelper.TB_Company);
				while (!id.equals(String.valueOf(maxid))) {
					if (stopflag == true) {
						break;
					}
					ls = WebserviceImport.GetDW(200, id, str4,
							str5, mSharedPreferences);
					if (!ls.isEmpty()) {
						for (int i = 0; i < ls.size(); i++) {
							for (int j = 0; j < str4.length; j++) {
								values[j] = (String) ls.get(i).get(
										str4[j]);
							}
							dm_op.insert_into_fromfile(
									DataBaseHelper.TB_Company,
									str4, values, db);
							dw_num++;
						}
						id = (String) ls.get(ls.size() - 1).get(
								DataBaseHelper.ID);
					} else {
						break;
					}
				}
				msg.what=3;
			} catch (Exception e) {
				msg.what = -5;
			} finally {
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
		}
	};
	
	private Runnable canshuxinxiRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try {
			conf_num = 0;
			String[] values = new String[str6.length];
			db.beginTransaction();
			dm_op.del_tableContent(DataBaseHelper.TB_Conf);
				ls = WebserviceImport.GetConf("�Զ����ֶ�", "", str6,
						str7, mSharedPreferences);
				if (!ls.isEmpty()) {
//					dm_op.Del_Conf(new String[] { "�Զ����ֶ�" }, db);
					for (int i = 0; i < ls.size(); i++) {
						for (int j = 0; j < str6.length; j++) {
							values[j] = (String) ls.get(i).get(
									str6[j]);
						}
						dm_op.insert_into_fromfile(
								DataBaseHelper.TB_Conf, str6,
								values, db);
						conf_num++;
					}
				}
				
				ls = WebserviceImport.GetConf("�����Զ����ֶ�", "", str6, str7, mSharedPreferences);
				if(!ls.isEmpty()){
					for (int i = 0; i < ls.size(); i++) {
						for (int j = 0; j < str6.length; j++) {
							values[j] = (String) ls.get(i).get(
									str6[j]);
						}

						dm_op.insert_into_fromfile(
								DataBaseHelper.TB_Conf, str6,
								values, db);
						conf_num++;
					}
				}
				
				ls = WebserviceImport.GetConf("������Ϣ", "", str6,
						str7, mSharedPreferences);
				if (!ls.isEmpty()) {
//					dm_op.Del_Conf(new String[] { "������Ϣ" }, db);
					for (int i = 0; i < ls.size(); i++) {
						for (int j = 0; j < str6.length; j++) {
							values[j] = (String) ls.get(i).get(
									str6[j]);
						}
						dm_op.insert_into_fromfile(
								DataBaseHelper.TB_Conf, str6,
								values, db);
						conf_num++;
					}
				}
				
				ls = WebserviceImport.GetConf("����ʶ��", "", str6, str7, mSharedPreferences);
				if(!ls.isEmpty()){
//					dm_op.Del_Conf(new String[] { "����ʶ��" }, db);
					for(int i=0;i<ls.size();i++){
						for(int j = 0;j<str6.length;j++){
							values[j] = ls.get(i).get(str6[j]).toString();
						}
						dm_op.insert_into_fromfile(DataBaseHelper.TB_Conf, str6, values, db);
						conf_num++;
					}
				}
				
				ls = WebserviceImport.GetIOType(str10, str11,
						mSharedPreferences);
				values = new String[str10.length];
				if (!ls.isEmpty()) {
//					dm_op.Del_Conf(new String[] { "�������" }, db);
//					dm_op.Del_Conf(new String[] { "��������" }, db);
					for (int i = 0; i < ls.size(); i++) {
						for (int j = 0; j < str10.length; j++) {
							if (str10[j].equals(DataBaseHelper.GID)) {
								if (ls.get(i).get(str10[j])
										.equals("1")) {
									values[j] = "�������";
								} else if (ls.get(i).get(str10[j])
										.equals("2")) {
									values[j] = "��������";
								}
							} else {
								values[j] = (String) ls.get(i).get(
										str10[j]);
							}

						}
						dm_op.insert_into_fromfile(
								DataBaseHelper.TB_Conf, str10,
								values, db);
						conf_num++;
					}
				}
				ls = WebserviceImport.GetDep(str17, str16,
						mSharedPreferences);
				values = new String[str16.length];
				dm_op.del_tableContent(DataBaseHelper.TB_depTree);
				if (!ls.isEmpty()) {
					for (int i = 0; i < ls.size(); i++) {
						for (int j = 0; j < str16.length; j++) {
							values[j] = (String) ls.get(i).get(
									str17[j]);
						}
//						dm.del_Dep((String) ls.get(i).get("id"), db);
						dm_op.insert_into_fromfile(
								DataBaseHelper.TB_depTree, str17,
								values, db);
						conf_num++;
					}
				}
				msg.what = 4;
			} catch (Exception e) {
				msg.what = -6;
			} finally {
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				MyApplication.getInstance().setALLPoint();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
		}
	};
	
	private Runnable cangkuxinxiRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			DataBaseManager dataBaseManager = DataBaseManager.getInstance(FinalMainActivity.this);
			SQLiteDatabase db = dataBaseManager.openDataBase();
			try {
				
				db.beginTransaction();
				dm_op.del_tableContent(DataBaseHelper.TB_CK);
				ck_num = 0;
				ls = WebserviceImport.GetCK(str14, str15,
						mSharedPreferences);
				if (!ls.isEmpty()) {
					String[] values = new String[str14.length];
					for (int i = 0; i < ls.size(); i++) {
						String id = (String) ls.get(i).get(
								DataBaseHelper.ID);
						if (id != null && !id.equals("")) {
							for (int j = 0; j < str14.length; j++) {
								values[j] = (String) ls.get(i).get(
										str14[j]);
							}
							// dm_op.Del_CK(id);
							dm_op.insert_into1(DataBaseHelper.TB_CK,
									str14, values, db);
							ck_num++;
						}
					}
				}
				msg.what=5;
			} catch (Exception e) {
				// TODO: handle exception
				msg.what=-8;
			} finally{
				db.setTransactionSuccessful();
				db.endTransaction();
				dataBaseManager.closeDataBase();
				mpDialog.dismiss();
				mSemaphore.release();
				myHandler.sendMessage(msg);
			}
		}
	};
	

	private Runnable tongjiRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Looper.prepare();
			StringBuilder strBuilder = new StringBuilder();
			if (hpxinxiflag) {
				strBuilder.append("��Ʒ��Ϣ���£�" + String.valueOf(hp_num)
						+ "��\n");
			}
			if(ckkcxinxiflag){
				strBuilder.append("�ֿ�����Ϣ���£�" + String.valueOf(ckkc_num)
						+ "��\n");
			}
			if (hpleixinflag) {
				strBuilder.append("��Ʒ���͸��£�" + String.valueOf(lb_num)
						+ "��\n");
			}
			if (dwxinxiflag) {
				strBuilder.append("������λ��Ϣ���£�" + String.valueOf(dw_num)
						+ "��\n");
			}
			if (canshuxinxiflag) {
				strBuilder.append("�������£�" + String.valueOf(conf_num)
						+ "��\n");
			}
			if (cangkuxinxiflag) {
				strBuilder.append("�ֿ���Ϣ���£�" + String.valueOf(ck_num) + "��");
			}
			
			mpDialog = new CommonDialog(FinalMainActivity.this, R.layout.prompt_dialog_layout2, R.style.yuanjiao_dialog);
			mpDialog.setDialogContentListener(new DialogContentListener() {
				
				@Override
				public void contentExecute(View parent, Dialog dialog, Object[] objs) {
					// TODO �Զ����ɵķ������
					TextView contentTextView = mpDialog.getView(R.id.content_txtview);
					TextView cancelTextView = mpDialog.getView(R.id.cancel);
					contentTextView.setText(objs[0].toString());
					cancelTextView.setText("ȷ��");
					cancelTextView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO �Զ����ɵķ������
							
							mpDialog.dismiss();
						}
					});
				}
			},new Object[]{strBuilder});
			mpDialog.show();
			
//			AlertDialog.Builder builder = new AlertDialog.Builder(
//					FinalMainActivity.this);
//			builder.setMessage(strBuilder.toString());
//			builder.setCancelable(false);
//			builder.setPositiveButton("ȷ��",
//					new DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog,
//								int which) {
//							// TODO Auto-generated method stub
//							hp_num = 0;
//							lb_num = 0;
//							dw_num = 0;
//							conf_num = 0;
//							ck_num = 0;
//							ckkc_num = 0;
//							dialog.dismiss();
//
//						}
//					});
//			builder.create().show();
			mSemaphore.release();
			Looper.loop();
		}
	};
	
	
	private int GtProgress(int leave, int total) {
		if (leave == total) {
			return 100;
		} else {
			return (total - leave) * 100 / total;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stu
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			CommonDialog myDialog = new CommonDialog(this, R.layout.prompt_dialog_layout, R.style.yuanjiao_dialog);
			myDialog.setDialogContentListener(new DialogContentListener() {
				
				@Override
				public void contentExecute(View parent, final Dialog dialog, Object[] objs) {
					// TODO �Զ����ɵķ������
					TextView titleTextView = (TextView) parent.findViewById(R.id.title);
					TextView contentTextview = (TextView) parent.findViewById(R.id.content_txtview);
					TextView cancelTextview = (TextView) parent.findViewById(R.id.cancel);
					TextView confirmTextView = (TextView) parent.findViewById(R.id.confirm);
					
					titleTextView.setVisibility(View.GONE);
					contentTextview.setText("�Ƿ��˳�����");
					cancelTextview.setText("��");
					confirmTextView.setText("��");
					cancelTextview.setOnClickListener(new OnClickListener() {
						
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
							if (mSharedPreferences.getInt(
									ShareprefenceBean.IS_LOGIN, -1) == 1) {
								pro_dialog = ProgressDialog.show(
										FinalMainActivity.this, null, "�����˳�");
								new Thread(ExitloadRun).start();
								mSharedPreferences.edit()
										.putInt(ShareprefenceBean.IS_LOGIN, -1)
										.commit();
								dialog.dismiss();
							} else if (mSharedPreferences.getInt(
									ShareprefenceBean.IS_LOGIN, -1) == -1) {
								mSharedPreferences.edit()
										.putInt(ShareprefenceBean.IS_LOGIN, -1)
										.commit();
								dialog.dismiss();
								// �ر�app����
								finish();
								android.os.Process
										.killProcess(android.os.Process.myPid());
								System.exit(0);
							}
						}
					});
					
				}
			}, null);
			myDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}