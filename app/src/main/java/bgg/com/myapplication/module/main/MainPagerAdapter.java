package bgg.com.myapplication.module.main;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * @author lailele
 *
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> mFragments;
	
	public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
		super(fm);
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragments != null ? mFragments.get(arg0) : null;
	}

	@Override
	public int getCount() {
		return mFragments != null ? mFragments.size() : 0;
	}

	
	
	

}
