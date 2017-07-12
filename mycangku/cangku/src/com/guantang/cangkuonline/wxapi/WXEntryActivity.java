package com.guantang.cangkuonline.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

import com.guantang.cangkuonline.static_constant.PathConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 这个类是微信回调的类
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx340122b85575be10", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
//    	File file = new File(PathConstant.PATH_cachephoto);
//		if (file.exists()) {
//			File[] fileList = file.listFiles();
//			for (File f : fileList) {
//				f.delete();
//			}
//		}
    }

    @Override
    public void onResp(BaseResp resp) {
//    	File file = new File(PathConstant.PATH_cachephoto);
//		if (file.exists()) {
//			File[] fileList = file.listFiles();
//			for (File f : fileList) {
//				f.delete();
//			}
//		}
    	finish();
    }
}
