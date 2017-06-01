package com.example.aleksefo.deardiary.adapters;


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
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

//todo custom text if no items in db

public class RecAdapter extends RealmRecyclerViewAdapter<Entry, RecAdapter.ViewHolder> {

	private static final String TAG = "RecAdapter";
	private int position;

	public RecAdapter(OrderedRealmCollection<Entry> data) {
		super(data, true);
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
				Log.d(TAG, "onLongClick: "+ getPosition());
				return false;
			}
		});
	}

	@Override
	public void onViewRecycled(ViewHolder holder) {
		holder.itemView.setOnLongClickListener(null);
		super.onViewRecycled(holder);
	}


	class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,
		OnCreateContextMenuListener {
		TextView title;
		public Entry data;

		ViewHolder(View view) {
			super(view);
			title = (TextView) view.findViewById(R.id.textview);
			view.setOnLongClickListener(this);
			view.setOnCreateContextMenuListener(this);
		}

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
			menu.add(Menu.NONE, R.id.action_edit,
				Menu.NONE, R.string.action_edit);
			menu.add(Menu.NONE, R.id.action_share,
				Menu.NONE, R.string.action_share);
			menu.add(Menu.NONE, R.id.action_delete,
				Menu.NONE, R.string.action_delete);
		}

//todo onLongClick
		@Override
		public boolean onLongClick(View v) {
			//activity.deleteItem(data);
			return true;
		}
	}
}