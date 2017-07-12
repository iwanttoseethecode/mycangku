package com.guantang.cangkuonline.Jpushhlper;

import android.content.SharedPreferences;

public abstract class AbstractAlias implements AliasInterface {
	
	protected SharedPreferences mSharedPreferences;
	
	public AbstractAlias(SharedPreferences mSharedPreferences){
		this.mSharedPreferences = mSharedPreferences;
	}
	
	@Override
	public abstract String toEncode();

	@Override
	public String returnAlias() {
		// TODO Auto-generated method stub
		return toEncode();
	}

}
