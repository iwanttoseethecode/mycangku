package com.guantang.cangkuonline.fragment;

import java.text.SimpleDateFormat;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.helper.PwdHelper;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;
import com.guantang.cangkuonline.webactivity.URLWebActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class New_Analytic_StatisticsFragment extends Fragment implements View.OnClickListener{
	
	private Context context;
	private LinearLayout product_1_layout,product_2_layout,product_3_layout;
	private LinearLayout sales_1_layout,sales_2_layout,sales_3_layout;
	private LinearLayout purchase_1_layout;
	private LinearLayout other_statistics_layout;
	
	@Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.new_anslytic_statistics_fragment, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		product_1_layout = (LinearLayout) getView().findViewById(R.id.product_1_layout);
		product_2_layout = (LinearLayout) getView().findViewById(R.id.product_2_layout);
		product_3_layout = (LinearLayout) getView().findViewById(R.id.product_3_layout);
		sales_1_layout = (LinearLayout) getView().findViewById(R.id.sales_1_layout);
		sales_2_layout = (LinearLayout) getView().findViewById(R.id.sales_2_layout);
		sales_3_layout = (LinearLayout) getView().findViewById(R.id.sales_3_layout);
		purchase_1_layout = (LinearLayout) getView().findViewById(R.id.purchase_1_layout);
		other_statistics_layout = (LinearLayout) getView().findViewById(R.id.other_statistics_layout);
		
		product_1_layout.setOnClickListener(this);
		product_2_layout.setOnClickListener(this);
		product_3_layout.setOnClickListener(this);
		sales_1_layout.setOnClickListener(this);
		sales_2_layout.setOnClickListener(this);
		sales_3_layout.setOnClickListener(this);
		purchase_1_layout.setOnClickListener(this);
		other_statistics_layout.setOnClickListener(this);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		PwdHelper pwdhelper = new PwdHelper();
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		switch(v.getId()){
		case R.id.product_1_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/kczq.html");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("?sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("?sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("?sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			sb.append("&act=2");
			
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "滞留时长分析");
			startActivity(intent);
			break;
		case R.id.product_2_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/kczq.html");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("?sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("?sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("?sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			sb.append("&act=1");
			
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "长期无出库分析");
			startActivity(intent);
			break;
		case R.id.product_3_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/kczq.html");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("?sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("?sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("?sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			sb.append("&act=yxq30");
			
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "有效期过期分析");
			startActivity(intent);
			break;
		case R.id.sales_1_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/saleTotal.html");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("?sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("?sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("?sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			sb.append("&io=2&type=销售出库");
			
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "销售出库汇总分析");
			startActivity(intent);
			break;
		case R.id.sales_2_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/GoodsSale.html?act=1");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("&sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("&sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("&sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "货品销量统计分析");
			startActivity(intent);
			break;
		case R.id.sales_3_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/saleCompare.html?act=1");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("&sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("&sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("&sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "客户销售量对比分析");
			startActivity(intent);
			break;
		case R.id.purchase_1_layout:
			sb.append(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.NET_URL, "")+"Views/WebAPP/saleTotal.html");
			if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.LOGIN_FLAG, 0)==1){//是否是独立域名  是1是注册制0是独立域名
				if(MyApplication.getInstance().getSharedPreferences().getInt(ShareprefenceBean.TIYANZHANGHAO, 0)==1){//是否是体验账户   1是体验帐号，0不是体验账号
					sb.append("?sob=测试");
					sb.append("&username=admin");
					sb.append("&password="+pwdhelper.createMD5("admin" + "#cd@guantang").toUpperCase());
				}else{
					sb.append("?sob="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.DWNAME_LOGIN, ""));
					sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
					sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
				}
			}else{
				sb.append("?sob=");
				sb.append("&username="+MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.USERNAME, ""));
				sb.append("&password="+pwdhelper.createMD5(MyApplication.getInstance().getSharedPreferences().getString(ShareprefenceBean.PASSWORD, "") + "#cd@guantang").toUpperCase());
			}
			sb.append("&io=1&type=采购入库");
			
			intent.setClass(context, URLWebActivity.class);
			intent.putExtra("url", sb.toString());
			intent.putExtra("title", "采购入库汇总分析");
			startActivity(intent);
			break;
		case R.id.other_statistics_layout:
			
			break;

		}
	}
}
