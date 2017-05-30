package com.example.aleksefo.deardiary.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.aleksefo.deardiary.R;
import com.example.aleksefo.deardiary.adapters.RecAdapter.ViewHolder;
import com.example.aleksefo.deardiary.model.Entry;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class RecAdapter  extends RealmBasedRecyclerViewAdapter<Entry, ViewHolder> {

	public class ViewHolder extends RealmViewHolder {
		public TextView todoTextView;
		public ViewHolder(FrameLayout container) {
			super(container);
			this.todoTextView = (TextView) container.findViewById(R.id.text_entry_title);
		}
	}

	public RecAdapter(
		Context context,
		RealmResults<Entry> realmResults,
		boolean automaticUpdate,
		boolean animateResults) {
		super(context, realmResults, automaticUpdate, animateResults);
	}

	@Override
	public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
		View v = inflater.inflate(R.layout.entry, viewGroup, false);
		return new ViewHolder((FrameLayout) v);
	}

	@Override
	public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
		final Entry toDoItem = realmResults.get(position);
		viewHolder.todoTextView.setText(toDoItem.getDescr());
//		viewHolder.itemView.setBackgroundColor(
//			COLORS[(int) (toDoItem.getId() % COLORS.length)]
//		);
	}
}