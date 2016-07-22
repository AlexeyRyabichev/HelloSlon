package com.slon_school.helloslon.core;

		import android.app.Activity;

import java.util.ArrayList;

public abstract class Worker {
	protected Activity activity;
	protected String name;

	public Worker (Activity activity, String name) {
		this.activity = activity;
		this.name = name;
	}



	public abstract ArrayList<Key> getKeys();

	public abstract boolean isLoop();

	public String getName() {
		return name;
	}

	public abstract Response doWork(ArrayList<Key> keys, Key arguments);

	public abstract Response getHelp();
}