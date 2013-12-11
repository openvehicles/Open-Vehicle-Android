package com.openvehicles.OVMS.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;

public class ControlParametersFragment extends BaseFragment implements OnResultCommandListenner, OnItemClickListener {
	private ControlParametersAdapter mAdapter;
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mListView = new ListView(container.getContext());
		mListView.setOnItemClickListener(this);
		return mListView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SherlockFragmentActivity activity = getSherlockActivity(); 
		activity.setTitle(R.string.Parameters);
	}
	
	@Override
	public void onServiceAvailable(ApiService pService) {
		pService.sendCommand("3", this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = parent.getContext();
		final int fn = position;
		final String val = (String)mAdapter.getItem(position);
		boolean isPasswd = position == ControlParametersAdapter.PARAM_REGPASS ||
				position == ControlParametersAdapter.PARAM_NETPASS1 ||
				position == ControlParametersAdapter.PARAM_GPRSPASS;
		
		Ui.showEditDialog(context, mAdapter.getTitleRow(context, position), val,  
				R.string.Set, isPasswd, new Ui.OnChangeListener<String>() {
			@Override
			public void onAction(String pData) {
				if (pData.equals(val)) return;
				
				sendCommand(String.format("4,%d,%s", fn, pData), ControlParametersFragment.this);
				mAdapter.setParam(fn, pData);
			}
		});
	}
	
	@Override
	public void onResultCommand(String[] result) {
		if (result.length <= 1) {
			return;
		}
		
		if (mAdapter == null) {
			mAdapter = new ControlParametersAdapter();
			mListView.setAdapter(mAdapter);
		}

		int command = Integer.parseInt(result[0]);
		int rcode = Integer.parseInt(result[1]);
		
		if (command == 4) {
			cancelCommand();
			switch (rcode) {
			case 0:
				break;
			case 1: // failed
				Toast.makeText(getActivity(), getString(R.string.err_failed, result[2]),
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

		if (command != 3) return; // Not for us
		
		switch (rcode) {
		case 0:
			if (result.length > 4) {
				int fn = Integer.parseInt(result[2]);
//				int fm = Integer.parseInt(result[3]);
				String fv = result[4];
				
				if (fn < ControlParametersAdapter.PARAM_FEATURE_S) {
					mAdapter.setParam(fn, fv);
				}
				
//				if (fn == (fm - 1)) {
//					cancelCommand();
//					mListView.setAdapter(mAdapter);
//				}
			}
			break;
		case 1: // failed
			Toast.makeText(getActivity(), getString(R.string.err_failed, result[2]),
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
	
	private static class ControlParametersAdapter extends BaseAdapter {
//		private static final int PARAM_MAX		= 32;
//		private static final int PARAM_MAX_LENGTH = 32;
//		private static final int PARAM_BANKSIZE	= 8;

		static final int PARAM_REGPHONE  = 0x00;
		static final int PARAM_REGPASS   = 0x01;
		static final int PARAM_MILESKM   = 0x02;
		static final int PARAM_NOTIFIES  = 0x03;
		static final int PARAM_SERVERIP  = 0x04;
		static final int PARAM_GPRSAPN   = 0x05;
		static final int PARAM_GPRSUSER  = 0x06;
		static final int PARAM_GPRSPASS   = 0x07;
		static final int PARAM_MYID      = 0x08;
		static final int PARAM_NETPASS1   = 0x09;
		static final int PARAM_PARANOID  = 0x0A;
		static final int PARAM_S_GROUP1  = 0x0B;
		static final int PARAM_S_GROUP2  = 0x0C;

		public static final int PARAM_FEATURE_S = 0x18;
//		private static final int PARAM_FEATURE8  = 0x18;
//		private static final int PARAM_FEATURE9  = 0x19;
//		private static final int PARAM_FEATURE10 = 0x1A;
//		private static final int PARAM_FEATURE11 = 0x1B;
//		private static final int PARAM_FEATURE12 = 0x1C;
//		private static final int PARAM_FEATURE13 = 0x1D;
//		private static final int PARAM_FEATURE14 = 0x1E;
//		private static final int PARAM_FEATURE15 = 0x1F;
		
		private LayoutInflater mInflater;
		private static final String[] mParams = new String[PARAM_FEATURE_S];

		@Override
		public int getCount() {
			return PARAM_FEATURE_S;
		}

		@Override
		public Object getItem(int position) {
			return mParams[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void setParam(int key, String val) {
			if (key > (PARAM_FEATURE_S-1)) return;
			mParams[key] = val;
			notifyDataSetChanged();
		}
		
		public String getTitleRow(Context context, int position) {
			switch (position) {
			case PARAM_REGPHONE:
				return context.getString(R.string.lb_cp_registered_telephone, position);
			case PARAM_REGPASS:
				return context.getString(R.string.lb_cp_module_password, position);
			case PARAM_MILESKM:
				return context.getString(R.string.lb_cp_miles_km, position);
			case PARAM_NOTIFIES:
				return context.getString(R.string.lb_cp_notifications, position);
			case PARAM_SERVERIP:
				return context.getString(R.string.lb_cp_ovms_server_ip, position);
			case PARAM_GPRSAPN:
				return context.getString(R.string.lb_cp_cellular_network_apn, position);
			case PARAM_GPRSUSER:
				return context.getString(R.string.lb_cp_cellular_network_user, position);
			case PARAM_GPRSPASS:
				return context.getString(R.string.lb_cp_cellular_network_password, position);
			case PARAM_MYID:
				return context.getString(R.string.lb_cp_vehicle_id, position);
			case PARAM_NETPASS1:
				return context.getString(R.string.lb_cp_server_password, position);
			case PARAM_PARANOID:
				return context.getString(R.string.lb_cp_paranoid_mode, position);
			case PARAM_S_GROUP1:
				return context.getString(R.string.lb_cp_social_group_1, position);
			case PARAM_S_GROUP2:
				return context.getString(R.string.lb_cp_social_group_2, position);
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
//				TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
//				tv.setTextAppearance(context, android.R.style.TextAppearance_Small);
//				tv = (TextView) convertView.findViewById(android.R.id.text2);
//				tv.setGravity(Gravity.RIGHT);
			}
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			tv.setText(getTitleRow(context, position));
			
			tv = (TextView) convertView.findViewById(android.R.id.text2);
			tv.setText(mParams[position]);
			
			return convertView;
		}
		
	}
}
