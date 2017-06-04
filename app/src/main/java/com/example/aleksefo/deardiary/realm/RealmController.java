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
	public RealmController(Context context) {
		realm = Realm.getDefaultInstance();
		this.context = context;
	}
	public static RealmController with(Context context) {
		if (instance == null) {
			instance = new RealmController(context);
		}
		return instance;
	}
	public static RealmController with(Activity activity) {
		if (instance == null) {
			instance = new RealmController(activity.getApplication());
		}
		return instance;
	}

	public Realm getRealm() {
		return realm;
	}

	public void addOrUpdateEntry(String title, String descr, String id, Date date) {
		final Entry e = realm.where(Entry.class).equalTo("id", id).findFirst();
		final String mTitle = title;
		final String mDescr = descr;
		final Date mDate = date;
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
					e.setTitle(mTitle);
					e.setDate(mDate);
					e.setDescr(mDescr);
			}
		});
	}
	public void addOrUpdateEntry(String title, String descr) {
		final String mTitle = title;
		final String mDescr = descr;
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
					Entry e = RealmController.this.realm.createObject(Entry.class, UUID.randomUUID().toString());
					e.setTitle(mTitle);
					e.setDate(new Date());
					e.setDescr(mDescr);
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
		Toast.makeText(context, title + " entry was removed from the Diary", Toast.LENGTH_LONG).show();
//		http://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/
//		if (results.size() == 0) {
//			Prefs.with(context).setPreLoad(false);
//		}
	}
	public Entry getEntry(int position) {
		final int mPosition = position;
		final RealmResults<Entry> results = realm.where(Entry.class).findAll();
		Entry e = results.get(mPosition);
		return e;
	}
}
