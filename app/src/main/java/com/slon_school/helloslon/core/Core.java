package com.slon_school.helloslon.core;

import android.app.Activity;

import com.slon_school.helloslon.workers.AlarmWorker;
import com.slon_school.helloslon.workers.BashOrgRandomQuoteWorker;
import com.slon_school.helloslon.workers.BrowserWorker;
import com.slon_school.helloslon.workers.CalvinHobbsWorker;
import com.slon_school.helloslon.workers.FateBallWorker;
<<<<<<< HEAD
=======
import com.slon_school.helloslon.workers.HelpWorker;
import com.slon_school.helloslon.workers.PhoneWorker;
>>>>>>> develop
import com.slon_school.helloslon.workers.SMSWorker;
import com.slon_school.helloslon.workers.TestWorker;
import com.slon_school.helloslon.workers.TownWorker;
import com.slon_school.helloslon.workers.XKCDRandomComicWorker;
import com.slon_school.helloslon.workers.TranslateWorker;

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

		workers.add(new TranslateWorker(activity));
		workers.add(new BrowserWorker(activity));
		workers.add(new TestWorker(activity));
		workers.add(new FateBallWorker(activity));
		//workers.add(new EmailWorker(activity));
		workers.add(new AlarmWorker(activity));
		workers.add(new TownWorker(activity));
		workers.add(new BashOrgRandomQuoteWorker(activity));
		workers.add(new SMSWorker(activity));
<<<<<<< HEAD
=======
		workers.add(new PhoneWorker(activity));
 		//workers.add(new WeatherWorker(activity));
>>>>>>> develop
		workers.add(new XKCDRandomComicWorker(activity));
		workers.add(new CalvinHobbsWorker(activity));

		currentWorker = idNone;
	}
	
	public Response request(Response request) {
		//Toast.makeText(activity, request,Toast.LENGTH_LONG).show();
		String r = request.getResponse().toLowerCase();
		Response response = new Response(defaultString, false);
		//Toast.makeText(activity, currentWorker + "", Toast.LENGTH_LONG).show();

		if (currentWorker == idNone) {
			for (int i = 0; i < workers.size(); i++) {
				boolean access = false;
				ArrayList<Key> eq = new ArrayList<Key>();

				for (Key key : workers.get(i).getKeys()) {
					if (subKey(key, r)) {
						access = true;
						eq.add(key);
						for (String word : key.get())
							r = r.replaceAll(word, "");
					}
				}

				if (access) {
					Key other = new Key(r);

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
				if (subKey(key, r)) {
					eq.add(key);
					for (String word : key.get())
						r = r.replaceAll(word, "");
				}
			}

			Key arguments = new Key(r);
			response = workers.get(currentWorker).doWork(eq,arguments);

			if (!response.getIsEnd()) {
				currentWorker = idNone;
			}
		}

		//Toast.makeText(activity, currentWorker + "", Toast.LENGTH_LONG).show();
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
