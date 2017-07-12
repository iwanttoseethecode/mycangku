package com.guantang.cangkuonline.activity;

import android.view.KeyEvent;

public class ChildLoginActivity extends NewLoginActivity {
	@Override
	protected void startFinalMainActivity1() {
		finish();
	}
	
	@Override
	protected void startFinalMainActivity2() {
		finish();
	}
	
	@Override
	protected void startFinalMainActivity3() {
		finish();
	}
	
	@Override
	protected void liXianStartFinalMainActivity() {
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ¹Ø±Õapp½ø³Ì
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
}
