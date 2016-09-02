package bgg.com.myapplication.common.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bgg.com.myapplication.R;

/**
 * 通用标题View
 */
public class CommonHeaderBarView extends RelativeLayout {
    public static String TAG = "CommonHeaderBarView";
    private View mTitleViews;
    private TextView mLeftTextView;
    private ImageView mLeftIconView;
    private TextView mRightTextView;
    private ImageView mRightIconView;
    private ImageView mRightIconView2;
    private TextView mMiddleTextView;
    private ImageView mMiddleIconView;
    private View mLeftArea;
    private View mTitleArea;
    private View mRightArea;
    private View mBottomLine;
    private int height;

    public CommonHeaderBarView(Context context) {
        this(context, null);
    }

    public CommonHeaderBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupSubView();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonHeaderBarView);

        // 公用属性
        initCommonAttr(a);
        // 左边
        initLeft(a);
        // 中间
        initMin(a);
        // 右边
        initRight(a);
        // 按钮事件
        initOnClick(context, a);

        a.recycle();

    }

    protected void setupSubView() {
        mTitleViews = LayoutInflater.from(getContext()).inflate(R.layout.common_title_bar, this);
        height = getContext().getResources().getDimensionPixelOffset(R.dimen.header_bar_height);
        mLeftTextView = (TextView) mTitleViews.findViewById(R.id.text_left_word);
        mLeftIconView = (ImageView) mTitleViews.findViewById(R.id.image_left_icon);
        mRightTextView = (TextView) mTitleViews.findViewById(R.id.text_right_word);
        mRightIconView = (ImageView) mTitleViews.findViewById(R.id.image_right_icon);
        mRightIconView2 = (ImageView) mTitleViews.findViewById(R.id.image_right_icon2);
        mMiddleTextView = (TextView) mTitleViews.findViewById(R.id.text_header_title);
        mMiddleIconView = (ImageView) mTitleViews.findViewById(R.id.image_header_title);
        mLeftArea = mTitleViews.findViewById(R.id.left_area);
        mTitleArea = mTitleViews.findViewById(R.id.title_area);
        mRightArea = mTitleViews.findViewById(R.id.right_area);
        mBottomLine = mTitleViews.findViewById(R.id.bottom_line);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initOnClick(Context context, TypedArray a) {
        boolean canBindClickAction = true;
        if (context.isRestricted()) {
            canBindClickAction = false;
            Log.e(TAG, "The android:onClick attribute cannot " + "be used within a restricted context");
        }

        if (canBindClickAction) {
            final String leftHandlerName = a.getString(R.styleable.CommonHeaderBarView_left_onClick);
            if (leftHandlerName != null) {
                setLeftOnClick(generateOnCliCkListener(leftHandlerName, mLeftArea));
            }

            final String rightHandlerName = a.getString(R.styleable.CommonHeaderBarView_right_onClick);
            if (rightHandlerName != null) {
                setRightOnClick(generateOnCliCkListener(rightHandlerName, mRightArea));
            }

            final String rightHandlerName2 = a.getString(R.styleable.CommonHeaderBarView_right2_onClick);
            if (rightHandlerName2 != null) {
                setRightOnClick2(generateOnCliCkListener(rightHandlerName2, mRightArea));
            }

            final String middleHandlerName = a.getString(R.styleable.CommonHeaderBarView_mid_onClick);
            if (middleHandlerName != null) {
                setMiddleOnClick(generateOnCliCkListener(middleHandlerName, mTitleArea));
            }
        }
    }

    // 初始化左边的按钮属性
    private void initLeft(TypedArray a) {
        // 文字颜色
        ColorStateList leftTextColor = a.getColorStateList(R.styleable.CommonHeaderBarView_left_textColor);
        if (leftTextColor != null) {
            mLeftTextView.setTextColor(leftTextColor);
        }

        // 文字大小
        float leftTextSize = a.getDimension(R.styleable.CommonHeaderBarView_left_textSize, -1);
        if (leftTextSize != -1) {
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        }

        // 文字内容
        CharSequence leftText = a.getText(R.styleable.CommonHeaderBarView_left_text);
        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }

        // 图标
        int leftIconResId = a.getResourceId(R.styleable.CommonHeaderBarView_left_icon, 0);
        if (leftIconResId != 0) {
            setLeftIcon(leftIconResId);
            // 获取图标宽高数值
            float leftIconWidth = a.getDimension(R.styleable.CommonHeaderBarView_left_icon_width, -1);
            float leftIconHegiht = a.getDimension(R.styleable.CommonHeaderBarView_left_icon_height, -1);
            ViewGroup.LayoutParams params = mLeftIconView.getLayoutParams();
            if (leftIconWidth != -1) {
                params.width = (int) leftIconWidth;
            }
            if (leftIconHegiht != -1) {
                params.height = (int) leftIconHegiht;
            }
            mLeftIconView.setLayoutParams(params);
        }
    }

    // 初始化中间的按钮属性
    private void initMin(TypedArray a) {
        // 文字颜色
        ColorStateList midTextColor = a.getColorStateList(R.styleable.CommonHeaderBarView_mid_textColor);
        if (midTextColor != null) {
            mMiddleTextView.setTextColor(midTextColor);
        }

        // 文字大小
        float midTextSize = a.getDimension(R.styleable.CommonHeaderBarView_mid_textSize, -1);
        if (midTextSize != -1) {
            mMiddleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, midTextSize);
        }
        // 文字内容
        CharSequence midText = a.getText(R.styleable.CommonHeaderBarView_mid_text);
        if (!TextUtils.isEmpty(midText)) {
            setMiddleText(midText);
        }

        // 图标
        int midIconResId = a.getResourceId(R.styleable.CommonHeaderBarView_mid_icon, 0);
        if (midIconResId != 0) {
            setMiddleIcon(midIconResId);
            // 获取图标宽高数值
            float midIconWidth = a.getDimension(R.styleable.CommonHeaderBarView_mid_icon_width, -1);
            float midIconHegiht = a.getDimension(R.styleable.CommonHeaderBarView_mid_icon_height, -1);
            ViewGroup.LayoutParams params = mMiddleIconView.getLayoutParams();
            if (midIconWidth != -1) {
                params.width = (int) midIconWidth;
            }
            if (midIconHegiht != -1) {
                params.height = (int) midIconHegiht;
            }
            mMiddleIconView.setLayoutParams(params);
        }

    }

    // 初始化右边的按钮属性
    private void initRight(TypedArray a) {
        // 文字颜色
        ColorStateList rightTextColor = a.getColorStateList(R.styleable.CommonHeaderBarView_right_textColor);
        if (rightTextColor != null) {
            mRightTextView.setTextColor(rightTextColor);
        }

        // 文字大小
        float rightTextSize = a.getDimension(R.styleable.CommonHeaderBarView_right_textSize, -1);
        if (rightTextSize != -1) {
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }

        // 文字内容
        CharSequence rightText = a.getText(R.styleable.CommonHeaderBarView_right_text);
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }

        // 第一个图标
        int rightIconResId = a.getResourceId(R.styleable.CommonHeaderBarView_right_icon, 0);
        if (rightIconResId != 0) {
            setRightIcon(rightIconResId);
            // 获取图标宽高数值
            float rightIconWidth = a.getDimension(R.styleable.CommonHeaderBarView_right_icon_width, -1);
            float rightconHegiht = a.getDimension(R.styleable.CommonHeaderBarView_right_icon_height, -1);
            ViewGroup.LayoutParams params = mRightIconView.getLayoutParams();
            if (rightIconWidth != -1) {
                params.width = (int) rightIconWidth;
            }
            if (rightconHegiht != -1) {
                params.height = (int) rightconHegiht;
            }
            mRightIconView.setLayoutParams(params);
        }

        // 第二个图标
        int rightIconResId2 = a.getResourceId(R.styleable.CommonHeaderBarView_right2_icon, 0);
        if (rightIconResId2 != 0) {
            setRightIcon2(rightIconResId2);
            // 获取图标宽高数值
            float rightIconWidth = a.getDimension(R.styleable.CommonHeaderBarView_right2_icon_width, -1);
            float rightconHegiht = a.getDimension(R.styleable.CommonHeaderBarView_right2_icon_height, -1);
            ViewGroup.LayoutParams params = mRightIconView2.getLayoutParams();
            if (rightIconWidth != -1) {
                params.width = (int) rightIconWidth;
            }
            if (rightconHegiht != -1) {
                params.height = (int) rightconHegiht;
            }
            mRightIconView2.setLayoutParams(params);
        }
    }

    // 初始化所有的按钮属性（文字大小和颜色（可以是：单独的颜色值、颜色资源索引或者是selector））
    private void initCommonAttr(TypedArray a) {
        // 文字颜色
        ColorStateList allTextColor = a.getColorStateList(R.styleable.CommonHeaderBarView_all_textColor);
        if (allTextColor != null) {
            mLeftTextView.setTextColor(allTextColor);
            mMiddleTextView.setTextColor(allTextColor);
            mRightTextView.setTextColor(allTextColor);
        }

        // 文字大小
        float allTextSize = a.getDimension(R.styleable.CommonHeaderBarView_all_textSize, -1);
        if (allTextSize != -1) {
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, allTextSize);
            mMiddleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, allTextSize);
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, allTextSize);
        }
    }

    protected OnClickListener generateOnCliCkListener(final String handlerName, final View view) {
        return new OnClickListener() {
            private Method mHandler;

            public void onClick(View v) {
                if (mHandler == null) {
                    try {
                        mHandler = getContext().getClass().getMethod(handlerName, View.class);
                    } catch (NoSuchMethodException e) {
                        int id = getId();
                        String idText = id == NO_ID ? ""
                                : " with id '" + getContext().getResources().getResourceEntryName(id) + "'";
                        System.out.print("Could not find a method " + handlerName + "(View) in the activity "
                                + getContext().getClass() + " for onClick handler" + " on view " + getClass() + idText);
                    }
                }

                try {
                    mHandler.invoke(getContext(), v);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Could not execute non " + "public method of the activity", e);
                } catch (InvocationTargetException e) {
                    System.out.print("Could not execute " + "method of the activity");
                }
            }
        };
    }

    /**
     * 设置标题背景颜色
     *
     * @param color
     */
    public void setHeaderBackgroundColor(int color) {
        mTitleViews.setBackgroundColor(color);
    }

    /**
     * 设置标题背景颜色
     *
     * @param color
     * @param isShowBottomLine 是否显示标题底部的分割线
     */
    public void setHeaderBackgroundColor(int color, boolean isShowBottomLine) {
        mTitleViews.setBackgroundColor(color);
        showBottomLine(isShowBottomLine);
    }

    /**
     * 设置标题背景图片资源
     *
     * @param resid
     */
    public void setHeaderBackgroundResources(int resid) {
        mTitleViews.setBackgroundResource(resid);
    }

    /**
     * 设置标题背景图片资源
     *
     * @param resid
     * @param isShowBottomLine 是否显示标题底部的分割线
     */

    public void setHeaderBackgroundResources(int resid, boolean isShowBottomLine) {
        mTitleViews.setBackgroundResource(resid);
        showBottomLine(isShowBottomLine);
    }


    /**
     * 是否显示底部分割线
     *
     * @param isShow
     */
    public void showBottomLine(boolean isShow) {
        if (isShow) {
            mBottomLine.setVisibility(View.VISIBLE);
        } else {
            mBottomLine.setVisibility(View.GONE);
        }
    }

    public void setLeftText(CharSequence text) {
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText(text);
    }


    public void setLeftIcon(int resId) {
        mLeftIconView.setVisibility(View.VISIBLE);
        mLeftIconView.setBackgroundResource(resId);
    }


    public void setRightText(CharSequence text) {
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText(text);
    }


    public void setRightIcon(int resId) {
        mRightIconView.setVisibility(View.VISIBLE);
        mRightIconView.setBackgroundResource(resId);
    }

    public void setRightIcon2(int resId) {
        mRightIconView2.setVisibility(View.VISIBLE);
        mRightIconView2.setBackgroundResource(resId);
    }

    public void setMiddleText(CharSequence text) {
        mMiddleIconView.setVisibility(View.GONE);
        mMiddleTextView.setVisibility(View.VISIBLE);
        mMiddleTextView.setText(text);
    }


    public void setMiddleIcon(int resId) {
        mMiddleIconView.setVisibility(View.VISIBLE);
        mMiddleTextView.setVisibility(View.GONE);
        mMiddleIconView.setBackgroundResource(resId);
    }

    public void setLeftOnClick(OnClickListener listener) {
        mLeftArea.setOnClickListener(listener);
    }

    public void showLeftArea(boolean isShow) {
        mLeftArea.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 右侧文字监听
     */
    public void setRightTextOnClick(OnClickListener listener) {
        // mRightArea.setOnClickListener(listener);
        mRightTextView.setOnClickListener(listener);
    }

    /**
     * 右侧第一个图标按钮监听
     */
    public void setRightOnClick(OnClickListener listener) {
        // mRightArea.setOnClickListener(listener);
        mRightIconView.setOnClickListener(listener);
    }

    /**
     * 右侧第二个图标按钮监听
     */
    public void setRightOnClick2(OnClickListener listener) {
        mRightIconView2.setOnClickListener(listener);
    }

    public void setMiddleOnClick(OnClickListener listener) {
        mTitleArea.setOnClickListener(listener);
    }

    /**
     * 设置左边icon控件的大小（单位：像素）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setLeftIconSize(int width, int height) {
        ViewGroup.LayoutParams params = mLeftIconView.getLayoutParams();
        if (width >= 0) {
            params.width = width;
        }

        if (height >= 0) {
            params.height = height;
        }
        mLeftIconView.setLayoutParams(params);
    }

    /**
     * 设置左边icon控件的大小（单位：dp）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setLeftIconSize(float width, float height) {
        ViewGroup.LayoutParams params = mLeftIconView.getLayoutParams();
        if (width >= 0) {
            params.width = dip2px(getContext(), width);
        }

        if (height >= 0) {
            params.height = dip2px(getContext(), height);
        }
        mLeftIconView.setLayoutParams(params);
    }

    /**
     * 设置右边icon控件的大小（单位：像素）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setRightIconSize(int width, int height) {
        ViewGroup.LayoutParams params = mRightIconView.getLayoutParams();
        if (width >= 0) {
            params.width = width;
        }

        if (height >= 0) {
            params.height = height;
        }
        mRightIconView.setLayoutParams(params);
    }

    /**
     * 设置右边icon控件的大小（单位：dp）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setRightIconSize(float width, float height) {
        ViewGroup.LayoutParams params = mRightIconView.getLayoutParams();
        if (width >= 0) {
            params.width = dip2px(getContext(), width);
        }

        if (height >= 0) {
            params.height = dip2px(getContext(), height);
        }
        mRightIconView.setLayoutParams(params);
    }

    /**
     * 设置右边第二个图标控件的大小（单位：像素）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setRightIconSize2(int width, int height) {
        ViewGroup.LayoutParams params = mRightIconView2.getLayoutParams();
        if (width >= 0) {
            params.width = width;
        }

        if (height >= 0) {
            params.height = height;
        }
        mRightIconView2.setLayoutParams(params);
    }

    /**
     * 设置右边第二个图标控件的大小（单位：dp）
     * </p>
     * 如果只想设置width，那么height可以传递小于0的参数。
     *
     * @param width
     * @param height
     */
    public void setRightIconSize2(float width, float height) {
        ViewGroup.LayoutParams params = mRightIconView2.getLayoutParams();
        if (width >= 0) {
            params.width = dip2px(getContext(), width);
        }

        if (height >= 0) {
            params.height = dip2px(getContext(), height);
        }
        mRightIconView2.setLayoutParams(params);
    }

    /**
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
