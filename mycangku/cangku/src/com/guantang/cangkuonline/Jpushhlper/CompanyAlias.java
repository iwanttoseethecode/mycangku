package com.guantang.cangkuonline.Jpushhlper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.guantang.cangkuonline.helper.MyBase64;
import com.guantang.cangkuonline.shareprefence.ShareprefenceBean;

import android.content.Context;
import android.content.SharedPreferences;

public class CompanyAlias extends AbstractAlias {
	
	private String company;
	public CompanyAlias(String company,SharedPreferences mSharedPreferences) {
		super(mSharedPreferences);
		this.company = company;
	}

	@Override
	public String toEncode() {
		String alias = null;
		if (mSharedPreferences.getInt(ShareprefenceBean.TIYANZHANGHAO, 0) == 1) {// 是否是体验账户
			alias = MyBase64.getBase64("测试");
		} else {
			alias = MyBase64.getBase64(company);
		}
		
		return alias.length()>40 ? alias.substring(0, 40):alias;
	}

}
