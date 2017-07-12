package com.guantang.cangkuonline.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.activity.FinalMainActivity;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseImport;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.helper.GenerateIMEI;
import com.guantang.cangkuonline.helper.RightsHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.static_constant.PathConstant;
import com.guantang.cangkuonline.webservice.WebserviceHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.umeng.fb.FeedbackAgent;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

/*
 * Java���Ѿ��ṩ��һ���ӿ�Thread.UncaughtExceptionHandler��������ʱ���쳣���д�����
 * */
//implements UncaughtExceptionHandler
public class MyApplication extends Application implements UncaughtExceptionHandler{
	private CheckBox hpxinxiCheckBox,hpleixinCheckBox,dwxinxiCheckBox,canshuxinxiCheckBox,cangkuxinxiCheckBox;
	
	private int numPoint=8,djPoint=2,jePoint=2;
	private DataBaseOperateMethod dm_op;
	private String[] str6={DataBaseHelper.GID,DataBaseHelper.ItemID,DataBaseHelper.ItemV,DataBaseHelper.Ord};
	private SharedPreferences mSharedPreferences;
	/*�ֻ�����
	 * **/
	private String IMEI="";
	/*�Ƿ���ʾѯ�ʸ���dialog,�ڱ��δ�app��ʱ���Ѿ������ˣ�֮��Ͳ�����ʾdialog.
	 * */
	private boolean showupDataFlag = true;

	private Stack<Activity> activityStack;
	
	private static MyApplication mInstance ;
	public static MyApplication getInstance() {
		return mInstance;
	}
	
	@Override
	public void onCreate() {// ���򴴽���ʱ��ִ��
		// TODO �Զ����ɵķ������
		super.onCreate();
		mInstance = this;
		activityStack = new Stack<Activity>();
		
//		mInstance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//			
//			@Override
//			public void onActivityStopped(Activity activity) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivityStarted(Activity activity) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivityResumed(Activity activity) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivityPaused(Activity activity) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivityDestroyed(Activity activity) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		mSharedPreferences = getSharedPreferences(ShareprefenceBean.SHAREPREFENCE_NAME, Context.MODE_PRIVATE);
		Thread.setDefaultUncaughtExceptionHandler(this);
		dm_op = new DataBaseOperateMethod(getApplicationContext());
//		getDataBaseImport();
		createDirs();
		setALLPoint();
		generateIEMI();
		JPushInterface.setDebugMode(false);
		JPushInterface.init(this);
		JPushInterface.stopPush(this);
		FeedbackAPI.init(mInstance, "23514936");
	}
	
