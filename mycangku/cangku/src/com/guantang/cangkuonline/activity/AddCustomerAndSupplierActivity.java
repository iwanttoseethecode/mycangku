package com.guantang.cangkuonline.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.guantang.cangkuonline.R;
import com.guantang.cangkuonline.application.BaseActivity;
import com.guantang.cangkuonline.application.MyApplication;
import com.guantang.cangkuonline.helper.RightsHelper;
import com.guantang.cangkuonline.webservice.WebserviceImport;
import com.guantang.cangkuonline.webservice.WebserviceImport_more;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddCustomerAndSupplierActivity extends BaseActivity implements
		OnClickListener {

	private ImageButton backImgBtn;
	private TextView titleTxtView, commitTxtView, dwlbTxtView;
	private EditText dwbmEdit, dwmcEdit, lxrEdit, telEdit,
			addressEdit, shEdit, emailEdit, ybEdit, bankNameEdit,
			bankCardNumEdit;
	private LinearLayout dwlbLayout;
	private CheckBox customerCheckBox, supplierCheckBox;
	private String dwindex = "";
	private int selectdw = 0; // 0 �Ȳ��ǿͻ�Ҳ���ǹ�Ӧ�̣�1�ǿͻ���2�ǹ�Ӧ�̣�3���ǿͻ����ǹ�Ӧ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcustomerandsupplier);
		initView();
		init();
	}

	public void initView() {
		backImgBtn = (ImageButton) findViewById(R.id.back);
		titleTxtView = (TextView) findViewById(R.id.title);
		commitTxtView = (TextView) findViewById(R.id.commitTxtView);
		dwlbTxtView = (TextView) findViewById(R.id.dwlbTxtView);
		dwbmEdit = (EditText) findViewById(R.id.dwbmEdit);
		dwmcEdit = (EditText) findViewById(R.id.dwmcEdit);
		lxrEdit = (EditText) findViewById(R.id.lxrEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);
		shEdit = (EditText) findViewById(R.id.shEdit);
		emailEdit = (EditText) findViewById(R.id.emailEdit);
		ybEdit = (EditText) findViewById(R.id.ybEdit);
		bankNameEdit = (EditText) findViewById(R.id.bankNameEdit);
		bankCardNumEdit = (EditText) findViewById(R.id.bankCardNumEdit);
		dwlbLayout = (LinearLayout) findViewById(R.id.dwlbLayout);
		customerCheckBox = (CheckBox) findViewById(R.id.customerCheckBox);
		supplierCheckBox = (CheckBox) findViewById(R.id.supplierCheckBox);

		backImgBtn.setOnClickListener(this);
		commitTxtView.setOnClickListener(this);
		dwlbLayout.setOnClickListener(this);
		
		if(!RightsHelper.isHaveRight(RightsHelper.dw_addedit_KH, MyApplication.getInstance().getSharedPreferences())){
			customerCheckBox.setEnabled(false);
		}
		
		if(!RightsHelper.isHaveRight(RightsHelper.dw_addedit_GYS, MyApplication.getInstance().getSharedPreferences())){
			supplierCheckBox.setEnabled(false);
		}
		
		titleTxtView.setText("��λ���");
	}

	public void init() {

	}
	
	public void setEmpty(){
		dwindex = "";
		selectdw = 0;
		dwlbTxtView.setText("");
		dwbmEdit.setText("");
		dwmcEdit.setText("");
		lxrEdit.setText("");
		telEdit.setText("");
		addressEdit.setText("");
		shEdit.setText("");
		emailEdit.setText("");
		ybEdit.setText("");
		bankNameEdit.setText("");
		bankCardNumEdit.setText("");
		customerCheckBox.setChecked(false);
		supplierCheckBox.setChecked(false);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.dwlbLayout:
			intent.setClass(this, DwlbListActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.commitTxtView:
			if(!customerCheckBox.isChecked()&&!supplierCheckBox.isChecked()){
				selectdw = 0;
			}else if(customerCheckBox.isChecked()&&!supplierCheckBox.isChecked()){
				selectdw = 1;
			}else if(!customerCheckBox.isChecked()&&supplierCheckBox.isChecked()){
				selectdw = 2;
			}else if(customerCheckBox.isChecked()&&supplierCheckBox.isChecked()){
				selectdw = 3;
			}
			if(selectdw == 0){
				Toast.makeText(this, "��ѡ��λ����", Toast.LENGTH_SHORT).show();
			}else{
				if(dwmcEdit.getText().toString().trim().equals("")){
					Toast.makeText(this, "����д��λ����", Toast.LENGTH_SHORT).show();
				}else{
					if(WebserviceImport.isOpenNetwork(this)){
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("py", dwbmEdit.getText().toString().trim());
						map.put("dwType", selectdw);
						map.put("area", dwlbTxtView.getText().toString().trim());
						map.put("areaIndex", dwindex);
						map.put("dwName", dwmcEdit.getText().toString().trim());
						map.put("lxr", lxrEdit.getText().toString().trim());
						map.put("tel", telEdit.getText().toString().trim());
						map.put("addr", addressEdit.getText().toString().trim());
						map.put("swdjh", shEdit.getText().toString().trim());//˰��
						map.put("email", emailEdit.getText().toString().trim());
						map.put("yb", ybEdit.getText().toString().trim());
						map.put("yh", bankNameEdit.getText().toString().trim());
						map.put("zh", bankCardNumEdit.getText().toString().trim());
						
						JSONObject jsonObject = new JSONObject(map);
						new AddCompanyAsyncTask().execute(jsonObject.toString());
					}else{
						Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
		}
	}

	class AddCompanyAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO �Զ����ɵķ������
			String jsonString = WebserviceImport_more.AddCompany_1_0(params[0]);
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
					setEmpty();
					Toast.makeText(AddCustomerAndSupplierActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(AddCustomerAndSupplierActivity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == 1) {
				dwlbTxtView.setText(data.getStringExtra("dwName"));
				dwindex = data.getStringExtra("dwindex");
			}
		}
	}

}