package com.slon_school.helloslon.core;

import android.app.Activity;

import java.util.ArrayList;

public abstract  class Worker {
	private Activity activity;

	public Worker (Activity activity) {
		this.activity = activity;
	}

	public abstract ArrayList<String> getKeys();
	public abstract boolean isContinue();
	public abstract String doWork(ArrayList<String> arguments);
}