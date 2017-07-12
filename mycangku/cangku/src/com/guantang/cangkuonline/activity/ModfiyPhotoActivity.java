package com.guantang.cangkuonline.activity;
/*
 * ͼƬ�������������ʾ����ɾ����
 * */

/*
 * ��ʱͼƬ������������ yyyy-MM-dd_HH-mm-ss_5λ�����
 * */
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.R.color;
import com.guantang.cangkuonline.adapter.DownLoadImageAdapter;
import com.guantang.cangkuonline.adapter.ImageDeleteAdapter;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseMethod;
import com.guantang.cangkuonline.helper.ImageHelper;
import com.guantang.cangkuonline.helper.TemporarilyImageBean;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.static_constant.PathConstant;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class ModfiyPhotoActivity extends BaseActivity implements OnClickListener,OnItemClickListener,OnCheckedChangeListener {
	private RelativeLayout all_layout, conf_layout;
	private TextView cancel, del_conf;
	private CheckBox radBtn;
	private GridView gridView;
	private ImageButton back, delete;
	private LinearLayout photo, look, select_layout;
	private int width=0,height=0;
	private String hpid = "";
	private String addImageName="";//����ͼƬ���ƣ�������ŵ����Ʋ��ܣ��ڴ����ݿ�֮ǰ��Ҫ��������
	private SharedPreferences mSharedPreferences;
	private DownLoadImageAdapter mDownLoadImageAdapter;
	private ImageDeleteAdapter mImageDeleteAdapter;
	private DataBaseMethod dm = new DataBaseMethod(this);
	private ExecutorService cacheThreadpool = Executors.newCachedThreadPool();
	private ProgressDialog NetProgressDialog;
	private int netPicNumber=0;
	private int upLoadImgNum=0;//�����ϴ�ͼƬ������
	
	/**
	 * �����ź�������ֹͼƬû���ϴ��ɹ����˳�
	 */
	private volatile Semaphore mSemaphore;
	/*
	 *�Ƿ����ɾ��״̬
	 * */
	private Boolean deleteFlagPager=false;
	/*���ڴ��ͼƬ��<ͼƬ����(������ͼ),ͼƬ(����ͼ)>
	 * */
	private List<TemporarilyImageBean> ImageList = new ArrayList<TemporarilyImageBean>();
	/*���ڴ��ͼƬ���Ƶļ���(������ͼ)
	 * */
	private List<String> ImageUrlList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_chose2);
		mSharedPreferences = getSharedPreferences(ShareprefenceBean.SHAREPREFENCE_NAME, Context.MODE_PRIVATE);
		initView();
		init();
	}
	public void initView(){
		gridView = (GridView) findViewById(R.id.gridView1);
		back = (ImageButton) findViewById(R.id.back);
		photo = (LinearLayout) findViewById(R.id.photo);
		look = (LinearLayout) findViewById(R.id.look);
		delete = (ImageButton) findViewById(R.id.delete);
		all_layout = (RelativeLayout) findViewById(R.id.all_layout);
		conf_layout = (RelativeLayout) findViewById(R.id.conf_layout);
		cancel = (TextView) findViewById(R.id.cancel);
		del_conf = (TextView) findViewById(R.id.del_conf);
		radBtn = (CheckBox) findViewById(R.id.radBtn);
		select_layout = (LinearLayout) findViewById(R.id.select_layout);
		delete.setOnClickListener(this);
		all_layout.setOnClickListener(this);
		conf_layout.setOnClickListener(this);
		cancel.setOnClickListener(this);
		del_conf.setOnClickListener(this);
		select_layout.setOnClickListener(this);
		back.setOnClickListener(this);
		look.setOnClickListener(this);
		photo.setOnClickListener(this);
		delete.setOnClickListener(this);
		gridView.setOnItemClickListener(this);
		radBtn.setOnCheckedChangeListener(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}
	
	public void init(){
		Intent intent = getIntent();
		hpid = intent.getStringExtra("hpid");
		if(mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1)==1){
			if(WebserviceImport.isOpenNetwork(this)){
				setNetAdapter();
				NetProgressDialog = ProgressDialog.show(ModfiyPhotoActivity.this, null, "���ڻ�ȡ����ͼƬ����");
				cacheThreadpool.execute(getImagenameRunnable);
			}else {
				Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void setNetAdapter(){
		mDownLoadImageAdapter = new DownLoadImageAdapter(this, mSharedPreferences,width);
		gridView.setAdapter(mDownLoadImageAdapter);
	}
	
	public void setDeleteAdapter(){
		mImageDeleteAdapter = new ImageDeleteAdapter(this, width);
		gridView.setAdapter(mImageDeleteAdapter);
	}
	
	Runnable getImagenameRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			JSONObject json;
			msg.obj=WebserviceImport_more.GetImageName(hpid, mSharedPreferences);
			msg.what=1;
			handler.sendMessage(msg);
		}
	};
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what=1) {
			case 1:
				NetProgressDialog.dismiss();
				if(WebserviceImport.isOpenNetwork(ModfiyPhotoActivity.this)){
					String[] urlArray = msg.obj.toString().split(",");
					if(urlArray.length>0){
						if(!urlArray[0].equals("")){
							netPicNumber= urlArray.length;
							for(int i=0;i<netPicNumber;i++){
								new MyAsyncTask().executeOnExecutor(cacheThreadpool, urlArray[i]);
							}
						}else{
							netPicNumber= 0;
						}
					}else{
						netPicNumber= 0;
					}
				}else{
					Toast.makeText(ModfiyPhotoActivity.this, "����δ����", Toast.LENGTH_SHORT).show();
				}
				
				break;
			}
		};
	};
	class MyAsyncTask extends AsyncTask<String, Void, String>{
		String urlString =null;
		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			urlString = params[0];
			String Base64String = WebserviceImport.GetImage("Compress_"+params[0],mSharedPreferences);
			return Base64String;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			if(result.equals("-2")){
//				netPicNumber--;
				Toast.makeText(ModfiyPhotoActivity.this, "�����ͼƬ����ѹ��,�����½���ҳ��", Toast.LENGTH_SHORT).show();
			}else if(result.equals("-3")){
//				netPicNumber--;
			}else{
				Bitmap bitmap = null;
				try {
					byte[] bitmapArray;
					bitmapArray = Base64.decode(result, Base64.DEFAULT);
					BitmapFactory.Options option = new BitmapFactory.Options();
					option.inJustDecodeBounds = true;
					option.inPreferredConfig = Bitmap.Config.RGB_565;
					option.inSampleSize = 1;
					BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, option);
					while ((option.outHeight / option.inSampleSize) > 300
							&& (option.outWidth / option.inSampleSize) > 300) {
						option.inSampleSize *= 2;
					}
					option.inPurgeable = true;
					option.inInputShareable = true;
					option.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length,option);
					if (bitmap != null) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
						int options = 100;  
						while ( baos.toByteArray().length / 1024>30) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����30kb,���ڼ���ѹ��         
							baos.reset();//����baos�����baos  
							bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
							options -= 20;//ÿ�ζ�����20  
						}  
						ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
						bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					System.gc();
				}
				ImageUrlList.add(urlString);
				ImageList.add(new TemporarilyImageBean(urlString, bitmap, false));
				mDownLoadImageAdapter.setData(ImageList);
			}
		}
	};
	
	class DeleteImageAsyncTask extends AsyncTask<String, Void, Integer>{
		String imageName="";
		@Override
		protected Integer doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			imageName=params[0];
			int i = WebserviceImport_more.DeleteImage(Integer.parseInt(hpid), params[0], mSharedPreferences);
			return i;
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			if(result<0){
				Toast.makeText(ModfiyPhotoActivity.this, "��ͼƬɾ��ʧ��", Toast.LENGTH_SHORT).show();
			}else if(result==1){
				Iterator<TemporarilyImageBean> it=ImageList.iterator();
				while(it.hasNext()){
					TemporarilyImageBean temporarilyImageBean = it.next();
					if(temporarilyImageBean.getImageName().equals(imageName)){
						File file = new File(PathConstant.PATH_photo+temporarilyImageBean.getImageName());
						if(file.exists()){
							file.delete();
						}
						//ɾ��ͼƬʱ��Ҳ���仺��ͼƬɾ��
						File file2 = new File(PathConstant.PATH_cachephoto+temporarilyImageBean.getImageName());
						if(file2.exists()){
							file2.delete();
						}
						it.remove();
						ImageUrlList.remove(temporarilyImageBean.getImageName());
						dm.deletePIC_OneUrl(hpid, imageName);
//						netPicNumber--;
						break;
					}
				}
			}
			
			del_conf.setTextColor(color.black);
			del_conf.setText("ɾ  ��");
			
			mImageDeleteAdapter.setData(ImageList);
		}
	};
	
	class AdditionImageAsyncTask extends AsyncTask<String, Void, String>{
		String oldImageName = "";
		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			String[] str = params[0].split("\\.");
			String type = "."+str[str.length-1];
			oldImageName = params[0];
			FileInputStream fis = null;
			String jsonString ="";
			try {
				File file = new File(PathConstant.PATH_photo+params[0]);
				if(!file.exists()){
            		file.createNewFile();
				}
				fis = new FileInputStream(file);
				BitmapFactory.Options opt=new BitmapFactory.Options();
				opt.inJustDecodeBounds=true;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inSampleSize = 1;
				BitmapFactory.decodeFile(PathConstant.PATH_photo+params[0],opt);
				while ((opt.outHeight / opt.inSampleSize) > width
						&& (opt.outWidth / opt.inSampleSize) > height) {
					opt.inSampleSize *= 2;
				}
				opt.inJustDecodeBounds=false;
				Bitmap bm=BitmapFactory.decodeFile(PathConstant.PATH_photo+params[0],opt);
				if(bm==null){
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					String base64string = Base64.encodeToString(bStream.toByteArray(), Base64.DEFAULT);
				    jsonString = WebserviceImport_more.AdditionImage(type, base64string, Integer.parseInt(hpid), mSharedPreferences);
				}else{
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			        bm.compress(CompressFormat.JPEG, 100, bStream); 
				    int options = 100;  
				    while ( bStream.toByteArray().length / 1024>512) {//ѭ���ж����ѹ����ͼƬ�Ƿ����512kb,���ڼ���ѹ��         
				    	bStream.reset();//����bStream�����bStream
				    	bm.compress(Bitmap.CompressFormat.JPEG, options, bStream);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
				    	options -= 10;//ÿ�ζ�����10
				    }
				    String base64string = Base64.encodeToString(bStream.toByteArray(), Base64.DEFAULT);
				    jsonString = WebserviceImport_more.AdditionImage(type, base64string, Integer.parseInt(hpid), mSharedPreferences);
					System.gc();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					fis.close();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			return jsonString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			upLoadImgNum--;
			try {
				JSONObject AdditionImageJSONObject = new JSONObject(result);
				if(AdditionImageJSONObject.getBoolean("Status")){
//					netPicNumber++;
					Toast.makeText(ModfiyPhotoActivity.this, AdditionImageJSONObject.getString("Message"), Toast.LENGTH_SHORT).show();
					String newImageName= AdditionImageJSONObject.getString("Data");
					ListIterator<String> iterator = ImageUrlList.listIterator();
					while(iterator.hasNext()){
						String ImageName = iterator.next();
						if(ImageName.equals(oldImageName)){
							ImageName=newImageName;
							iterator.set(ImageName);
							break;
						}
					}
					
					ListIterator<TemporarilyImageBean> it=ImageList.listIterator();
					while(it.hasNext()){
						TemporarilyImageBean temporarilyImageBean = it.next();
						if(temporarilyImageBean.getImageName().equals(oldImageName)){
							File file = new File(PathConstant.PATH_photo+temporarilyImageBean.getImageName());
							if(file.exists()){
								file.renameTo(new File(PathConstant.PATH_photo+newImageName));
							}
							temporarilyImageBean.setImageName(newImageName);
							it.set(temporarilyImageBean);
							break;
						}
					}
					dm.insertTB_PIC(hpid, newImageName);
					mDownLoadImageAdapter.setData(ImageList);
				}else{
					Toast.makeText(ModfiyPhotoActivity.this, AdditionImageJSONObject.getString("Message"), Toast.LENGTH_SHORT).show();
					ListIterator<String> iterator = ImageUrlList.listIterator();
					while(iterator.hasNext()){
						if(iterator.next().equals(oldImageName)){
							iterator.remove();
							break;
						}
					}
					
					ListIterator<TemporarilyImageBean> it=ImageList.listIterator();
					while(it.hasNext()){
						TemporarilyImageBean temporarilyImageBean = it.next();
						if(temporarilyImageBean.getImageName().equals(oldImageName)){
							File file = new File(PathConstant.PATH_photo+temporarilyImageBean.getImageName());
							if(file.exists()){
								file.delete();
							}
							it.remove();
							break;
						}
					}
					mDownLoadImageAdapter.setData(ImageList);
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch(v.getId()){
		case R.id.back:
//			if(ImageList.size()==netPicNumber){
			if(upLoadImgNum<=0){
				deletePic(PathConstant.PATH_cachephoto);
				finish();
			}else{
				Toast.makeText(this, "�����ϴ�ͼƬ,�����ĵȴ�", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.delete:
			if(upLoadImgNum<=0){//֤��ͼƬ�Ѿ��������ˣ����Խ���ͼƬɾ������
				if(!deleteFlagPager){
					deleteFlagPager = true;
					all_layout.setVisibility(View.VISIBLE);
					conf_layout.setVisibility(View.VISIBLE);
					select_layout.setVisibility(View.GONE);
					delete.setVisibility(View.GONE);
					setDeleteAdapter();
					mImageDeleteAdapter.setData(ImageList);
				}else{
					deleteFlagPager = false;
					all_layout.setVisibility(View.INVISIBLE);
					conf_layout.setVisibility(View.GONE);
					select_layout.setVisibility(View.VISIBLE);
					delete.setVisibility(View.VISIBLE);
					setNetAdapter();
					mDownLoadImageAdapter.setData(ImageList);
				}
			}else {
				Toast.makeText(this, "�����ϴ�ͼƬ�����ܽ���ɾ������", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.cancel:
			deleteFlagPager = false;
			all_layout.setVisibility(View.INVISIBLE);
			conf_layout.setVisibility(View.GONE);
			select_layout.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
			
			radBtn.setChecked(false);
			for(int i=0;i<ImageList.size();i++){
				ImageList.get(i).setDeletethis(false);
			}
			setNetAdapter();
			mDownLoadImageAdapter.setData(ImageList);
			break;
		case R.id.del_conf:
			if(mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1)==1){
				if(WebserviceImport.isOpenNetwork(ModfiyPhotoActivity.this)){
					Iterator<TemporarilyImageBean> it=ImageList.iterator();
					while(it.hasNext()){
						TemporarilyImageBean temporarilyImageBean = it.next();
						if(temporarilyImageBean.getDeletethis()){
							new DeleteImageAsyncTask().executeOnExecutor(cacheThreadpool, temporarilyImageBean.getImageName());
						}
					}
				}else{
					Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
				}
				
			}else if(mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1)==-1){
				Iterator<TemporarilyImageBean> it=ImageList.iterator();
				while(it.hasNext()){
					TemporarilyImageBean temporarilyImageBean = it.next();
					if(temporarilyImageBean.getDeletethis()){
						File file = new File(PathConstant.PATH_photo+temporarilyImageBean.getImageName());
						if(file.exists()){
							file.delete();
						}
						it.remove();
						ImageUrlList.remove(temporarilyImageBean.getImageName());
					}
				}
			}
			setDeleteAdapter();
			mImageDeleteAdapter.setData(ImageList);
			break;
		case R.id.all_layout:
			if(radBtn.isChecked()){
				radBtn.setChecked(false);
			}else{
				radBtn.setChecked(true);
			}
			break;
		case R.id.photo:
			if(ImageList.size()<4){
				addImageName=ImageHelper.TemporarilyImageName()+".jpg";
				File file = new File(PathConstant.PATH_photo + addImageName);
				if(file.exists()){
					file.delete();
				}
				// ����ϵͳ���๦��
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
				startActivityForResult(intent, 1);
			}else{
				Toast.makeText(this, "ÿ����Ʒֻ������4��ͼƬ", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.look:
			if(ImageList.size()<4){
				addImageName=ImageHelper.TemporarilyImageName();
				showChooser();
			}else{
				Toast.makeText(this, "ÿ����Ʒֻ������4��ͼƬ", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	
	private void showChooser() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		try {
			startActivityForResult(intent, 2);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "�밲װͼƬ�����", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void CountDeleteImage(){
		int count = 0;
		for(int i=0;i<ImageList.size();i++){
			if(ImageList.get(i).getDeletethis()){
				count += 1;
			}
			if(count>0){
				del_conf.setTextColor(color.ziti1);
				del_conf.setText("ɾ  �� ("+String.valueOf(count)+")");
			}else{
				del_conf.setTextColor(color.black);
				del_conf.setText("ɾ  ��");
			}
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO �Զ����ɵķ������
		if(!isChecked){
			for(int i=0;i<ImageList.size();i++){
				ImageList.get(i).setDeletethis(false);
			}
			mImageDeleteAdapter.setData(ImageList);
			CountDeleteImage();
		}else{
			for(int i=0;i<ImageList.size();i++){
				ImageList.get(i).setDeletethis(true);
			}
			mImageDeleteAdapter.setData(ImageList);
			CountDeleteImage();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO �Զ����ɵķ������
		TemporarilyImageBean imageBean=(TemporarilyImageBean) arg0.getAdapter().getItem(arg2);
		if(deleteFlagPager){
			if(imageBean.getDeletethis()){
				ImageList.get(arg2).setDeletethis(false);
			}else{
				ImageList.get(arg2).setDeletethis(true);
			}
			mImageDeleteAdapter.setData(ImageList);
			CountDeleteImage();
		}else if(!deleteFlagPager){
			Intent intent = new Intent(this,ShowImagePagerActivity.class);
			intent.putExtra("ImageNameList",(Serializable) ImageUrlList);
			intent.putExtra("hpid", hpid);
			intent.putExtra("nowShowImage", imageBean.getImageName());
			intent.putExtra("LocalOrNet", mSharedPreferences.getInt(ShareprefenceBean.IS_LOGIN, -1));//-1�������ʱ���ͼƬ��1������������ͼƬ
			startActivity(intent);
		}
	}
	
	private Bitmap getCompressBitmap(String path){
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		option.inPreferredConfig = Bitmap.Config.RGB_565;
		option.inSampleSize = 1;
		BitmapFactory.decodeFile(path, option);
		while ((option.outHeight / option.inSampleSize) > 300
				&& (option.outWidth / option.inSampleSize) > 300) {
			option.inSampleSize *= 2;
		}
		option.inPurgeable = true;
		option.inInputShareable = true;
		option.inJustDecodeBounds = false;
		Bitmap bm =null;
		try{
			bm = BitmapFactory.decodeFile(path, option);
		}catch(OutOfMemoryError e){
			Toast.makeText(this, "�ֻ������ڴ治�㣬�޷���ʾ����ͼƬ", Toast.LENGTH_SHORT).show();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;
		while (baos.toByteArray().length / 1024 > 30) { // ѭ���ж����ѹ����ͼƬ�Ƿ����30kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10%
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(
				baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap =null;
		try{
			bitmap = BitmapFactory
					.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.photo_del);
			}
		}catch(OutOfMemoryError e){
			Toast.makeText(this, "�ֻ������ڴ治�㣬����ͼѹ��ͼƬ��ʾ", Toast.LENGTH_SHORT).show();
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int opt = 100;
			while (baos.toByteArray().length / 1024 > 20) {
				baos.reset();
				bm.compress(Bitmap.CompressFormat.JPEG, opt, baos);
				options -= 10;
			}
		}finally{
			System.gc();
		}
		return bitmap;
	}
	
	public int CopyFile(String fromFile, String toFile) {
		File file = new File(fromFile);
		if(!file.exists()){
			return -1;
		}
		
		InputStream fosfrom =null;
		OutputStream fosto =null;
		try {
			fosfrom = new FileInputStream(fromFile);
			fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) != -1) {
				fosto.write(bt, 0, c);
			}
			
			return 0;
		} catch (Exception ex) {
			return -1;
		} finally{
			try {
				fosfrom.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			try {
				fosto.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(requestCode==1){
				if(WebserviceImport.isOpenNetwork(this)){
					if(!addImageName.equals("")){
						File file = new File(PathConstant.PATH_photo+ addImageName);
						if(file.exists()){
							ImageList.add(new TemporarilyImageBean(addImageName, getCompressBitmap(PathConstant.PATH_photo+ addImageName),false));
							ImageUrlList.add(addImageName);
							mDownLoadImageAdapter.setData(ImageList);
							upLoadImgNum++;
							mSemaphore = new Semaphore(1);
							new AdditionImageAsyncTask().executeOnExecutor(cacheThreadpool, addImageName);
						}else{
							Toast.makeText(this, "����ʧ��", Toast.LENGTH_SHORT).show();
						}
					}
				}else{
					Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
				}
			}else if(requestCode==2){
				Uri uri = data.getData();
				if(uri != null){//��ͬ�ֻ���װ��data��ͬ���е��Ƿŵ�ͼƬ·���е���ͼƬ
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					Cursor cursor = this.getContentResolver().query(uri, filePathColumn,null, null, null);
					if(cursor!=null){
						if(cursor.moveToFirst()){
				            String path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
				            cursor.close();
							String[] urlArray = path.split("\\.");
							if(!addImageName.equals("")){
								if(urlArray.length>1){//����ͼƬ��ʽ����
									addImageName = addImageName+"."+urlArray[urlArray.length-1];
								}
								if(CopyFile(path, PathConstant.PATH_photo+addImageName)==0){
									File file = new File(PathConstant.PATH_photo+ addImageName);
									if(file.exists()){
										if(WebserviceImport.isOpenNetwork(this)){
											ImageList.add(new TemporarilyImageBean(addImageName, getCompressBitmap(PathConstant.PATH_photo+ addImageName),false));
											ImageUrlList.add(addImageName);
											mDownLoadImageAdapter.setData(ImageList);
											upLoadImgNum++;
											new AdditionImageAsyncTask().executeOnExecutor(cacheThreadpool, addImageName);
										}else{
											Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
										}
									}else{
										Toast.makeText(this, "ͼƬѡȡʧ��", Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(this, "ͼƬѡȡʧ��", Toast.LENGTH_SHORT).show();
								}
							}
						}
					}else{
						Toast.makeText(this, "ͼƬѡȡʧ��", Toast.LENGTH_SHORT).show();
					}
				}else{
					Bundle bundle = data.getExtras();
					if (bundle != null) {                 
			               Bitmap  photo = (Bitmap) bundle.get("data"); //get bitmap  
			               //spath :����ͼƬȡ�����ֺ�·����������      
			               if(saveImage(photo, PathConstant.PATH_photo+ addImageName)){
			            	   File file = new File(PathConstant.PATH_photo+ addImageName);
			            	   if(file.exists()){
			            		   ImageList.add(new TemporarilyImageBean(addImageName, getCompressBitmap(PathConstant.PATH_photo+ addImageName+".jpg"),false));
				            	   ImageUrlList.add(addImageName);
				            	   mDownLoadImageAdapter.setData(ImageList);
				            	   upLoadImgNum++;
				            	   new AdditionImageAsyncTask().executeOnExecutor(cacheThreadpool, addImageName);
			            	   }else{
			            		   Toast.makeText(this, "ͼƬѡȡʧ��", Toast.LENGTH_SHORT).show();
			            	   }
			               }else{
			            	   Toast.makeText(this, "ͼƬѡȡʧ��", Toast.LENGTH_SHORT).show();
			               }
					}
				}
			}
			addImageName="";
		}
	}
	
	public boolean saveImage(Bitmap photo, String spath){
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(spath+".jpg", false));
			photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}finally{
			try {
				bos.flush();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}  
            try {
				bos.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		} 
		return true;
	}
	
	public void deletePic(String fileName){
    	File file = new File(fileName);
    	File[] fileList = file.listFiles();
    	if(fileList!=null && fileList.length>0){
    		for(File f : fileList){
        		if(f.isDirectory()){
        			deletePic(f.getName());//ͼƬ�ļ�����û���ļ��У�һ�㲻��ִ�е�
        		}else{
        			f.delete();
        		}
        	}
    	}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO �Զ����ɵķ������
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			if(upLoadImgNum<=0){
				deletePic(PathConstant.PATH_cachephoto);
				finish();
				return true;
			}else{
				Toast.makeText(this, "�����ϴ�ͼƬ,�����ĵȴ�", Toast.LENGTH_SHORT).show();
				return false;
			}
		}else{
			return true;
		}
//		return super.onKeyDown(keyCode, event);
	}
}