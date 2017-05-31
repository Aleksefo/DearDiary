package com.example.aleksefo.deardiary.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Case;
import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmResults;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private static final String TAG = "MainActivity";

	private Realm realm;
	private RecyclerView recyclerView;
	private Menu menu;
	private RecAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		realm = Realm.getDefaultInstance();
//		RealmResults<Entry> entries = realm
//			.where(Entry.class)
//			.findAll();
		recyclerView = (RecyclerView) findViewById(R.id.recycler);
		adapter = new RecAdapter(realm.where(Entry.class).findAll());
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);

//		create a task
		realm.executeTransaction(new Transaction() {
			@Override
			public void execute(Realm realm) {
				Entry t = MainActivity.this.realm.createObject(Entry.class, UUID.randomUUID().toString());
//				t.setId(UUID.randomUUID().toString());
				t.setTitle("Hilloo");
				t.setDate(new Date());
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
		RealmResults<Entry> entries = realm.where(Entry.class)
			.contains("title", "Hillo", Case.INSENSITIVE)
			.findAll();
		for (Entry e: entries) {
			Log.d("Realm", e.getTitle() + e.getId() + e.getDate());
		}
	}

	//to prevent memory leak
	@Override
	public void onDestroy() {
		super.onDestroy();
		realm.close();
	}
}
