package com.example.State_Pattern;

public class SleepState implements State {

	@Override
	public void doAction() {
		System.out.println("又梦见女神了");
	}

	@Override
	public void describeState() {
		System.out.println("这是睡觉状态");
	}

}
