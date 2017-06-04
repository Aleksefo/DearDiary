package com.example.aleksefo.deardiary.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.activity.DetailsActivity;
import com.example.aleksefo.deardiary.activity.EditActivity;
import com.example.aleksefo.deardiary.activity.MainActivity;
import com.example.aleksefo.deardiary.adapters.RecAdapter.ViewHolder;
import com.example.aleksefo.deardiary.model.Entry;
import com.example.aleksefo.deardiary.realm.RealmController;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//todo custom text if no items in db

public class RecAdapter extends RealmRecyclerViewAdapter<Entry, ViewHolder> {

	private static final String TAG = "RecAdapter";
	public static final String EXTRA_ID = "com.example.aleksefo.deardiary.ID";
	private int position;
	private Context mCtx;
	MainActivity mActivity = new MainActivity();


	public RecAdapter(OrderedRealmCollection<Entry> data, Context mCtx) {
		super(data, true);
		this.mCtx = mCtx;
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.entry, parent, false);
		return new ViewHolder(itemView);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final ViewHolder stupid = holder;
		Entry obj = getData().get(position);
		Log.d(TAG, "onBindViewHolder: checking" + obj);
//		holder.data = obj;
		holder.title.setText(obj.getTitle());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
		String formatted = formatter.format(obj.getDate());
		holder.showDate.setText(formatted);
		holder.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPosition(stupid.getAdapterPosition());
				String id = RealmController.with(mCtx).getEntry(getPosition()).getId();
				Intent intent = new Intent(mCtx, DetailsActivity.class);
				intent.putExtra(EXTRA_ID, id);
				mCtx.startActivity(intent);
			}
		});
		holder.itemView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				setPosition(stupid.getAdapterPosition());
				//creating a popup menu
				PopupMenu popup = new PopupMenu(mCtx, stupid.itemView);
				//inflating menu from xml resource
				popup.inflate(R.menu.menu_context);
				//adding click listener
				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						String id = RealmController.with(mCtx).getEntry(getPosition()).getId();
						switch (item.getItemId()) {
							case R.id.action_edit:
								Intent intent = new Intent(mCtx, EditActivity.class);
								intent.putExtra(EXTRA_ID, id);
								mCtx.startActivity(intent);
								Log.d(TAG, "openEditEntry: " + id);
								break;
							case R.id.action_share:
								Intent sendIntent = new Intent(Intent.ACTION_SEND);
								sendIntent.putExtra(Intent.EXTRA_TEXT,
									RealmController.with(mCtx).getEntry(getPosition()).getTitle()
										+ ": " + RealmController.with(mCtx).getEntry(getPosition())
										.getDescr());
								sendIntent.setType("text/plain");
								mCtx.startActivity(sendIntent);
								break;
							case R.id.action_delete:
								RealmController.with(mCtx).deleteEntry(getPosition());
								break;
						}
						return false;
					}
				});
				popup.show();
				return false;
			}
		});
	}

	@Override
	public void onViewRecycled(ViewHolder holder) {
		holder.itemView.setOnLongClickListener(null);
		holder.itemView.setOnClickListener(null);
		super.onViewRecycled(holder);
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		TextView title;
		TextView showDate;

		ViewHolder(View view) {
			super(view);
			title = (TextView) view.findViewById(R.id.titleText);
			showDate = (TextView) view.findViewById(R.id.show_dateText);
		}
	}
}