package com.guantang.cangkuonline.Jpushhlper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import com.guantang.cangkuonline.helper.MyBase64;

public class LoginTags extends AbstractTags {

	public LoginTags(TagsBean tagsBean) {
		super(tagsBean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> toEncode() {
		
		Set<String> tagSet = new HashSet<String>();
		
		//标签和别名都不能超过40个字节
		String imei = MyBase64.getBase64(tagsBean.getIMEI());
		String username = MyBase64.getBase64(tagsBean.getUsername());
		tagSet.add(imei.length()>40 ? imei.substring(0, 40):imei);
		tagSet.add(username.length()>40 ? username.substring(0, 40):username);
		
		return tagSet;
	}

}
