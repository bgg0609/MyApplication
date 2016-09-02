/**
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.dooland.net;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Helper that handles loading and caching images from remote URLs.
 *
 * The simple way to use this class is to call
 * {@link FixedImageLoader#get(String, ImageListener)} and to pass in the
 * default image listener provided by
 * {@link FixedImageLoader#getImageListener(ImageView, int, int)}. Note that all
 * function calls to this class must be made from the main thead, and all
 * responses will be delivered to the main thread as well.
 */
public class FixedImageLoader extends ImageLoader {

	/**
	 * Constructs a new ImageLoader.
	 * 
	 * @param queue
	 *            The RequestQueue to use for making image requests.
	 * @param imageCache
	 *            The cache to use as an L1 cache.
	 */
	public FixedImageLoader(RequestQueue queue, ImageCache imageCache) {
		super(queue, imageCache);
	}

	protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth,
			int maxHeight, ScaleType scaleType, final String cacheKey) {
		return new NeverOutDateImageRequest(requestUrl, new Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				onGetImageSuccess(cacheKey, response);
			}
		}, maxWidth, maxHeight, scaleType, Config.RGB_565, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				onGetImageError(cacheKey, error);
			}
		});
	}

}
