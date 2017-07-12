package com.guantang.cangkuonline.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.database.DataBaseHelper;
import com.guantang.cangkuonline.database.DataBaseOperateMethod;
import com.guantang.cangkuonline.helper.AES;
import com.guantang.cangkuonline.helper.AESUtils;
import com.guantang.cangkuonline.helper.DecimalsHelper;
import com.guantang.cangkuonline.helper.DimensionConvert;
import com.guantang.cangkuonline.helper.EncodingHandler;
import com.guantang.cangkuonline.helper.ImageHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.static_constant.PathConstant;
import com.guantang.cangkuonline.webservice.WebserviceHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryDJ_DetailsActivity extends BaseActivity implements OnClickListener, OnScrollListener {
	private LinearLayout layout1, layout2, typelayout, bottom_layout, bzLayout;
	private TextView dhTxtView, dateTxtView, lrsjTxtView, dwTxtView, jbrTxtView, text1TxtView, text2TxtView,
			znumTxtView, zjeTxtView, typeTxtView, ckTxtView, depTxtView, bzTextView, shareTxtView;

	private ImageButton backImgBtn;
	private ListView mListView;
	private String djid = "", bztxt = "", op_type, dh;
	private int from = 0;// 1 代表从本地数据库获取，0代表从服务端获取
	private DataBaseOperateMethod dm_op = new DataBaseOperateMethod(this);
	private List<Map<String, Object>> DJList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> MXList = new ArrayList<Map<String, Object>>();
	private String[] str1 = { DataBaseHelper.MVDH, DataBaseHelper.MVDT, DataBaseHelper.DWName, DataBaseHelper.JBR,
			DataBaseHelper.BZ, DataBaseHelper.mType, DataBaseHelper.actType, DataBaseHelper.DepName,
			DataBaseHelper.DepID, DataBaseHelper.CKMC, DataBaseHelper.CKID, DataBaseHelper.Lrr, DataBaseHelper.Lrsj,
			DataBaseHelper.DWID, DataBaseHelper.Details, DataBaseHelper.HPzj };
	private String[] str11 = { DataBaseHelper.HPBM, DataBaseHelper.HPMC, DataBaseHelper.GGXH, DataBaseHelper.JLDW,
			DataBaseHelper.MSL, DataBaseHelper.ZJ, DataBaseHelper.DJ };
	private String[] str2 = { DataBaseHelper.HPBM, DataBaseHelper.HPMC, DataBaseHelper.GGXH, DataBaseHelper.JLDW,
			DataBaseHelper.MSL, DataBaseHelper.ZJ, DataBaseHelper.DJ, DataBaseHelper.HPD_ID };
	private String[] strs2 = { "HPBM", "HPMC", "GGXH", "JLDW", "msl", "zj", "dj", "hpd_id" };
	private String[] str4 = { DataBaseHelper.HPBM, DataBaseHelper.HPMC, DataBaseHelper.GGXH, DataBaseHelper.JLDW,
			DataBaseHelper.BTKC, DataBaseHelper.ATKC, "profit", "lose" };
	private String[] strs4 = { "HPBM", "HPMC", "GGXH", "JLDW", "ZMSL", "PDSL", "YSL", "KSL" };
	private String[] str7 = { DataBaseHelper.HPBM, DataBaseHelper.HPMC, DataBaseHelper.GGXH, DataBaseHelper.JLDW,
			DataBaseHelper.MSL, DataBaseHelper.HPD_ID, DataBaseHelper.BTKC, DataBaseHelper.ATKC,
			DataBaseHelper.MVDirect };
	private ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
	private SharedPreferences mSharedPreferences;
	private ProgressDialog progressDialog;
	// private Moved_DetailsAdapter moved_DetailsAdapter;

	private Tencent mTencent;
	private IWXAPI wxapi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dj_details);
		initView();
		init();
		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		mTencent = Tencent.createInstance("1102034999", this.getApplicationContext());
		wxapi = WXAPIFactory.createWXAPI(this.getApplicationContext(), "wx340122b85575be10", true);
		wxapi.registerApp("wx340122b85575be10");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		wxapi.unregisterApp();
	}

	public void initView() {
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		typelayout = (LinearLayout) findViewById(R.id.typelayout);
		bottom_layout = (LinearLayout) findViewById(R.id.layout_bottom);
		dhTxtView = (TextView) findViewById(R.id.dh);
		typeTxtView = (TextView) findViewById(R.id.type);
		dateTxtView = (TextView) findViewById(R.id.date);
		lrsjTxtView = (TextView) findViewById(R.id.lrsj);
		dwTxtView = (TextView) findViewById(R.id.dw);
		depTxtView = (TextView) findViewById(R.id.dep);
		ckTxtView = (TextView) findViewById(R.id.ck);
		jbrTxtView = (TextView) findViewById(R.id.jbr);
		text1TxtView = (TextView) findViewById(R.id.text1);
		text2TxtView = (TextView) findViewById(R.id.text2);
		znumTxtView = (TextView) findViewById(R.id.zs);
		zjeTxtView = (TextView) findViewById(R.id.zje);
		backImgBtn = (ImageButton) findViewById(R.id.back);
		mListView = (ListView) findViewById(R.id.list);
		bzLayout = (LinearLayout) findViewById(R.id.bzlayout);
		bzTextView = (TextView) findViewById(R.id.bz);
		shareTxtView = (TextView) findViewById(R.id.shareTxtView);

		bzLayout.setOnClickListener(this);
		backImgBtn.setOnClickListener(this);
		mListView.setOnScrollListener(this);
		shareTxtView.setOnClickListener(this);

		mSharedPreferences = getSharedPreferences(ShareprefenceBean.SHAREPREFENCE_NAME, Context.MODE_PRIVATE);

	}

	public void init() {
		Intent intent = getIntent();
		djid = intent.getStringExtra("HPM_ID");
		from = intent.getIntExtra("from", 0);
		switch (from) {
		case 0:
			Bundle bundle = intent.getExtras();
			DJList = (List<Map<String, Object>>) bundle.getSerializable("DJDetails");
			break;
		case 1:
			DJList = dm_op.Gt_Movem(djid, str1);
			break;
		}
		Map<String, Object> map = DJList.get(0);
		bztxt = map.get(DataBaseHelper.BZ).toString();
		op_type = map.get(DataBaseHelper.mType).toString();
		if (op_type.equals("0")) {
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
			text1TxtView.setText("盘盈数量");
			text2TxtView.setText("盘亏数量");
			typelayout.setVisibility(View.GONE);
			dh = map.get(DataBaseHelper.MVDH).toString();
			if (dh.equals("anyType{}")) {
				dhTxtView.setText("服务端自动编号");
			} else {
				dhTxtView.setText(dh);
			}
			if (map.get(DataBaseHelper.CKMC).toString().equals("anyType{}")) {
				ckTxtView.setText("");
			} else {
				ckTxtView.setText(map.get(DataBaseHelper.CKMC).toString());
			}
			if (map.get(DataBaseHelper.JBR).toString().equals("anyType{}")) {
				jbrTxtView.setText("");
			} else {
				jbrTxtView.setText(map.get(DataBaseHelper.JBR).toString());
			}
			if (map.get(DataBaseHelper.MVDT).toString().equals("anyType{}")) {
				dateTxtView.setText("");
			} else {
				dateTxtView.setText(map.get(DataBaseHelper.MVDT).toString().replace("T", " ").subSequence(0, 10));
			}
			if (map.get(DataBaseHelper.Lrsj).toString().equals("anyType{}")) {
				lrsjTxtView.setText("");
			} else {
				lrsjTxtView.setText(map.get(DataBaseHelper.Lrsj).toString().replace("T", " "));
			}
			if (bztxt.equals("anyType{}")) {
				bzTextView.setText("");
			} else {
				bzTextView.setText(bztxt);
			}
		} else {
			layout2.setVisibility(View.GONE);
			layout1.setVisibility(View.VISIBLE);
			typelayout.setVisibility(View.VISIBLE);
			text1TxtView.setText("总数量");
			text2TxtView.setText("总金额");
			dh = map.get(DataBaseHelper.MVDH).toString();
			if (map.get(DataBaseHelper.MVDH) == null || map.get(DataBaseHelper.MVDH).toString().equals("anyType{}")) {
				dhTxtView.setText("服务端自动编号");
			} else {
				dhTxtView.setText(dh);
			}
			if (map.get(DataBaseHelper.CKMC) == null || map.get(DataBaseHelper.CKMC).toString().equals("anyType{}")) {
				ckTxtView.setText("");
			} else {
				ckTxtView.setText(map.get(DataBaseHelper.CKMC).toString());
			}
			if (map.get(DataBaseHelper.JBR) == null || map.get(DataBaseHelper.JBR).toString().equals("anyType{}")) {
				jbrTxtView.setText("");
			} else {
				jbrTxtView.setText(map.get(DataBaseHelper.JBR).toString());
			}
			if (map.get(DataBaseHelper.MVDT) == null || map.get(DataBaseHelper.MVDT).toString().equals("anyType{}")) {
				dateTxtView.setText("");
			} else {
				dateTxtView.setText(map.get(DataBaseHelper.MVDT).toString().replace("T", " ").subSequence(0, 10));
			}
			if (map.get(DataBaseHelper.Lrsj) == null || map.get(DataBaseHelper.Lrsj).toString().equals("anyType{}")) {
				lrsjTxtView.setText("");
			} else {
				lrsjTxtView.setText(map.get(DataBaseHelper.Lrsj).toString().replace("T", " "));
			}
			if (map.get(DataBaseHelper.DepName) == null
					|| map.get(DataBaseHelper.DepName).toString().equals("anyType{}")) {
				depTxtView.setText("");
			} else {
				depTxtView.setText(map.get(DataBaseHelper.DepName).toString());
			}
			if (map.get(DataBaseHelper.actType) == null
					|| map.get(DataBaseHelper.actType).toString().equals("anyType{}")) {
				typeTxtView.setText("");
			} else {
				typeTxtView.setText(map.get(DataBaseHelper.actType).toString());
			}
			if (map.get(DataBaseHelper.DWName) == null
					|| map.get(DataBaseHelper.DWName).toString().equals("anyType{}")) {
				dwTxtView.setText("");
			} else {
				dwTxtView.setText(map.get(DataBaseHelper.DWName).toString());
			}
			if (bztxt.equals("anyType{}")) {
				bzTextView.setText("");
			} else {
				bzTextView.setText(bztxt);
			}
			bottom_layout.setVisibility(View.VISIBLE);
		}
		switch (from) {
		case 0:// 服务端单据明细
			progressDialog = ProgressDialog.show(this, null, "正在加载数据");
			cacheThreadPool.execute(addmovedRunnable);
			if (op_type.endsWith("1")||op_type.endsWith("2")) {
				shareTxtView.setVisibility(View.VISIBLE);//只有在线状态下的出入库单可以分享
			}else{
				shareTxtView.setVisibility(View.GONE);//只有在线状态下的出入库单可以分享
			}
			break;
		case 1:// 本地单据明细
			shareTxtView.setVisibility(View.GONE);//只有在线状态下的出入库单可以分享
			if (map.get(DataBaseHelper.mType).toString().equals("0")) {
				MXList = dm_op.Gt_Moved_details(djid, str7);
				double ying = 0, kui = 0;
				for (int i = 0; i < MXList.size(); i++) {
					if (MXList.get(i).get(DataBaseHelper.MVDirect).equals("1")) {
						MXList.get(i).put("profit", (String) MXList.get(i).get(DataBaseHelper.MSL));
						MXList.get(i).put("lose", "");
						ying += Float.parseFloat(MXList.get(i).get("profit").toString());
					} else {
						MXList.get(i).put("profit", "");
						MXList.get(i).put("lose", (String) MXList.get(i).get(DataBaseHelper.MSL));
						kui += Float.parseFloat(MXList.get(i).get("lose").toString());
					}
				}
				znumTxtView.setText(DecimalsHelper.Transfloat(ying, MyApplication.getInstance().getNumPoint()));
				zjeTxtView.setText(DecimalsHelper.Transfloat(kui, MyApplication.getInstance().getJePoint()));
				setAdapter(MXList, 0);// 0 代表是盘点单据
			} else {
				MXList = dm_op.Gt_Moved_details(djid, str2);
				setAdapter(MXList, 1);// 1、2 代表不是盘点单据
				znumTxtView.setText(dm_op.Gt_Moved_znum(djid));
				zjeTxtView.setText(dm_op.Gt_Moved_zje(djid));
			}
			break;
		}
	}

	public void setAdapter(List<Map<String, Object>> list, int op_type) {
		// moved_DetailsAdapter = new Moved_DetailsAdapter(this, op_type);
		// moved_DetailsAdapter.setData(list);
		// mListView.setAdapter(moved_DetailsAdapter);

		if (op_type == 0) {
			SimpleAdapter listItemAdapter = new SimpleAdapter(this, list, R.layout.djdetail_item_check, str4,
					new int[] { R.id.bm, R.id.mc, R.id.gg, R.id.jldw, R.id.znum, R.id.snum, R.id.profit, R.id.lose });
			mListView.setAdapter(listItemAdapter);
		} else {
			SimpleAdapter listItemAdapter = new SimpleAdapter(this, list, R.layout.djdetail_item, str11,
					new int[] { R.id.bm, R.id.mc, R.id.gg, R.id.jldw, R.id.num, R.id.zj, R.id.dj });
			mListView.setAdapter(listItemAdapter);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.back:
			File file = new File(PathConstant.PATH_cachephoto);
			if (file.exists()) {
				File[] fileList = file.listFiles();
				for (File f : fileList) {
					f.delete();
				}
			}
			finish();
			break;
		case R.id.bzlayout:
			View view = LayoutInflater.from(this).inflate(R.layout.simple_textview, null);
			TextView textView = (TextView) view.findViewById(R.id.simple_textview);
			if (bztxt.equals("anyType{}")) {
				textView.setText("备注：\n\t" + "");
			} else {
				textView.setText("备注：\n\t" + bztxt);
			}

			PopupWindow popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// 这个是为了点击“返回Back”也能使其消失.
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// 使其聚焦
			popupWindow.setFocusable(true);
			// 设置允许在外点击消失
			popupWindow.setOutsideTouchable(true);
			// 刷新状态
			popupWindow.update();
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			popupWindow.showAtLocation(view, Gravity.CENTER, location[0], location[1] - popupWindow.getHeight());
			break;

		case R.id.shareTxtView:
			try {
				// 生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
				final Bitmap qrcodeBitmap = EncodingHandler.createQRCode(URLMakeUp(), 500);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LinearLayout mLinearLayout = new LinearLayout(this);
				mLinearLayout.setOrientation(LinearLayout.VERTICAL);
				TextView mTextView = new TextView(this);
				mTextView.setText("已为你生成单据的二维码，\n赶快分享给朋友吧！");
				mTextView.setPadding(40, 30, 30, 40);
				mTextView.setTextColor(Color.parseColor("#333333"));
				mTextView.setTextSize(16);
				ImageView mImageView = new ImageView(this);
				mImageView.setPadding(30, 30, 30, 30);
				mImageView.setImageBitmap(qrcodeBitmap);
				mLinearLayout.addView(mTextView);
				mLinearLayout.addView(mImageView);
				builder.setView(mLinearLayout);
				builder.setPositiveButton("qq分享", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String path = saveErWeiMa(qrcodeBitmap);
						if (path != null) {
							onQQShare(path);
						}
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("微信分享", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String path = saveErWeiMa(qrcodeBitmap);
						if (path != null) {
							onWeiXinShare(path);
						}
						dialog.dismiss();
					}
				});
				builder.create().show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	private String URLMakeUp() {
		StringBuilder sb = new StringBuilder();
		sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, ""));
		sb.append("views/WebAPP/CkQRCode.html?");
		if (op_type.equals("1")) {
			sb.append("op=CK&");
		} else if (op_type.equals("2")) {
			sb.append("op=RK&");
		}
		try {
			StringBuilder sb1 = new StringBuilder();
			if (MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 1) == 1) {
				if(mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户
					sb1.append("sob=" + URLEncoder.encode("测试", "utf-8"));
				}else{
					sb1.append("sob=" + URLEncoder.encode(MyApplication.getInstance().getSharedPreferences()
							.getString(ShareprefenceBean.DWNAME_LOGIN, "测试"), "utf-8"));
				}
				
			} else {
				sb1.append("sob=" + URLEncoder.encode(MyApplication.getInstance().getSharedPreferences()
						.getString(ShareprefenceBean.IDN_ALONE_URL, ""), "utf-8"));
			}
			sb1.append("&sn=" + URLEncoder.encode(dh, "utf-8"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			sb1.append("&dt="+format.format(System.currentTimeMillis()));
			
			String key = "27CA18FDA024A14E";
			try {
				sb.append("Param="+AES.Encrypt(sb1.toString(), key));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(sb.toString());
		String sss= m.replaceAll("");
		return sss;
	}

	private String saveErWeiMa(Bitmap bitmap) {
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(PathConstant.PATH_cachephoto + ImageHelper.TemporarilyImageName() + ".png");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fos.close();
					;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			Toast.makeText(this, "sd卡存储不可用", Toast.LENGTH_SHORT).show();
		}
		return file.getAbsolutePath();
	}

	private void onQQShare(String path) {
		Bundle params = new Bundle();
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "冠唐云仓库");
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
		// params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
		// QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
		mTencent.shareToQQ(this, params, new BaseUiListener());

	}

	private void onWeiXinShare(String path) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		WXImageObject imgobj = new WXImageObject(bitmap);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgobj;
		
//		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
//		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
//		int options = 100;
//		thumbBmp.compress(Bitmap.CompressFormat.JPEG, options, localByteArrayOutputStream);
//		int len = 0;
//        while ((len=localByteArrayOutputStream.toByteArray().length) / 1024 > 32) { // 循环判断如果压缩后图片是否大于32kb,大于继续压缩
//        	localByteArrayOutputStream.reset();// 重置baos即清空baos
//			thumbBmp.compress(Bitmap.CompressFormat.JPEG, options, localByteArrayOutputStream);// 这里压缩options%，把压缩后的数据存放到baos中
//			options -= 15;// 每次都减少15%
//		}
//        msg.thumbData = localByteArrayOutputStream.toByteArray();
//		try {
//			localByteArrayOutputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		msg.setThumbImage(getWxShareBitmap(bitmap));
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		wxapi.sendReq(req);
		bitmap.recycle();
	}
	
	 /**
     * 微信分享图片不能超过64kb，特别坑...
     * @param targetBitmap bitmap
     * @return Bitmap
     */
    protected Bitmap getWxShareBitmap(Bitmap targetBitmap) {
        float scale = Math.min((float) 150 / targetBitmap.getWidth(), (float) 150 / targetBitmap.getHeight());
        Bitmap fixedBmp = Bitmap.createScaledBitmap(targetBitmap, (int) (scale * targetBitmap.getWidth()), (int) (scale * targetBitmap.getHeight()), true);
        return fixedBmp;
    }

	Runnable addmovedRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			Message msg = Message.obtain();
			if (DJList.get(0).get(DataBaseHelper.mType).toString().equals("0")) {
				MXList = WebserviceImport.Gt_PDDJ_details(100000000, "0",
						DJList.get(0).get(DataBaseHelper.HPM_ID).toString(), str4, strs4,
						WebserviceHelper.Gt_PDDJ_details, mSharedPreferences);
			} else {
				MXList = WebserviceImport.Gt_DJ_details(1000000000, "0",
						DJList.get(0).get(DataBaseHelper.HPM_ID).toString(), str2, strs2,
						WebserviceHelper.Gt_DJ_details, mSharedPreferences);
				// MaxMXID=WebserviceImport.GtMaxID_DJ_details(DJList.get(0).get(DataBaseHelper.HPM_ID).toString(),WebserviceHelper.GtMaxID_DJ_details,mSharedPreferences);
			}
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (DJList.get(0).get(DataBaseHelper.mType).toString().equals("0")) {
					double ying = 0, kui = 0;
					for (int i = 0; i < MXList.size(); i++) {
						if (!MXList.get(i).get("profit").toString().equals("")) {
							ying += Float.parseFloat(MXList.get(i).get("profit").toString());
						}
						if (!MXList.get(i).get("lose").toString().equals("")) {
							kui += Float.parseFloat(MXList.get(i).get("lose").toString());
						}
					}
					znumTxtView.setText(DecimalsHelper.Transfloat(ying, MyApplication.getInstance().getNumPoint()));
					zjeTxtView.setText(DecimalsHelper.Transfloat(kui, MyApplication.getInstance().getJePoint()));
					setAdapter(MXList, 0);// 0 代表不是盘点单据
				} else {
					double znum = 0, zje = 0;
					for (int i = 0; i < MXList.size(); i++) {
						if (!MXList.get(i).get(DataBaseHelper.MSL).toString().equals("")) {
							znum += Float.parseFloat(MXList.get(i).get(DataBaseHelper.MSL).toString());
						}
						if (!MXList.get(i).get(DataBaseHelper.ZJ).toString().equals("")) {
							zje += Float.parseFloat(MXList.get(i).get(DataBaseHelper.ZJ).toString());
						}
					}
					znumTxtView.setText(DecimalsHelper.Transfloat(znum, MyApplication.getInstance().getNumPoint()));
					zjeTxtView.setText(DecimalsHelper.Transfloat(zje, MyApplication.getInstance().getJePoint()));
					setAdapter(MXList, 1);// 1 代表不是盘点单据
				}
				progressDialog.dismiss();
				break;
			}
		};
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
		
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO 自动生成的方法存根

	}

	class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub

		}

	}

	private class BaseApiListener implements IRequestListener {

		@Override
		public void onComplete(JSONObject arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onJSONException(JSONException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNetworkUnavailableException(NetworkUnavailableException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUnknowException(Exception arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			File file = new File(PathConstant.PATH_cachephoto);
			if (file.exists()) {
				File[] fileList = file.listFiles();
				for (File f : fileList) {
					f.delete();
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
