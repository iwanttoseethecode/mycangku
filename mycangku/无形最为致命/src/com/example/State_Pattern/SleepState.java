package com.example.State_Pattern;

public class SleepState implements State {

	@Override
	public void doAction() {
		System.out.println("���μ�Ů����");
	}

	@Override
	public void describeState() {
		System.out.println("����˯��״̬");
	}

}
