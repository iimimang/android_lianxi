package com.app.note.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.note.R;
import com.app.note.db.NoteDefine;

public class NoteAdapter extends BaseAdapter {
	private List<NoteDefine> mList;
	private LayoutInflater layoutInflater;
	private Context mContext;

	public NoteAdapter(Context context, List<NoteDefine> noteList) {
		this.mContext = context;
		layoutInflater = LayoutInflater.from(context);
		this.mList = noteList;
	}

	public List<NoteDefine> getShowList() {
		return mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public NoteDefine getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class Viewholder {
		public TextView note_content;// 内容
		public TextView note_date;// 日期
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;

		if (convertView == null) {
			holder = new Viewholder();
			convertView = layoutInflater.inflate(R.layout.note_adapter_item,
					null);
			holder.note_date = (TextView) convertView
					.findViewById(R.id.note_date);
			holder.note_content = (TextView) convertView
					.findViewById(R.id.note_content);
			convertView.setTag(holder);
		} else {
			holder = (Viewholder) convertView.getTag();
		}

		final NoteDefine item = getItem(position);
		holder.note_date.setText(item.getNotetime());
		holder.note_content.setText(item.getNoteExplain());
		return convertView;
	}
}
