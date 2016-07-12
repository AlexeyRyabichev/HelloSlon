package com.slon_school.helloslon.core;

		import android.app.Activity;

		import java.util.ArrayList;

public abstract  class Worker {
	protected Activity activity;
	public Worker (Activity activity) {
		this.activity = activity;
	}
	public abstract ArrayList<Key> getKeys();
	//TODO Delete isContinue ?
	public abstract boolean isContinue();
	public abstract Response doWork(ArrayList<Key> keys, Key arguments);
}