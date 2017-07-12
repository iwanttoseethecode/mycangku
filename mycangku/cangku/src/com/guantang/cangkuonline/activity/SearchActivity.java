package com.guantang.cangkuonline.activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.adapter.HistorySearchAdapter;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener,OnItemClickListener,TextWatcher{
	private ImageView scanImgView,delete_iconImgView;
	private TextView cancelTxtView,searchContentTextView;
	private LinearLayout searchContentlayout,historysearchlayout;
	private EditText searchEdit;
	private ListView historysearchListView;
	private LinkedList<String> historySearchList = new LinkedList<String>();
	private SharedPreferences mSharedPreferences;
	private HistorySearchAdapter mHistorySearchAdapter;
//	private Boolean HP_choseActivityStart = false;//�Ƿ��ǻ�Ʒѡ���������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		init();
	}
	
	public void initView(){
		scanImgView = (ImageView) findViewById(R.id.scanImgView);
		delete_iconImgView = (ImageView) findViewById(R.id.delete_icon);
		cancelTxtView = (TextView) findViewById(R.id.cancelTxtView);
		searchContentTextView = (TextView) findViewById(R.id.searchContentTextView);
		historysearchlayout = (LinearLayout) findViewById(R.id.historysearchlayout);
		searchContentlayout = (LinearLayout) findViewById(R.id.searchContentlayout);
		searchEdit = (EditText) findViewById(R.id.searchEdit);
		historysearchListView = (ListView) findViewById(R.id.historysearchListView);
		
		scanImgView.setOnClickListener(this);
		delete_iconImgView.setOnClickListener(this);
		cancelTxtView.setOnClickListener(this);
		searchContentlayout.setOnClickListener(this);
		historysearchListView.setOnItemClickListener(this);
		searchEdit.addTextChangedListener(this);
		searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO �Զ����ɵķ������
				if(actionId==EditorInfo.IME_ACTION_SEARCH||(event!=null&&event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
					saveHistorySearch(searchEdit.getText().toString().trim());
					Intent intent = new Intent();
//					if(HP_choseActivityStart){
//						intent.putExtra("searchString", searchEdit.getText().toString().trim());
//						intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
//						setResult(1,intent);
//						finish();
//					}else{
						intent.putExtra("searchString", searchEdit.getText().toString().trim());
						intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
						setResult(1,intent);
						finish();
//					}
					return true;
				}
				return false;
			}
		});
		
		Intent intent = getIntent();
//		HP_choseActivityStart = intent.getBooleanExtra("HP_choseActivityStart", false);
		switch(intent.getIntExtra("hint", 1)){
		case 1:
			searchEdit.setHint("������Ʒ���ơ����롢����ͺ�");
			break;
		case 2:
			searchEdit.setHint("������λ���ơ���ϵ�ˡ��绰��");
			break;
		}
	}

	public void init(){
		mSharedPreferences = MyApplication.getInstance().getSharedPreferences();
		String litttleString=mSharedPreferences.getString(ShareprefenceBean.HISTORYSEARCHITEM_1, "");
		if(!litttleString.equals("")){
			historySearchList.add(litttleString);
		}
		litttleString=mSharedPreferences.getString(ShareprefenceBean.HISTORYSEARCHITEM_2, "");
		if(!litttleString.equals("")){
			historySearchList.add(litttleString);
		}
		litttleString=mSharedPreferences.getString(ShareprefenceBean.HISTORYSEARCHITEM_3, "");
		if(!litttleString.equals("")){
			historySearchList.add(litttleString);
		}
		litttleString=mSharedPreferences.getString(ShareprefenceBean.HISTORYSEARCHITEM_4, "");
		if(!litttleString.equals("")){
			historySearchList.add(litttleString);
		}
		litttleString=mSharedPreferences.getString(ShareprefenceBean.HISTORYSEARCHITEM_5, "");
		if(!litttleString.equals("")){
			historySearchList.add(litttleString);
		}
		
		mHistorySearchAdapter = new HistorySearchAdapter(this,historySearchList,R.layout.popupwindow_list_textview);
		historysearchListView.setAdapter(mHistorySearchAdapter);
	}
	
	public void saveHistorySearch(String historySearchString){
		if(historySearchList.size()<5){
			historySearchList.addFirst(historySearchString);
		}else if(historySearchList.size()>=5){
			historySearchList.pollLast();
			historySearchList.addFirst(historySearchString);
		}

		int length = historySearchList.size();
		for(int i=0;i<length;i++){
			mSharedPreferences.edit().putString("historysearchitem_"+(i+1), historySearchList.pollFirst()).commit();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.scanImgView:
			intent.setClass(this, CaptureActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.cancelTxtView:
			finish();
			break;
		case R.id.delete_icon:
			searchEdit.setText("");
			break;
		case R.id.cancel:
			finish();
			break;
		case R.id.searchContentlayout:
			saveHistorySearch(searchEdit.getText().toString().trim());
//			if(HP_choseActivityStart){
//				intent.putExtra("searchString", searchContentTextView.getText().toString().trim());
//				intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
//				setResult(1,intent);
//				finish();
//			}else{
				intent.putExtra("searchString", searchContentTextView.getText().toString().trim());
				intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
				setResult(1,intent);
				finish();
//			}
			break;
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
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO �Զ����ɵķ������
		if(!s.toString().equals("")){
			delete_iconImgView.setVisibility(View.VISIBLE);
			searchContentlayout.setVisibility(View.VISIBLE);
			historysearchlayout.setVisibility(View.GONE);
			searchContentTextView.setText(s.toString());
		}else{
			delete_iconImgView.setVisibility(View.GONE);
			searchContentlayout.setVisibility(View.GONE);
			historysearchlayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO �Զ����ɵķ������
		String item=(String) arg0.getAdapter().getItem(arg2);
		Intent intent = new Intent();
//		if(HP_choseActivityStart){
//			intent.putExtra("searchString", item);
//			intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
//			setResult(1,intent);
//			finish();
//		}else{
			intent.putExtra("searchString", item);
			intent.putExtra("scanOrsearch", 1);////1����������2����ɨ�衣
			setResult(1,intent);
			finish();
//		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			if(resultCode==1){
				String scan_tm = data.getStringExtra("scan_tm");
				searchEdit.setText(scan_tm);
				Intent intent = new Intent();
//				if(HP_choseActivityStart){
//					intent.putExtra("searchString", searchContentTextView.getText());
//					intent.putExtra("scanOrsearch", 2);////1����������2����ɨ�衣
//					setResult(1,intent);
//					finish();
//				}else{
					intent.putExtra("searchString", searchContentTextView.getText());
					intent.putExtra("scanOrsearch", 2);////1����������2����ɨ�衣
					setResult(1,intent);
					finish();
//				}
			}
		}
	}
	
}