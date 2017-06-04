package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Realm;

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_bydate) {
			adapter = new RecAdapter(realm.where(Entry.class).findAll().sort("date"), this);
			recyclerView.setAdapter(adapter);
		}
		if (id == R.id.action_bytitle) {
			adapter = new RecAdapter(realm.where(Entry.class).findAll().sort("title"), this);
			recyclerView.setAdapter(adapter);
		}
		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.fab)
	public void onViewClicked(View view) {
		Intent addTaskIntent = new Intent(MainActivity.this, EditActivity.class);
		startActivity(addTaskIntent);
	}

	//to prevent memory leak
	@Override
	public void onDestroy() {
		super.onDestroy();
		realm.close();
	}


}
