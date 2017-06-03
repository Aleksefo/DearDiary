package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Realm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

	@BindView(R.id.show_date)
	TextView showDate;
	@BindView(R.id.add_description)
	TextView addDescription;

	public static final String EXTRA_ID = "com.example.aleksefo.deardiary.ID";

	private Realm realm;
	private String title;
	private String descr;
	private String idE;
	private Date date;
	Intent intent;
	Entry e;
	private ShareActionProvider mShareActionProvider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);
		realm = Realm.getDefaultInstance();
		intent = getIntent();
		idE = intent.getStringExtra(RecAdapter.EXTRA_ID);
		e = realm.where(Entry.class).equalTo("id", idE).findFirst();
		getSupportActionBar().setTitle(e.getTitle());
		addDescription.setText(e.getDescr());

		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
		String formatted = formatter.format(e.getDate());
		showDate.setText(formatted);
//		date = e.getDate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_details, menu);
		MenuItem item = menu.findItem(R.id.action_shareD);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
		setShareIntent();
		return true;
	}

	// Call to update the share intent
	private void setShareIntent() {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, e.getTitle() +": " + e.getDescr());
		sendIntent.setType("text/plain");
		if (mShareActionProvider != null) {
			mShareActionProvider.setShareIntent(sendIntent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_editD) {
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra(EXTRA_ID, idE);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