	@Override
	public void onTerminate() {// ������ֹ��ʱ��ִ��
		// TODO �Զ����ɵķ������
		super.onTerminate();
		new Thread(ExitloadRun).start();
	}
	@Override
	public void onLowMemory() {// ���ڴ��ʱ��ִ��
		// TODO �Զ����ɵķ������
		super.onLowMemory();
	}
	@Override
	public void onTrimMemory(int level) {// �������ڴ�������ʱ��ִ��
		// TODO �Զ����ɵķ������
		super.onTrimMemory(level);
		new Thread(ExitloadRun).start();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {//���øı�ʱ�����������
		// TODO �Զ����ɵķ������
		super.onConfigurationChanged(newConfig);
	}
	
	
//----------------------------------------------------------------------------------------------
	
	public void pushActivityToStack(Activity activity){
		activityStack.push(activity);
	}
	
	public boolean removeActivityToStack(Activity activity){
		
		return activityStack.size()>0 ? activityStack.remove(activity):false;
	}
	
	public Activity peekActivityToStack(){
		return activityStack.size()>0 ? activityStack.peek():null;
	}
	
	public SharedPreferences getSharedPreferences(){
		return mSharedPreferences;
	}
	
	public void createDirs() {
		File file;
		file = new File(PathConstant.PATH_photo);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(PathConstant.PATH_backup);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(PathConstant.PATH_system);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(PathConstant.PATH_cachephoto);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public void setALLPoint(){
		List<Map<String, Object>> mlist = dm_op.Gt_ConfByGID("������Ϣ", str6);
		if(!mlist.isEmpty()){
			Iterator<Map<String, Object>> it = mlist.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				String itemIDString = map.get(DataBaseHelper.ItemID).toString();
				if(itemIDString.equals("����С��λ��")){
					if(map.get(DataBaseHelper.ItemV)!=null){
						if(map.get(DataBaseHelper.ItemV).toString().matches("\\d+?")){
							numPoint=Integer.parseInt(map.get(DataBaseHelper.ItemV).toString());
						}else{
							numPoint=8;
						}
					}
				}else if(itemIDString.equals("����С��λ��")){
					if(map.get(DataBaseHelper.ItemV)!=null){
						if(map.get(DataBaseHelper.ItemV).toString().matches("\\d+?")){
							djPoint=Integer.parseInt(map.get(DataBaseHelper.ItemV).toString());
						}else{
							djPoint=2;
						}
					}
				}else if(itemIDString.equals("С��λ��")){
					if(map.get(DataBaseHelper.ItemV)!=null){
						if(map.get(DataBaseHelper.ItemV).toString().matches("\\d+?")){
							jePoint=Integer.parseInt(map.get(DataBaseHelper.ItemV).toString());
						}else{
							jePoint=2;
						}
					}
				}
			}
		}
	}
	
	public int getNumPoint(){
		return numPoint;
	}
	public int getDjPoint(){
		return djPoint;
	}
	public int getJePoint(){
		return jePoint;
	}
	
	public boolean getshowupDataFlag(){
		return showupDataFlag;
	}
	public void setshowupDataFlag(boolean flag){
		showupDataFlag = flag;
	}
	
//	/*�������ݿ����
//	 * **/
//	public DataBaseImport getDataBaseImport(){
//		if(data==null){
//			data = DataBaseImport.getInstance(getApplicationContext());
//		}
//		return data;
//	}
	
	/*��ȡapp�汾��
	 * */
	public int getVisionCode() {
		PackageManager manager = this.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int version = info.versionCode;
		return version;
	}
	
	/*��ȡapp�汾����
	 * */
	public String getVisionName() {
		PackageManager manager = this.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info.versionName;
	}
	
	/**
	 * ��ȡ�������ֻ�����
	 * */
	public void generateIEMI(){
		IMEI = ((TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		if(IMEI==null || IMEI.equals("")){
			File file = new File(PathConstant.PATH_system+"Customchuan.gt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			BufferedReader reader = null;
			BufferedWriter writer = null;
			try {
				reader = new BufferedReader(new FileReader(PathConstant.PATH_system+"Customchuan.gt"));
				writer  = new BufferedWriter(new FileWriter(PathConstant.PATH_system+"Customchuan.gt"));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while((line=reader.readLine())!=null){
					sb.append(line);
				}
				IMEI = sb.toString();
			    if(IMEI.equals("") || IMEI==null){
			    	IMEI=GenerateIMEI.produceIMEI();
			    	writer.write(IMEI.toCharArray());
			    	writer.flush();
			    }
			}catch (FileNotFoundException e) {  
			    e.printStackTrace();  
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				try {
					writer.close();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public String getIEMI(){
		if(IMEI==null || IMEI.equals("")){
			generateIEMI();
		}
		return IMEI;
	}
	
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {// ����������Դ����� �������ʱ������������
		// TODO �Զ����ɵķ������
		Toast.makeText(getApplicationContext(), "�������������һֱ�ھ���������������ӭ���ķ�����", Toast.LENGTH_SHORT).show();
		new Thread(ExitloadRun).start();
	}
	 
	Runnable ExitloadRun = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			WebserviceImport.delMEI(MyApplication.getInstance().getIEMI(),mSharedPreferences);
			JPushInterface.stopPush(getApplicationContext());
		} 
	 };
}