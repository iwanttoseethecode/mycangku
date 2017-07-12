package com.example.State_Pattern;

public class Context {
	
	private State mState;
	
	public void study(){
		mState = new StudyState();
		mState.describeState();
	}
	
	public void amusement(){
		mState = new AmusementState();
		mState.describeState();
	}
	
	public void sleep(){
		mState = new SleepState();
		mState.describeState();
	}
	
	public void doAction(){
		mState.doAction();
	}
}
