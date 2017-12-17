package com.openvehicles.OVMS.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.openvehicles.OVMS.R;

public class BaseFragmentActivity extends ApiActivity {
	private static final String EXT_FRAGMENT_CLASS_NAME = "ext_fragmentclassname";
	private static final String EXT_ONLY_ORIENTATION = "ext_only_orientation";
	private static final String EXT_FOR_RESULT = "ext_for_result";
	
	public static void show(Context pContext, Class<? extends Fragment> pClazz,
			Bundle pArgs, int pOnlyOrientation) {
		Intent intent = new Intent(pContext, BaseFragmentActivity.class);
		intent.putExtra(EXT_FRAGMENT_CLASS_NAME, pClazz.getName());
		intent.putExtra(EXT_ONLY_ORIENTATION, pOnlyOrientation);
		if (pArgs != null) intent.putExtras(pArgs);
		pContext.startActivity(intent);
	}
	
	public static void showForResult(Fragment pFragment, Class<? extends Fragment> pClazz,
			Bundle pArgs, int pRequestCode, int pOnlyOrientation) {
		Intent intent = new Intent(pFragment.getActivity(), BaseFragmentActivity.class);
		intent.putExtra(EXT_FRAGMENT_CLASS_NAME, pClazz.getName());
		intent.putExtra(EXT_ONLY_ORIENTATION, pOnlyOrientation);
		intent.putExtra(EXT_FOR_RESULT, true);
		if (pArgs != null) intent.putExtras(pArgs);
		pFragment.startActivityForResult(intent, pRequestCode);
	}

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String fragmentClassName = getIntent().getStringExtra(EXT_FRAGMENT_CLASS_NAME);
        if (TextUtils.isEmpty(fragmentClassName)) {
        	finish();
        	return;
        }
        
        int orientation = getIntent().getIntExtra(EXT_ONLY_ORIENTATION, Configuration.ORIENTATION_UNDEFINED);
        if (orientation != Configuration.ORIENTATION_UNDEFINED && orientation !=  
    			getResources().getConfiguration().orientation) {
        	finish();
        	return;
    	}
        
        setDefaultContentView();
        if (getCurrentFragment() == null) setStartFragment(fragmentClassName);
    }
    
	protected void setDefaultContentView() {
        FrameLayout fl = new FrameLayout(this);
        fl.setId(R.id.content);
        setContentView(fl);
	}
	
	public int getContentId() {
		return R.id.content;
	}
	
	public Fragment getCurrentFragment() {
		return getSupportFragmentManager().findFragmentById(getContentId());
	}
	

	public Fragment setStartFragment(Class<? extends Fragment> pClazz) {
        return setStartFragment(pClazz, getIntent().getExtras());
	}
	
	public Fragment setStartFragment(String pClassName) {
        return setStartFragment(pClassName, getContentId(), getIntent().getExtras());
	}
	
	public Fragment setStartFragment(Class<? extends Fragment> pClazz, int pId) {
        return setStartFragment(pClazz, pId, getIntent().getExtras());
	}

	public Fragment setStartFragment(Class<? extends Fragment> pClazz, Bundle pArgs) {
		return setStartFragment(pClazz, getContentId(), pArgs);
	}
	
	public Fragment setStartFragment(Class<? extends Fragment> pClazz, int pId, Bundle pArgs) {
		return setStartFragment(pClazz.getName(), pId, pArgs);
	}
	
	public Fragment setStartFragment(String pClassName, int pId, Bundle pArgs) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		Fragment fragment = fm.findFragmentById(pId);
		if (fragment == null) {
			fragment = Fragment.instantiate(this, pClassName, pArgs);
			ft.add(pId, fragment);
		} else {
			if (!fragment.getClass().getName().equals(pClassName)) {
				fragment = Fragment.instantiate(this, pClassName, pArgs);
				ft.replace(pId, fragment);
			}
		}
		ft.commit();
		return fragment;
	}
	
	public Fragment replaceFragment(Class<? extends Fragment> pClazz, int pId, Bundle pArgs) {
		return replaceFragment(pClazz.getName(), pId, pArgs);
	}
	
	public Fragment replaceFragment(String pClassName, int pId, Bundle pArgs) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		Fragment fragment = Fragment.instantiate(this, pClassName, pArgs);
		ft.replace(pId, fragment);
		ft.commit();
		return fragment;
	}
	
	public Fragment setNextFragment(Class<? extends Fragment> pClazz) {
        return setNextFragment(pClazz, getIntent().getExtras());
	}
	
	public Fragment setNextFragment(Class<? extends Fragment> pClazz, Bundle pArgs) {
		Fragment fragment = Fragment.instantiate(this, pClazz.getName(), pArgs);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(getContentId(), fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        return fragment;
	}
	
}
