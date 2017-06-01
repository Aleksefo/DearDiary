package com.example.aleksefo.deardiary.realm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import com.example.aleksefo.deardiary.activity.MainActivity;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmResults;
import java.util.Date;
import java.util.UUID;

// This is a singleton class where we put our realm methods for usage through the app.
//How to use: RealmController.with(this).getEntries()
public class RealmController {

	private static RealmController instance;
	private final Realm realm;
	final Context context;

	public RealmController(Application application) {
		realm = Realm.getDefaultInstance();
		this.context = application;
	}

	public static RealmController with(Fragment fragment) {
		if (instance == null) {
			instance = new RealmController(fragment.getActivity().getApplication());
		}
		return instance;
	}
	public static RealmController with(Activity activity) {
		if (instance == null) {
			instance = new RealmController(activity.getApplication());
		}
		return instance;
	}
	public static RealmController with(Application application) {
		if (instance == null) {
			instance = new RealmController(application);
		}
		return instance;
	}

	public static RealmController getInstance() {
		return instance;
	}
	public Realm getRealm() {
		return realm;
	}

	public void addEntry(String title, String descr) {
		final String mTitle = title;
		final String mDescr = descr;
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
				Entry t = RealmController.this.realm.createObject(Entry.class, UUID.randomUUID().toString());
				t.setTitle(mTitle);
				t.setDate(new Date());
				t.setDescr(mDescr);
			}
		});
	}
	public void deleteEntry(int position) {
		final int mPosition = position;
		final RealmResults<Entry> results = realm.where(Entry.class).findAll();
		Entry e = results.get(mPosition);
		String title = e.getTitle();
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
				results.deleteFromRealm(mPosition);
			}
		});
		Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
//		http://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/
//		if (results.size() == 0) {
//			Prefs.with(context).setPreLoad(false);
//		}

	}


	//Refresh the realm istance
	//todo You should either use realm.waitForChange()or open a new Realm instance as appropriate.
	public void refresh() {
		realm.refresh();
	}

	//clear all objects
	public void clearAll() {
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
				realm.deleteAll();
			}
		});
	}

	//find all objects
	public RealmResults<Entry> getEntries() {
		return realm.where(Entry.class).findAll();
	}

	//query a single item with the given id
	public Entry getEntry(String id) {
		return realm.where(Entry.class).equalTo("id", id).findFirst();
	}

	//check if Entry.class is empty
	public boolean hasEntries() {
		return !realm.where(Entry.class).findAll().isEmpty();
	}

	//query example
	public RealmResults<Entry> queriedEntries() {

		return realm.where(Entry.class)
//			.contains("author", "Author 0")
//			.or()
//			.contains("title", "Realm")
			.findAll();

	}
}
//todo clean this file
