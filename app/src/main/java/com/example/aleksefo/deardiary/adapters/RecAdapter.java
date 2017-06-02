package com.example.aleksefo.deardiary.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.activity.MainActivity;
import com.example.aleksefo.deardiary.model.Entry;
import com.example.aleksefo.deardiary.realm.RealmController;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

//todo custom text if no items in db

public class RecAdapter extends RealmRecyclerViewAdapter<Entry, RecAdapter.ViewHolder> {

	private static final String TAG = "RecAdapter";
	private int position;
	private Context mCtx;

	public RecAdapter(OrderedRealmCollection<Entry> data,  Context mCtx) {
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
		Log.d(TAG, "onBindViewHolder: checking"+ obj);
		holder.data = obj;
		holder.title.setText(obj.getTitle());
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
						switch (item.getItemId()) {
							case R.id.action_edit:
								//handle menu1 click
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