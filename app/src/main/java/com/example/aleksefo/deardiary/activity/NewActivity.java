package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.realm.RealmController;

public class NewActivity extends AppCompatActivity {

	private static final String TAG = "NewActivity";

	@BindView(R.id.add_title)
	EditText addTitle;
	@BindView(R.id.add_description)
	EditText addDescription;
	@BindView(R.id.show_date)
	TextView showDate;

	private String title;
	private String descr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		ButterKnife.bind(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_new, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_save) {
			title = addTitle.getText().toString();
			descr = addDescription.getText().toString();
			RealmController.with(this).addEntry(title, descr);
			Intent createEntryIntent = new Intent(this, MainActivity.class);
			startActivity(createEntryIntent);
			return true;
		} else if (id == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
