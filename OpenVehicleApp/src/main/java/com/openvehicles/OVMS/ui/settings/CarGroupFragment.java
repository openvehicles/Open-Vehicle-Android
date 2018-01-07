package com.openvehicles.OVMS.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;

public class CarGroupFragment
		extends BaseFragment
		implements OnResultCommandListener, OnItemClickListener, AdapterView.OnItemLongClickListener
{
	private CarGroupAdapter mAdapter;
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mListView = new ListView(container.getContext());
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		return mListView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentActivity activity = getCompatActivity();
		activity.setTitle(R.string.cargroup_title);
	}
	
	@Override
	public void onServiceAvailable(ApiService pService) {
		// nop
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = parent.getContext();




		final int fn = position;

		Ui.showPinDialog(context, mAdapter.getTitleRow(context, position), Long.toString(id),
				R.string.Set, false, new Ui.OnChangeListener<String>() {
			@Override
			public void onAction(String pData) {
				int val = TextUtils.isEmpty(pData) ? 0 : Integer.parseInt(pData);
				sendCommand(String.format("2,%d,%d", fn, val), CarGroupFragment.this);
				mAdapter.setFeature(fn, val);
			}
		});

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

		return false;
	}

	@Override
	public void onResultCommand(String[] result) {
		if (result.length <= 1) {
			return;
		}
		
		if (mAdapter == null) {
			mAdapter = new CarGroupAdapter();
			mListView.setAdapter(mAdapter);
		}

		int command = Integer.parseInt(result[0]);
		int rcode = Integer.parseInt(result[1]);
		String resText = (result.length > 2) ? result[2] : "";

		if (command == 2) {
			cancelCommand();
			switch (rcode) {
			case 0:
				break;
			case 1: // failed
				Toast.makeText(getActivity(), getString(R.string.err_failed, resText),
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // unsupported
				Toast.makeText(getActivity(), getString(R.string.err_unsupported_operation),
						Toast.LENGTH_SHORT).show();
				break;
			case 3: // unimplemented
				Toast.makeText(getActivity(), getString(R.string.err_unimplemented_operation),
						Toast.LENGTH_SHORT).show();
				break;
			}
			return;
		}

		if (command != 1) return; // Not for us
		
		switch (rcode) {
		case 0:
			if (result.length > 4) {
				int fn = Integer.parseInt(result[2]);
//				int fm = Integer.parseInt(result[3]);
				int fv = Integer.parseInt(result[4]);
				
				if (fn < CarGroupAdapter.FEATURES_MAX) {
					mAdapter.setFeature(fn, fv);
				}
				
//				if (fn == (fm - 1)) {
//					cancelCommand();
//					mListView.setAdapter(mAdapter);
//				}
			}
			break;
		case 1: // failed
			Toast.makeText(getActivity(), getString(R.string.err_failed, resText),
					Toast.LENGTH_SHORT).show();
			cancelCommand();
			break;
		case 2: // unsupported
			Toast.makeText(getActivity(), getString(R.string.err_unsupported_operation),
					Toast.LENGTH_SHORT).show();
			cancelCommand();
			break;
		case 3: // unimplemented
			Toast.makeText(getActivity(), getString(R.string.err_unimplemented_operation),
					Toast.LENGTH_SHORT).show();
			cancelCommand();
		}
	}
	
	private static class CarGroupAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		private static final int FEATURES_MAX = 16;
		private static final int[] mFeature = new int[FEATURES_MAX];

		@Override
		public int getCount() {
			return FEATURES_MAX;
		}

		@Override
		public Object getItem(int position) {
			return mFeature[position];
		}

		@Override
		public long getItemId(int position) {
			return mFeature[position];
		}
		
		public void setFeature(int key, int val) {
			if (key > (FEATURES_MAX-1)) return;
			mFeature[key] = val;
			notifyDataSetChanged();
		}
		
		public String getTitleRow(Context context, int position) {
			switch (position) {
			default:
				return String.format("#%d:", position);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Context context = parent.getContext();

			if (convertView == null) {
				if (mInflater == null) {
					mInflater = LayoutInflater.from(context);
				}
				convertView = mInflater.inflate(R.layout.item_keyvalue, null);
			}

			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			tv.setText(getTitleRow(context, position));
			
			tv = (TextView) convertView.findViewById(android.R.id.text2);
			tv.setText(Integer.toString(mFeature[position]));
			
			return convertView;
		}
		
	}
}
