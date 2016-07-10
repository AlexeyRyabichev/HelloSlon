package com.slon_school.helloslon.core;

import android.app.Activity;

import java.util.ArrayList;

public abstract  class Worker {
	public Worker (Activity activity){};
	public abstract ArrayList<String> getKeys();
	public abstract boolean isContinue();
	public abstract String doWork(ArrayList<String> arguments);
}