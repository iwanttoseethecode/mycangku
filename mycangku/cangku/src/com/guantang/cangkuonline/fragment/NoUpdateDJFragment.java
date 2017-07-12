package com.guantang.cangkuonline.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.activity.AddDJActivity;
import com.guantang.cangkuonline.activity.DJMXCheckActivity;
import com.guantang.cangkuonline.activity.HistoryDJ_DetailsActivity;
import com.guantang.cangkuonline.activity.LocalDJActivity;
import com.guantang.cangkuonline.adapter.MyDJAdapter;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.helper.RightsHelper;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenu;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenuCreator;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenuItem;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenuListView;
import com.guantang.cangkuonline.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.guantang.cangkuonline.webservice.WebserviceHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NoUpdateDJFragment extends Fragment implements
		OnMenuItemClickListener,OnClickListener,OnItemClickListener {

	private List<Map<String, Object>> mlist = new ArrayList<Map<String, Object>>();
	private SwipeMenuListView myListView;
	private LinearLayout alluploadLayout;
	private SharedPreferences mSharedPreferences;
	private ProgressDialog progDialog,allLoaDialog;
	private String uploadDJID = "", dh = "", dacttype = "";
	private int op_type = 1, ckid = -1;
	private ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
	private String[] strs1 = { DataBaseHelper.MVDH, DataBaseHelper.MVDT,
			DataBaseHelper.DWName, DataBaseHelper.JBR, DataBaseHelper.BZ,
			DataBaseHelper.mType, DataBaseHelper.actType,
			DataBaseHelper.DepName, DataBaseHelper.DepID, DataBaseHelper.CKMC,
			DataBaseHelper.CKID, DataBaseHelper.Lrr, DataBaseHelper.Lrsj,
			DataBaseHelper.DWID, DataBaseHelper.Details, DataBaseHelper.HPzj };
	private String[] strs2 = { "mvdh", "mvdt", "dwName", "jbr", "bz", "mType",
			"actType", "depName", "depId", "ckName", "ckid", "lrr", "lrsj",
			"dwid", "hpDetails", "hpzjz" };
	private String[] strs3 = { DataBaseHelper.HPID, DataBaseHelper.MVDDATE,
			DataBaseHelper.MSL, DataBaseHelper.MVDType, DataBaseHelper.DH,
			DataBaseHelper.MVDirect, DataBaseHelper.DJ, DataBaseHelper.ZJ,
			DataBaseHelper.MVType, DataBaseHelper.CKID, DataBaseHelper.BTKC,
			DataBaseHelper.ATKC };
	private String[] strs4 = { "hpid", "mvddt", "msl", "mdType", "mvddh",
			"mddirect", "dj", "zj", "dactType", "ckid", "Btkc", "Atkc" };
	private DataBaseOperateMethod dm_op;
	private MyDJAdapter DJadapter;
	private Context context;
	
	public interface RefreshInterface{
		void execute();
	}
	public RefreshInterface mRefreshInterface;
	

	public static NoUpdateDJFragment getInstance(List<Map<String, Object>> mList) {
		NoUpdateDJFragment nudj = new NoUpdateDJFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("list", (Serializable) mList);
		nudj.setArguments(bundle);
		return nudj;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO �Զ����ɵķ������
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		mlist = (List<Map<String, Object>>) getArguments().getSerializable(
				"list");
		mSharedPreferences = MyApplication.getInstance().getSharedPreferences();
		dm_op = new DataBaseOperateMethod(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		View view = inflater.inflate(R.layout.localdj_itemlist, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onActivityCreated(savedInstanceState);
		myListView = (SwipeMenuListView) getView().findViewById(R.id.djlist);
		alluploadLayout = (LinearLayout) getView().findViewById(R.id.alluploadLayout);
		alluploadLayout.setVisibility(View.VISIBLE);
		alluploadLayout.setOnClickListener(this);
		init();
	}

	public void init() {
		DJadapter = new MyDJAdapter(context);
		myListView.setAdapter(DJadapter);
		DJadapter.setData(mlist);
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				if (menu.getViewType() == -1) {
					createUpLoadMenu(menu);
					createDeleteMenu(menu);
				}
			}
		};
		myListView.setMenuCreator(creator);
		myListView.setOnMenuItemClickListener(this);
		myListView.setOnItemClickListener(this);
	}

	public void createUpLoadMenu(SwipeMenu menu) {
		SwipeMenuItem UploadItem = new SwipeMenuItem(context);
		// set item background
		UploadItem.setBackground(getResources().getDrawable(R.color.grey));
		// set item width
		UploadItem.setWidth(dp2px(90));
		//
		UploadItem.setTitle("�ϴ�");
		// set item title fontsize
		UploadItem.setTitleSize(18);
		// set item title font color
		UploadItem.setTitleColor(Color.WHITE);
		// set a icon
		UploadItem.setIcon(R.drawable.gpc);
		// add to menu
		menu.addMenuItem(UploadItem);
	}

	public void createDeleteMenu(SwipeMenu menu) {
		// create "delete" item
		SwipeMenuItem deleteItem = new SwipeMenuItem(context);
		// set item background
		deleteItem
				.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
		// set item width
		deleteItem.setWidth(dp2px(90));
		// set a icon
		deleteItem.setIcon(R.drawable.ic_delete);
		// add to menu
		menu.addMenuItem(deleteItem);
	}

//	public void permissionDialog(String str){
//		AlertDialog.Builder builder = new AlertDialog.Builder(
//				context);
//		builder.setMessage("�Բ�����û��"+str+"������Ȩ�ޡ�");
//		builder.setNegativeButton("ȷ��",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog,
//							int which) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//					}
//				});
//		builder.create().show();
//	}
	
	@Override
	public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		// TODO �Զ����ɵķ������
		Map<String, Object> map = mlist.get(position);
		if (menu.getViewType() == -1) {
			
			switch(index){
			case 0:
				if (WebserviceImport.isOpenNetwork(context)) {
					op_type = Integer.valueOf(map.get(DataBaseHelper.mType)
							.toString());
//					switch(op_type){
//					case 0:
//						if (!RightsHelper.isHaveRight(RightsHelper.dj_pd_add,
//								mSharedPreferences)) {
//							permissionDialog("�̵㵥��");
//							return false;
//						}
//						break;
//					case 1:
//						if (!RightsHelper.isHaveRight(RightsHelper.dj_rk_add,
//								mSharedPreferences)) {
//							permissionDialog("��ⵥ��");
//							return false;
//						}
//						break;
//					case 2:
//						if (!RightsHelper.isHaveRight(RightsHelper.dj_ck_add,
//								mSharedPreferences) == false) {
//							permissionDialog("���ⵥ��");
//							return false;
//						}
//						break;
//					case 3:
//						break;
//					}
					progDialog = ProgressDialog.show(context, null,
							"�����ϴ�����");
					uploadDJID = map.get(DataBaseHelper.HPM_ID).toString();
					ckid = Integer.valueOf(map.get(DataBaseHelper.CKID)
							.toString());
					dh = map.get(DataBaseHelper.MVDH).toString();
					if (map.get(DataBaseHelper.actType) != null) {
						dacttype = map.get(DataBaseHelper.actType).toString();
					}
					cacheThreadPool.execute(addRun);
				} else {
					Toast.makeText(context, "����δ���ӡ���", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case 1:
				dm_op.Del_Moved(map.get(DataBaseHelper.HPM_ID).toString());
				dm_op.Del_Movem(map.get(DataBaseHelper.HPM_ID).toString());
				mlist.remove(map);
				DJadapter.setData(mlist);
				break;
			}
			
		}
		return false;
	}
	
	

	Runnable addRun = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			String[] values_movem = new String[strs1.length];
			List<Map<String, Object>> ls2 = dm_op.Gt_Movem(uploadDJID, strs1);
			if (ls2 != null) {
				for (int j = 0; j < strs1.length; j++) {
					values_movem[j] = (String) ls2.get(0).get(strs1[j]);
				}
			}
			List<Map<String, Object>> lss = dm_op.Gt_Moved(uploadDJID, strs3);
			String[] values_moved = new String[lss.size() * strs3.length];
			for (int j = 0; j < lss.size(); j++) {
				for (int n = 0; n < strs3.length; n++) {
					values_moved[j * strs3.length + n] = (String) lss.get(j)
							.get(strs3[n]);
				}
			}
			int flag = -1;
			try {
				String type = (String) ls2.get(0).get(DataBaseHelper.mType);
				switch (Integer.parseInt(type)) {
				case 0:
					flag = WebserviceImport.AddDJ(values_movem, strs2,
							values_moved, strs4, WebserviceHelper.AddPD,
							mSharedPreferences);
					break;
				case 1:
					flag = WebserviceImport.AddDJ(values_movem, strs2,
							values_moved, strs4, WebserviceHelper.AddRK,
							mSharedPreferences);
					break;
				case 2:
					flag = WebserviceImport.AddChuKu(values_movem, strs2,
							values_moved, strs4, false, mSharedPreferences);
					break;

				}
			} catch (Exception e) {
				flag = -1;
			}
			if (flag > 0) {
				dm_op.Update_DJtype(uploadDJID, 1);// type
													// 0����û�б��棬1�����ϴ��ͱ��汾�أ�-1�������汾��û�ϴ�
			} else {
				dm_op.Update_DJtype(uploadDJID, -1);
			}
			msg.what = flag;
			msg.setTarget(mHandler);
			mHandler.sendMessage(msg);
		}
	};

	Runnable addRun2 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			Message msg = new Message();
			String[] values_movem = new String[strs1.length];
			List<Map<String, Object>> ls2 = dm_op.Gt_Movem(uploadDJID, strs1);
			if (ls2 != null) {
				for (int j = 0; j < strs1.length; j++) {
					values_movem[j] = (String) ls2.get(0).get(strs1[j]);
				}
			}
			List<Map<String, Object>> lss = dm_op.Gt_Moved(uploadDJID, strs3);
			String[] values_moved = new String[lss.size() * strs3.length];
			for (int j = 0; j < lss.size(); j++) {
				for (int n = 0; n < strs3.length; n++) {
					values_moved[j * strs3.length + n] = (String) lss.get(j)
							.get(strs3[n]);
				}
			}
			int flag;
			try {
				String type = (String) ls2.get(0).get(DataBaseHelper.mType);
				switch (Integer.parseInt(type)) {
				case 0:
					flag = WebserviceImport.AddDJ(values_movem, strs2,
							values_moved, strs4, WebserviceHelper.AddPD,
							mSharedPreferences);
					break;
				case 1:
					flag = WebserviceImport.AddDJ(values_movem, strs2,
							values_moved, strs4, WebserviceHelper.AddRK,
							mSharedPreferences);
					break;
				case 2:
					flag = WebserviceImport.AddChuKu(values_movem, strs2,
							values_moved, strs4, true, mSharedPreferences);
					break;
				default:
					flag = -1;
					break;
				}
			} catch (Exception e) {
				flag = -1;
			}
			if (flag > 0) {
				dm_op.Update_DJtype(uploadDJID, 1);// type
													// 0����û�б��棬1�����ϴ��ͱ��汾�أ�-1�������汾��û�ϴ�
			} else {
				dm_op.Update_DJtype(uploadDJID, -1);
			}
			msg.what = flag;
			msg.setTarget(mHandler);
			mHandler.sendMessage(msg);
		}
	};

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			progDialog.dismiss();
			if (msg.what > 0) {
				for (int i = 0; i < mlist.size(); i++) {
					if (mlist.get(i).get(DataBaseHelper.HPM_ID).toString().equals(uploadDJID)) {
						mlist.get(i).put(DataBaseHelper.DJType, 1);
						break;
					}
				}
				DJadapter.setData(mlist);
				uploadDJID = "";
			} else if (msg.what == -501) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setMessage("�л�Ʒ���㣬�Ƿ�������⣿");
				builder.setNegativeButton("��������",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								if (WebserviceImport
										.isOpenNetwork(context)) {
									progDialog = ProgressDialog.show(
											context, null, "�����ϴ�����");
									cacheThreadPool.execute(addRun2);
								} else {
									Toast.makeText(context, "����δ����",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
				
				builder.setPositiveButton("��ֹ����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context,
										DJMXCheckActivity.class);
								intent.putExtra("op_type", op_type);
								intent.putExtra("djid", uploadDJID);
								intent.putExtra("ckid", ckid);
								intent.putExtra("dh", dh);
								intent.putExtra("dacttype", dacttype);
								startActivity(intent);
								dialog.dismiss();
							}
						});
				builder.create().show();
			} else if (msg.what == -502) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setMessage("����������,���޸��̵㵥�����ϴ���");
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
								dialog.dismiss();
							}
						});
				builder.setPositiveButton("�޸�������",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
								Intent intent = new Intent(context,
										DJMXCheckActivity.class);
								intent.putExtra("op_type", op_type);
								intent.putExtra("djid", uploadDJID);
								intent.putExtra("ckid", ckid);
								intent.putExtra("dh", dh);
								intent.putExtra("dacttype", dacttype);
								startActivity(intent);
							}
						});
				builder.create().show();
			}else if(msg.what == -500){
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setMessage("��治��,��ֹ����,���޸ĵ��ݺ����ϴ���");
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				
				builder.setPositiveButton("�޸�",
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context,
										DJMXCheckActivity.class);
								intent.putExtra("op_type", op_type);
								intent.putExtra("djid", uploadDJID);
								intent.putExtra("ckid", ckid);
								intent.putExtra("dh", dh);
								intent.putExtra("dacttype", dacttype);
								startActivity(intent);
								dialog.dismiss();
							}
						});
				builder.create().show();
				
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				switch (msg.what) {
				case -503:
					builder.setMessage("�õ��ݵ�ĳ��Ʒ��ָ���ֿ��޿�棬���ǿ���ύ���Ϊ����");
					break;
				case -101:
					builder.setMessage("�ֿ���Ϣ����,����²ֿ���Ϣ�����ϴ���");
					break;
				case -102:
					builder.setMessage("�������������,����²�����Ϣ�����ϴ���");
					break;
				case -103:
					builder.setMessage("�����Ѵ���,���޸ĵ��ź����ϴ���");
					break;
				case -104:
					builder.setMessage("�������ڴ���,��ֹ���뵥��,���޸ĵ��ݺ����ϴ���");
					break;
				case -3:
					builder.setMessage("�ʺ���Ϣ��֤����");
					break;
				case -2:
					builder.setMessage("Ȩ�޲���,�����ϴ����ݡ�");
					break;
				case -10:
					builder.setMessage("������쳣��");
					break;
				default:
					builder.setMessage("�ύʧ��");
					break;
				}
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		};
	};

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch(v.getId()){
		case R.id.alluploadLayout:
			allLoaDialog = new ProgressDialog(context);
			allLoaDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			allLoaDialog.setTitle("�����ϴ�������ɸѡ�ĵ���");
			allLoaDialog.setMax(mlist.size());
			allLoaDialog.setProgress(0);
			allLoaDialog.setIndeterminate(false); 
			allLoaDialog.setIndeterminate(false);
			allLoaDialog.setCancelable(false);
			allLoaDialog.show();
			new allUpLoadAsyncTask().executeOnExecutor(cacheThreadPool,mlist);
			break;
		}
	}
	
	class allUpLoadAsyncTask extends AsyncTask<List<Map<String,Object>>, Integer, Void>{
		int progress = 0;
		int success = 0;
		int fail = 0;
		@Override
		protected Void doInBackground(List<Map<String,Object>>... params){
			// TODO �Զ����ɵķ������
			ListIterator<Map<String, Object>> it=params[0].listIterator();
			while(it.hasNext()){
				progress++;
				Map<String, Object> map = it.next();
				String thisDJid = map.get(DataBaseHelper.HPM_ID).toString();
				String[] values_movem = new String[strs1.length];
				List<Map<String, Object>> ls2 = dm_op.Gt_Movem(thisDJid, strs1);
				if (ls2 != null) {
					for (int j = 0; j < strs1.length; j++) {
						values_movem[j] = (String) ls2.get(0).get(strs1[j]);
					}
				}
				List<Map<String, Object>> lss = dm_op.Gt_Moved(thisDJid, strs3);
				String[] values_moved = new String[lss.size() * strs3.length];
				for (int j = 0; j < lss.size(); j++) {
					for (int n = 0; n < strs3.length; n++) {
						values_moved[j * strs3.length + n] = (String) lss.get(j)
								.get(strs3[n]);
					}
				}
				int flag = -1;
				try {
					String type = (String) ls2.get(0).get(DataBaseHelper.mType);
					switch (Integer.parseInt(type)) {
					case 0:
						flag = WebserviceImport.AddDJ(values_movem, strs2,
								values_moved, strs4, WebserviceHelper.AddPD,mSharedPreferences);
						break;
					case 1:
						flag = WebserviceImport.AddDJ(values_movem, strs2,
								values_moved, strs4, WebserviceHelper.AddRK,mSharedPreferences);
						break;
					case 2:
						flag = WebserviceImport.AddChuKu(values_movem, strs2,
								values_moved, strs4, false,mSharedPreferences);
						break;

					}
				} catch (Exception e) {
					flag = -1;
				}
				if (flag > 0) {
					dm_op.Update_DJtype(thisDJid, 1);// type 0����û�б��棬1�����ϴ��ͱ��汾�أ�-1�������汾��û�ϴ�
					map.put(DataBaseHelper.DJType,1);
					it.set(map);
				} else {
					dm_op.Update_DJtype(thisDJid, -1);
					map.put(DataBaseHelper.DJType,-1);
					it.set(map);
				}
				publishProgress(flag);
			}
			return null;
		}
		protected void onProgressUpdate(Integer[] values) {
			if(values[0]>0){
				success +=1;
			}else{
				fail +=1;
			}
			allLoaDialog.setProgress(GtProgress(progress, mlist.size()));
		};
		protected void onPostExecute(Void result) {
			allLoaDialog.dismiss();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if(progress>0){
				if(fail>0){
					builder.setMessage("�ϴ��ɹ�"+success+"�ʵ��ݣ��ϴ�ʧ��"+fail+"�ʵ��ݡ�����û�����Ȩ��,ʧ�ܵĵĵ����뵥���ϴ���");
				}else{
					builder.setMessage("����ȫ���ϴ��ɹ�");
				}
			}else{
				builder.setMessage("��ǰ�����£�û�п����ϴ��ĵ��ݣ�");
			}
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							mRefreshInterface = (RefreshInterface) context;
							mRefreshInterface.execute();
							dialog.dismiss();
						}
					});
			builder.create().show();
		};
	};

	
	
	private int GtProgress(int now, int total) {
		if (now == total) {
			return 100;
		} else {
			return now * 100 / total;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO �Զ����ɵķ������
		Intent i = new Intent();
		Map<String, Object> map = (Map<String, Object>) arg0.getAdapter()
				.getItem(arg2);
		i.putExtra("HPM_ID", map.get(DataBaseHelper.HPM_ID).toString());
		// from��ֵ����1�����鿴���ص���
		i.putExtra("from", 1);
		i.setClass(context, HistoryDJ_DetailsActivity.class);
		startActivity(i);
	}
	
}