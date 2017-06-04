package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import com.example.aleksefo.deardiary.realm.RealmController;
import io.realm.Realm;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

	private static final String TAG = "EditActivity";
	private Realm realm;

	@BindView(R.id.add_title)
	EditText addTitle;
	@BindView(R.id.add_description)
	EditText addDescription;
	@BindView(R.id.show_date)
	TextView showDate;

	private String title;
	private String descr;
	private String id;
	private Date date;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		ButterKnife.bind(this);
		realm = Realm.getDefaultInstance();

		//for editMode
		intent = getIntent();
		if (intent.getStringExtra(RecAdapter.EXTRA_ID) != null) {
			getSupportActionBar().setTitle("Edit Entry");
			id = intent.getStringExtra(RecAdapter.EXTRA_ID);
			Entry e = realm.where(Entry.class).equalTo("id", id).findFirst();
			addTitle.setText(e.getTitle());
			addDescription.setText(e.getDescr());
			SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
			String formatted = formatter.format(e.getDate());
			showDate.setText(formatted);
			date = e.getDate();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_new, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int idM = item.getItemId();

		if (idM == R.id.action_save) {
			if (intent.getStringExtra(RecAdapter.EXTRA_ID) != null) {
				title = addTitle.getText().toString();
				descr = addDescription.getText().toString();
				RealmController.with(this).addOrUpdateEntry(title, descr, id, date);
			} else {
				title = addTitle.getText().toString();
				descr = addDescription.getText().toString();
				RealmController.with(this).addOrUpdateEntry(title, descr);
			}
			Intent createEntryIntent = new Intent(this, MainActivity.class);
			startActivity(createEntryIntent);
			return true;
		} else if (idM == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//to prevent memory leak
	@Override
	public void onDestroy() {
		super.onDestroy();
		realm.close();
	}

}
