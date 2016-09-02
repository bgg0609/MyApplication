package bgg.com.commlibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

/**
 *  远程图片加载
 */
public class ABOImageView extends ImageView {

	private int defaultImageResId;
	private int errorImageResId;
	private String url = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	public int getErrorImageResId() {
		return errorImageResId;
	}

	public void setErrorImageResId(int errorImageResId) {
		this.errorImageResId = errorImageResId;
	}

	public ABOImageView(Context context) {
		super(context);
	}

	public ABOImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ABOImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception e) {
			System.out.println("MyImageView  -> onDraw() Canvas: trying to use a recycled bitmap");
		}
	}

	/**
	 * 开始加载图片
	 * 
	 * @param imgUrl  图片的URL
	 */
	public void loadImage(String imgUrl) {
		DrawableTypeRequest<String> request = Glide.with(getContext()).load(imgUrl);
		if(defaultImageResId != 0){
			request.placeholder(defaultImageResId);
		} else if(errorImageResId != 0){
			request.error(errorImageResId);
		}
		// 设置图片切换动画时间（单位：毫秒）
		request.crossFade(150);
		request.into(this);
	}
	
	public void loadFinish(Bitmap bitmap) {

	}
}
