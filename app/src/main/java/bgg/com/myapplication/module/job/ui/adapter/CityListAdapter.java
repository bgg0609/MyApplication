package bgg.com.myapplication.module.job.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.module.job.model.entity.CityGroup;
import bgg.com.myapplication.module.job.ui.custom.CityGroupLayout;
import bgg.com.myapplication.module.job.ui.custom.CityGroupLayout.OnItemSelectListener;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class CityListAdapter extends BaseAdapter {

    private Context context;
    private List<CityGroup> cityGroups;
    private OnItemSelectListener onItemSelectListener;


    public CityListAdapter(Context context, List<CityGroup> cityGroups) {
        this.context = context;
        this.cityGroups = cityGroups;
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @Override
    public int getCount() {
        return cityGroups == null ? 0 : cityGroups.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.job_city_list_item, null);
            viewHolder.tvGroupName = (TextView) convertView.findViewById(R.id.tvGroupName);
            viewHolder.cityGroupView = (CityGroupLayout) convertView.findViewById(R.id.CityGroupView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CityGroup cityGroup = cityGroups.get(position);

        viewHolder.tvGroupName.setText(cityGroup.getName());

        viewHolder.cityGroupView.setCityList(cityGroup.getCityList());
        viewHolder.cityGroupView.setOnItemSelectListener(onItemSelectListener);

        return convertView;
    }

    class ViewHolder {
        TextView tvGroupName;
        CityGroupLayout cityGroupView;
    }
}
