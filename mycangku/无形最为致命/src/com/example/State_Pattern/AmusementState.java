package com.example.State_Pattern;

public class AmusementState implements State {

	@Override
	public void doAction() {
		System.out.println("�������ӻ�����������������ɣ�����װ����Ϊ����");
	}

	@Override
	public void describeState() {
		System.out.println("��������״̬");
	}

}
