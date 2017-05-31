package com.example.aleksefo.deardiary.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class RecAdapter extends RealmRecyclerViewAdapter<Entry, RecAdapter.ViewHolder> {

	private static final String TAG = "RecAdapter";

	public RecAdapter(OrderedRealmCollection<Entry> data) {
		super(data, true);
		Log.d(TAG, "RecAdapter: constr");
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Log.d(TAG, "onCreateViewHolder: created");
		View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.entry, parent, false);
		Log.d(TAG, "onCreateViewHolder: created");
		return new ViewHolder(itemView);
	}

//	@Override
//	public void onBindViewHolder(ViewHolder viewHolder, int i) {
//
//	}

	@Override
	public long getItemId(int index) {
		//noinspection ConstantConditions
//		return getItem(index).getCount();
		return index;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Entry obj = getData().get(position);
		Log.d(TAG, "onBindViewHolder: checking"+ obj);
		holder.data = obj;
		holder.title.setText(obj.getTitle());
	}


	class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
		TextView title;
		public Entry data;

		ViewHolder(View view) {
			super(view);
			title = (TextView) view.findViewById(R.id.textview);
			view.setOnLongClickListener(this);
		}

		@Override
		public boolean onLongClick(View v) {
			//activity.deleteItem(data);
			return true;
		}
	}
}