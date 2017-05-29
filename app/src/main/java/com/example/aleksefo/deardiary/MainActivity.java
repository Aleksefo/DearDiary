package com.example.aleksefo.deardiary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.aleksefo.deardiary.Model.Entry;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmResults;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private Realm mRealm;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		mRealm = Realm.getDefaultInstance();

//		create a task
		mRealm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
				Entry t = mRealm.createObject(Entry.class, UUID.randomUUID().toString());
//				t.setId(UUID.randomUUID().toString());
				t.setTitle("Hillo");
//				t.setDate(new Date());
				t.setDescr("description");
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Log.d(TAG, "onOptionsItemSelected: ");
		}

		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.fab)
	public void onViewClicked(View view) {
//		Snackbar.make(view, "Replace plz", Snackbar.LENGTH_LONG)
//			.setAction("Action", null).show();
		RealmResults<Entry> entries = mRealm.where(Entry.class).findAll();
		for (Entry e: entries) {
			Log.d("Realm", e.getTitle() + e.getId());
		}
	}

	//to prevent memory leak
	@Override
	public void onDestroy() {
		super.onDestroy();
		mRealm.close();
	}
}
