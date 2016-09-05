package bgg.com.myapplication.module.job.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.module.job.model.entity.City;


public class CityGroupLayout extends LinearLayout {

    public interface OnItemSelectListener {
        void cityItemSelect(City city);
    }

    private static final int COLUMN = 3;

    private List<City> cityList;

    private CityItemAdapter adapter;

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
        adapter.notifyDataSetChanged();
    }

    public CityGroupLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public CityGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CityGroupLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        adapter = new CityItemAdapter(getContext());

        InnerGridView gridView = new InnerGridView(getContext());
        gridView.setHorizontalSpacing(16);
        gridView.setVerticalSpacing(16);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        gridView.setAdapter(adapter);

        gridView.setNumColumns(COLUMN);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);

        addView(gridView);
    }

    class CityItemAdapter extends BaseAdapter {

        private Context context;

        public CityItemAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            if (cityList == null) return 0;
            return cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.job_city_item, null);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final City city = cityList.get(position);
            viewHolder.tvName.setText(city.getName());
            viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectListener != null)
                        onItemSelectListener.cityItemSelect(city);
                }
            });
            return convertView;
        }


        class ViewHolder {
            public TextView tvName;
        }
    }


}
