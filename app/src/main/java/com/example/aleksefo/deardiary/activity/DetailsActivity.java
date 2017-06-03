package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.Realm;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

	@BindView(R.id.show_date)
	TextView showDate;
	@BindView(R.id.add_description)
	TextView addDescription;

	private Realm realm;
	private String title;
	private String descr;
	private String id;
	private Date date;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ButterKnife.bind(this);
		realm = Realm.getDefaultInstance();

		intent = getIntent();
		id = intent.getStringExtra(RecAdapter.EXTRA_ID);
		Entry e = realm.where(Entry.class).equalTo("id", id).findFirst();
		addDescription.setText(e.getDescr());
		showDate.setText(e.getDate().toString());
//		date = e.getDate();

	}
}
