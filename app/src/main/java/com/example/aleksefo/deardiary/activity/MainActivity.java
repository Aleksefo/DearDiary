package com.example.aleksefo.deardiary.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Realm;

//todo swipe to delete
public class MainActivity extends AppCompatActivity {

	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private static final String TAG = "MainActivity";

	private Realm realm;
	private RecyclerView recyclerView;
	private RecAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		realm = Realm.getDefaultInstance();
		recyclerView = (RecyclerView) findViewById(R.id.recycler);
		adapter = new RecAdapter(realm.where(Entry.class).findAll(), this);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);

		registerForContextMenu(recyclerView);
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
		if (id == R.id.action_bydate) {
			adapter = new RecAdapter(realm.where(Entry.class).findAll().sort("date"), this);
			recyclerView.setAdapter(adapter);
		}
		if (id == R.id.action_bytitle) {
			adapter = new RecAdapter(realm.where(Entry.class).findAll().sort("title"), this);
			recyclerView.setAdapter(adapter);
		}
		//todo add stuff
//		if(id == R.id.action_add_task){

//		}


		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.fab)
	public void onViewClicked(View view) {
//		Snackbar.make(view, "Replace plz", Snackbar.LENGTH_LONG)
//			.setAction("Action", null).show();
//		RealmResults<Entry> entries = realm.where(Entry.class)
//			.contains("title", "Hillo", Case.INSENSITIVE)
//			.findAll();
//		for (Entry e: entries) {
//			Log.d("Realm", e.getTitle() + e.getId() + e.getDate());
//		}
//		RealmController.with(this).addOrUpdateEntry();
		Intent addTaskIntent = new Intent(MainActivity.this, EditActivity.class);
		startActivity(addTaskIntent);
	}

//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		int position = -1;
//		try {
//			position = ((RecAdapter) recyclerView.getAdapter()).getPosition();
//
//		} catch (Exception e) {
//			Log.d(TAG, e.getLocalizedMessage(), e);
//			return super.onContextItemSelected(item);
//		}
//		switch (item.getItemId()) {
//			case R.id.action_edit:
//
//				return true;
//			case R.id.action_share:
//				return true;
//			case R.id.action_delete:
//				RealmController.with(this).deleteEntry(position);
//				return true;
//			default:
//				return super.onContextItemSelected(item);
//		}
//	}

	//to prevent memory leak
	@Override
	public void onDestroy() {
		super.onDestroy();
		realm.close();
	}


}
