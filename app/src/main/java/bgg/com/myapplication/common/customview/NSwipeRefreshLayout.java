package bgg.com.myapplication.common.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import bgg.com.myapplication.R;

/**
 * Created by dell on 2016/9/3.
 */
public class NSwipeRefreshLayout extends SwipeRefreshLayout {
    public NSwipeRefreshLayout(Context context) {
        super(context);
        initStyle();
    }

    public NSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle();
    }

   private void initStyle(){
       setColorSchemeResources(R.color.swipe_color_1, R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
       setSize(SwipeRefreshLayout.DEFAULT);
    }
}
