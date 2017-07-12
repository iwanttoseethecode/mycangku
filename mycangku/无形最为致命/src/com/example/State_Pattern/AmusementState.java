package com.example.State_Pattern;

public class AmusementState implements State {

	@Override
	public void doAction() {
		System.out.println("又遇见坑货，让我来拯救世界吧！无形装逼最为致命");
	}

	@Override
	public void describeState() {
		System.out.println("这是娱乐状态");
	}

}
