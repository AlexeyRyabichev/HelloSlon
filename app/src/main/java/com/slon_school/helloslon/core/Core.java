package com.slon_school.helloslon.core;

import android.app.Activity;
import android.widget.Toast;

import com.slon_school.helloslon.workers.BrowserWorker;
import com.slon_school.helloslon.workers.FateBallWorker;
import com.slon_school.helloslon.workers.TestWorker;

import java.util.ArrayList;

import static java.lang.Long.compare;

public class Core {
	static private String defaultString = "...";

	private Activity activity;
	private ArrayList<Worker> workers;

	public Core (Activity activity) {
		this.activity = activity;
		workers = new ArrayList<Worker>();
		//TODO add all workers
		workers.add(new TestWorker(activity));
		workers.add(new FateBallWorker(activity));
		workers.add(new BrowserWorker(activity));
	}
	
	public String request(String request) {
		request = request.toLowerCase();

		String response = defaultString;

		for (Worker worker : workers) {
			boolean access = false;
			ArrayList<Key> eq = new ArrayList<Key>();

			for (Key key : worker.getKeys()) {

				if (subKey(key, request)) {
					access = true;
					eq.add(key);
					for (String word : key.get())
						request = request.replaceAll(word, "");
				}

			}

			if (access) {
				Key other = new Key(request);
				response = worker.doWork(eq, other);
			    break;
			}
		}

		return response;
}


private boolean subKey(Key key, String string) {
		long counter = 0;
		for (String str : key.get()) {
			if (string.contains(str)) {
				counter++;
			}
		}
		return compare(counter,key.get().size()) >= 0;
}

}
