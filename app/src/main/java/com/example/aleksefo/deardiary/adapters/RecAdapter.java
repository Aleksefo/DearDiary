package com.example.aleksefo.deardiary.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.activity.DetailsActivity;
import com.example.aleksefo.deardiary.activity.EditActivity;
import com.example.aleksefo.deardiary.activity.MainActivity;
import com.example.aleksefo.deardiary.model.Entry;
import com.example.aleksefo.deardiary.realm.RealmController;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

//todo custom text if no items in db

public class RecAdapter extends RealmRecyclerViewAdapter<Entry, RecAdapter.ViewHolder> {

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
		holder.data = obj;
		holder.title.setText(obj.getTitle());
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
		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				setPosition(stupid.getAdapterPosition());
				//creating a popup menu
				PopupMenu popup = new PopupMenu(mCtx, stupid.itemView);
				//inflating menu from xml resource
				popup.inflate(R.menu.menu_context);
				//adding click listener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
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
								//handle menu2 click
								break;
							case R.id.action_delete:
								RealmController.with(mCtx).deleteEntry(getPosition());
								break;
						}
						return false;
					}
				});
				//displaying the popup
				popup.show();
				return false;
			}
		});


	}

	@Override
	public void onViewRecycled(ViewHolder holder) {
		holder.itemView.setOnLongClickListener(null);
		super.onViewRecycled(holder);
	}


	class ViewHolder extends RecyclerView.ViewHolder {

		TextView title;
		public Entry data;

		ViewHolder(View view) {
			super(view);
			title = (TextView) view.findViewById(R.id.textview);
//			view.setOnLongClickListener(this);
//			view.setOnCreateContextMenuListener(this);
		}

//		@Override
//		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//			menu.add(Menu.NONE, R.id.action_edit,
//				Menu.NONE, R.string.action_edit);
//			menu.add(Menu.NONE, R.id.action_share,
//				Menu.NONE, R.string.action_share);
//			menu.add(Menu.NONE, R.id.action_delete,
//				Menu.NONE, R.string.action_delete);
//		}

//todo onLongClick
//		@Override
//		public boolean onLongClick(View v) {
//			//activity.deleteItem(data);
//			return true;
//		}
	}
}