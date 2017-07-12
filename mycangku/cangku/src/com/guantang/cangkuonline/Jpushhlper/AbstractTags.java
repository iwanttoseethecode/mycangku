package com.guantang.cangkuonline.Jpushhlper;

import java.util.Set;

public abstract class AbstractTags implements TagsInterface {
	
	protected TagsBean tagsBean;
	
	public AbstractTags(TagsBean tagsBean) {
		super();
		this.tagsBean = tagsBean;
	}

	@Override
	public abstract Set<String> toEncode();

	@Override
	public Set<String> returnTags() {
		// TODO Auto-generated method stub
		return toEncode();
	}

}
