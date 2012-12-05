package com.openvehicles.OVMS.ui;

import com.actionbarsherlock.app.SherlockFragment;

public class BaseFragment extends SherlockFragment {
	
	
//	private void destroyView(View pView) {
//		if (!(pView instanceof ViewGroup)) return;
//		ViewGroup vg = (ViewGroup) pView;
//		int count = vg.getChildCount();
//		for (int i=0; i<count; i++) {
//			View v = vg.getChildAt(i);
//			if (v instanceof ImageView) onDestroyBitmap((ImageView)v);
//			if (v instanceof ViewGroup) destroyView(v);
//		}
//	}
//	
//	protected void onDestroyBitmap(ImageView pImageView) {
//		Drawable drawable = pImageView.getDrawable();
//		if (drawable instanceof BitmapDrawable) {
//			Log.d("DEBUG", "DestroyBitmap: " + drawable);
//		    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//		    Bitmap bitmap = bitmapDrawable.getBitmap();
//		    if (bitmap != null) bitmap.recycle();
//		    bitmap = null;
//		    pImageView.setImageBitmap(null);
//		}
//	}
}
