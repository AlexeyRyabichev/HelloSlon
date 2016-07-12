package com.slon_school.helloslon.core;

import android.app.Activity;
import android.widget.Toast;

import com.slon_school.helloslon.workers.BrowserWorker;
import com.slon_school.helloslon.workers.TestWorker;

import java.util.ArrayList;

import static java.lang.Long.compare;

public class Core {
	static private String defaultString = "...";
	static private int idNone = -1;

	private Activity activity;
	private ArrayList<Worker> workers;
	private Worker testWorker;
	private int currentWorker;

	public Core (Activity activity) {
		this.activity = activity;
		workers = new ArrayList<Worker>();
		//TODO add all workers
		testWorker = new TestWorker(activity);
		workers.add(testWorker);
		workers.add(new BrowserWorker(activity));


		currentWorker = idNone;
	}
	
	public String request(String request) {
		request = request.toLowerCase();
		Response response = new Response(defaultString, false);


		if (currentWorker == idNone) {
			for (int i = 0; i < workers.size(); i++) {
				boolean access = false;
				ArrayList<Key> eq = new ArrayList<Key>();

				for (Key key : workers.get(i).getKeys()) {
					if (subKey(key, request)) {
						access = true;
						eq.add(key);
						for (String word : key.get())
							request = request.replaceAll(word, "");
					}
				}

				if (access) {
					Key other = new Key(request);

					response = workers.get(i).doWork(eq, other);

					if (response.getIsEnd()) {
						currentWorker = idNone;
					} else {
						currentWorker = i;
					}

					break;
				}
			}
		} else {
            ArrayList<Key> eq = new ArrayList<Key>();
			for (Key key : workers.get(currentWorker).getKeys()) {
				if (subKey(key, request)) {
					eq.add(key);
					for (String word : key.get())
						request = request.replaceAll(word, "");
				}
			}

			Key arguments = new Key(request);
			response = workers.get(currentWorker).doWork(eq,arguments);

			if (response.getIsEnd()) {
				currentWorker = idNone;
			}
		}

		return response.getResponse();
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
