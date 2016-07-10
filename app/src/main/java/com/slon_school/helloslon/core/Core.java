package com.slon_school.helloslon.core;

import android.app.Activity;

import com.slon_school.helloslon.workers.TestWorker;

import java.util.ArrayList;

public class Core {
	private Activity activity;
	private ArrayList<Worker> workers;
	
	public Core (Activity activity) {
		this.activity = activity;
		workers = new ArrayList<Worker>();
		//TODO add all workers
		workers.add(new TestWorker(activity));
	}
	
	
	public String request(String string) {
		
			String backString = "...";
		
			ArrayList<String> keys = new ArrayList<String>();
			//TODO normal parser
			keys.add(string);
			
				
			for (Worker worker : workers) {
				//TODO think about keys
				if (equalsKeys(keys, worker.getKeys())) {
					backString = worker.doWork(keys);
					break; //TODO how to remove this
				}
			}
			
			return backString;
	}
	
	
	
	//TODO think about keys
	private boolean equalsKeys(ArrayList<String> keys, ArrayList<String> workerKeys) {
		for (String key : keys) {
			if (workerKeys.contains(key)) {
				return true;
			}
		}
		return false;
	}
	
	
}