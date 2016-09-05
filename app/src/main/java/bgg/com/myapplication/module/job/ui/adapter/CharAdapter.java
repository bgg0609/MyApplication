package bgg.com.myapplication.module.job.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bgg.com.myapplication.R;
import bgg.com.myapplication.module.job.model.entity.CityGroup;

public class CharAdapter extends BaseAdapter {

	private List<CityGroup> data = new ArrayList<CityGroup>();
	private Context context;

	public List<CityGroup> getData() {
		return data;
	}

	public void setData(List<CityGroup> data) {
		this.data = data;
	}

	public CharAdapter(Context context, List<CityGroup> data) {
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.view_char_text, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.char_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.text.setText(data.get(position).getName());
		} catch (Exception e) {
			holder.text.setText("");
		}

		return convertView;
	}

	class ViewHolder {
		TextView text;
	}

}
