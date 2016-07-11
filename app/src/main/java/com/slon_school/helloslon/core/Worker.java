package com.slon_school.helloslon.core;

import android.app.Activity;

import java.util.ArrayList;

public abstract  class Worker {
	protected Activity activity;

	public Worker (Activity activity) {
		this.activity = activity;
	}

	public abstract ArrayList<Key> getKeys();
	public abstract boolean isContinue();
	public abstract String doWork(ArrayList<Key> keys, ArrayList<String> arguments);
}