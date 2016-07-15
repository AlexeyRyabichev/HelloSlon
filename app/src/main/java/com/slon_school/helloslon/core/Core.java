package com.slon_school.helloslon.core;

import android.app.Activity;
import android.widget.Toast;

import com.slon_school.helloslon.workers.AlarmWorker;
import com.slon_school.helloslon.workers.BashOrgRandomQuote;
import com.slon_school.helloslon.workers.BrowserWorker;
import com.slon_school.helloslon.workers.FateBallWorker;
import com.slon_school.helloslon.workers.HelpWorker;
import com.slon_school.helloslon.workers.SMSSendWorker;
import com.slon_school.helloslon.workers.SMSWorker;
import com.slon_school.helloslon.workers.TestWorker;
import com.slon_school.helloslon.workers.TownWorker;

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
		workers.add(new FateBallWorker(activity));
		workers.add(new SMSSendWorker(activity));
		workers.add(new AlarmWorker(activity));
		workers.add(new TownWorker(activity));
		workers.add(new BashOrgRandomQuote(activity));
		workers.add(new HelpWorker(activity));
		workers.add(new SMSWorker(activity));

		currentWorker = idNone;
	}
	
	public String request(String request) {
		Toast.makeText(activity, request,Toast.LENGTH_LONG).show();
		request = request.toLowerCase();
		Response response = new Response(defaultString, false);

		Toast.makeText(activity, currentWorker + "", Toast.LENGTH_LONG).show();

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

					if (!response.getIsEnd()) {
						currentWorker = idNone;
					} else {
						currentWorker = i;
					}

					break;
				}
			}
		} else {

			//Toast.makeText(activity, "I was here", Toast.LENGTH_LONG).show();

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

			if (!response.getIsEnd()) {
				currentWorker = idNone;
			}
		}



		Toast.makeText(activity, currentWorker + "", Toast.LENGTH_LONG).show();
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
