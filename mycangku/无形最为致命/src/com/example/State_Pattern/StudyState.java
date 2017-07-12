package com.example.State_Pattern;

public class StudyState implements State {

	@Override
	public void doAction() {
		System.out.println(" 5 + 5 = ?");
	}

	@Override
	public void describeState() {
		System.out.println("这是学习的状态");
	}

}
