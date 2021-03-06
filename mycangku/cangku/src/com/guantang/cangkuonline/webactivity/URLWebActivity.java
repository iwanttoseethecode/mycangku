package com.guantang.cangkuonline.webactivity;

import com.guantang.cangkuonline.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class URLWebActivity extends Activity implements OnClickListener {

	private ImageButton backImgBtn;
	private WebView myWebView;
	private TextView titleTxtView;
//	private ProgressDialog pro_dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urlweb);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		init();
	}

	public void init() {
		backImgBtn = (ImageButton) findViewById(R.id.back);
		myWebView = (WebView) findViewById(R.id.myWebView);
		titleTxtView = (TextView) findViewById(R.id.title);
		Intent intent = getIntent();
		titleTxtView.setText(intent.getStringExtra("title"));
		WebSettings myWebSettings = myWebView.getSettings();
		myWebSettings.setDefaultTextEncodingName("UTF-8");
		myWebSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl(intent.getStringExtra("url"));
		// 设置此属性，仅支持双击缩放，不支持触摸缩放（在android4.0是这样，其他平台没试过）
		myWebSettings.setSupportZoom(true);

		// 如果设置了此属性，那么webView.getSettings().setSupportZoom(true);也默认设置为true
		myWebSettings.setBuiltInZoomControls(true);
		myWebSettings.setDisplayZoomControls(false);
		// myWebView.setInitialScale(100);
		// myWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。
		myWebView.requestFocus();
		// myWebView.setScrollBarStyle(0);
		myWebView.setWebViewClient(webViewClient);

		backImgBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}

	 WebViewClient webViewClient = new WebViewClient() {

	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	            super.onPageStarted(view, url, favicon);
//	            pro_dialog.show(URLWebActivity.this, null, "正在加载数据");
//	            pro_dialog.setCancelable(true);
	        }

	        @Override
	        public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
//	            pro_dialog.dismiss();
	        }
	        
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        	view.loadUrl(url);  
                return true;
	        };

	        @Override
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	            super.onReceivedError(view, errorCode, description, failingUrl);
	            if (errorCode>299){
//	            	pro_dialog.dismiss();
	            	Toast.makeText(URLWebActivity.this, "加载失败了", Toast.LENGTH_SHORT).show();
	            }
	        }

	    };

	   

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (myWebView.canGoBack()) {
				myWebView.goBack();// 返回上一页面

			} else {
				finish();
			}
		}

		return super.onKeyDown(keyCode, event);
	}

}
