package bgg.com.myapplication.common.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bgg.com.myapplication.R;


/**
 * 底部对话框
 *
 * @author Administrator
 */
public class BottomDialog extends Dialog {

    private Context context;
    private TextView tvTitle;
    private ListView lvOptions;

    public interface CallBack {
        void onItemSelect(int position);
    }


    public BottomDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
        initTheme();
        initPositionAndAnimation();
    }

    private void initTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_bottom_dialog);
        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lvOptions = (ListView) findViewById(R.id.lvOptions);
    }


    protected void initPositionAndAnimation() {
        Window window = getWindow();

        WindowManager windowManager = window.getWindowManager();

        Point mPoint = new Point();

        windowManager.getDefaultDisplay().getSize(mPoint);

        // 设置显示动画
        window.setWindowAnimations(R.style.bottom_dialog_animstyle);
        WindowManager.LayoutParams windowManagerParams = window.getAttributes();
        windowManagerParams.x = 0;
        windowManagerParams.y = mPoint.y;
        // 以下这两句是为了保证按钮可以水平满屏
//		windowManagerParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//		windowManagerParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        if (metrics.heightPixels < mPoint.y / 2) {
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        } else {
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mPoint.y / 2);

        }

        // 设置显示位置
        onWindowAttributesChanged(windowManagerParams);
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);

    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOptions(List<String> options, final CallBack callBack) {
        OptionAdapter adapter = new OptionAdapter(getContext(), options);
        lvOptions.setAdapter(adapter);
        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callBack != null)
                    callBack.onItemSelect(position);
                dismiss();
            }
        });
    }

    class OptionAdapter extends BaseAdapter {
        Context context;
        List<String> options;

        public OptionAdapter(Context context, List<String> options) {
            this.context = context;
            this.options = options;
        }

        @Override
        public int getCount() {
            return options == null ? 0 : options.size();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_option_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvOption.setText(options.get(position));
            return convertView;
        }

        class ViewHolder {
            public TextView tvOption;

            public ViewHolder(View view) {
                tvOption = (TextView) view.findViewById(R.id.tvOption);
            }
        }
    }
}
