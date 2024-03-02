package com.openvehicles.OVMS.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;

public class FeaturesFragment extends BaseFragment implements OnResultCommandListener, OnItemClickListener {
	private static final String TAG = "FeaturesFragment";

	private FeaturesAdapter mAdapter;
	private ListView mListView;
	private int mEditPosition;
	private CarData mCarData;
	private ApiService mService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// get data of car to edit:
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = CarsStorage.INSTANCE.getStoredCars().get(mEditPosition);
		} else {
			mCarData = CarsStorage.INSTANCE.getSelectedCarData();
		}
		Log.d(TAG, "mEditPosition=" + mEditPosition + " → mCarData=" + mCarData);

		mListView = new ListView(container.getContext());
		mListView.setOnItemClickListener(this);

		// create storage adapter:
		mAdapter = new FeaturesAdapter();
		mListView.setAdapter(mAdapter);

		createProgressOverlay(inflater, container, true);

		return mListView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentActivity activity = getActivity();
		activity.setTitle(R.string.Features);
	}

	@Override
	public void onServiceAvailable(ApiService service) {
		mService = service;
		requestData();
	}

	@Override
	public void update(CarData carData) {
	}

	private void requestData() {
		// send request:
		showProgressOverlay();
		mService.sendCommand("1", this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = parent.getContext();
		final int fn = position;
		Ui.showEditDialog(context,
				mAdapter.getTitleRow(context, position),
				(String) mAdapter.getItem(position),
				R.string.Set, false, new Ui.OnChangeListener<String>() {
					@Override
					public void onAction(String pData) {
						sendCommand(String.format("2,%d,%s", fn, pData), FeaturesFragment.this);
						mAdapter.setFeature(fn, pData);
					}
				});
		/*
		Ui.showPinDialog(context, mAdapter.getTitleRow(context, position), Long.toString(id),
				R.string.Set, false, new Ui.OnChangeListener<String>() {
			@Override
			public void onAction(String pData) {
				int val = TextUtils.isEmpty(pData) ? 0 : Integer.parseInt(pData);
				sendCommand(String.format("2,%d,%d", fn, val), FeaturesFragment.this);
				mAdapter.setFeature(fn, val);
			}
		});
		*/
	}
	
	@Override
	public void onResultCommand(String[] result) {

		if (result.length < 2)
			return;
		if (getContext() == null)
			return;

		int command = Integer.parseInt(result[0]);
		int resCode = Integer.parseInt(result[1]);
		String resText = (result.length > 2) ? result[2] : "";

		if (command == 2) {
			// Set feature (single) response:
			cancelCommand();
			switch (resCode) {
			case 0:
				Toast.makeText(getActivity(), getString(R.string.msg_ok),
						Toast.LENGTH_SHORT).show();
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

		// Get feature list (multiple) responses:
		switch (resCode) {
		case 0:
			if (result.length > 4) {
				int fn = Integer.parseInt(result[2]);
				int fm = Integer.parseInt(result[3]);
				String fv = result[4];

				stepProgressOverlay(fn + 1, fm);

				if (fm != mAdapter.mFeaturesMax) {
					mAdapter.setFeaturesMax(fm);
				}

				if (fn < mAdapter.mFeaturesMax) {
					mAdapter.setFeature(fn, fv);
				}

				if (fn == (fm - 1)) {
					// got all, cancel listening:
					cancelCommand();
				}
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
	
	private class FeaturesAdapter extends BaseAdapter {
		public int mFeaturesMax = 16;

		// Standard features:
		private static final int FEATURE_SPEEDO			= 0x00; // Speedometer feature
		private static final int FEATURE_STREAM			= 0x08; // Location streaming feature
		private static final int FEATURE_MINSOC			= 0x09; // Minimum SOC feature
		private static final int FEATURE_CARBITS		= 0x0E; // Various ON/OFF features (bitmap)
		private static final int FEATURE_CANWRITE		= 0x0F; // CAN bus can be written to

		// Renault Twizy:
		private static final int FEATURE_GPSLOGINT				= 0x00; // GPS log interval [seconds]
		private static final int FEATURE_KICKDOWN_THRESHOLD		= 0x01; // Kickdown threshold (pedal change)
		private static final int FEATURE_KICKDOWN_COMPZERO 		= 0x02; // Kickdown pedal compensation zero point
		private static final int FEATURE_CHARGEMODE 			= 0x06; // Charge mode (0-1)
		private static final int FEATURE_CHARGEPOWER 			= 0x07; // Charge power level (0-7)
		private static final int FEATURE_SUFFSOC 				= 0x0A; // Charge alert: sufficient SOC
		private static final int FEATURE_SUFFRANGE 				= 0x0B; // Charge alert: sufficient range
		private static final int FEATURE_MAXRANGE 				= 0x0C; // Max ideal range (100% SOC)
		private static final int FEATURE_CAPACITY 				= 0x0D; // Battery capacity average (SOH%)
		private static final int FEATURE_CAPACITY_NOM_AH		= 0x10; // Battery nominal capacity (Ah)

		// VW e-Up:
		private static final int FEATURE_MODELYEAR				= 0x14; // model year
		private static final int FEATURE_REMOTE_AC_TEMP			= 0x15; // temperature for remote AC
		private static final int FEATURE_REMOTE_AC_ON_BAT		= 0x16; // allow remote AC on battery power?

		// The FEATURE_CARBITS feature is a set of ON/OFF bits to control different
		// miscelaneous aspects of the system. The following bits are defined:
		//private static final int FEATURE_CB_2008		= 0x01; // Set to 1 to mark the car as 2008/2009
		//private static final int FEATURE_CB_SAD_SMS		= 0x02; // Set to 1 to suppress "Access Denied" SMS
		//private static final int FEATURE_CB_SOUT_SMS	= 0x04; // Set to 1 to suppress all outbound SMS

		private LayoutInflater mInflater;
		private String[] mFeature = new String[mFeaturesMax];

		public void setFeaturesMax(int cnt) {
			mFeaturesMax = cnt;
			mFeature = new String[mFeaturesMax];
		}

		@Override
		public int getCount() {
			return mFeaturesMax;
		}

		@Override
		public Object getItem(int position) {
			return mFeature[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void setFeature(int key, String val) {
			if (key > (mFeaturesMax -1)) return;
			mFeature[key] = val;
			notifyDataSetChanged();
		}
		
		public String getTitleRow(Context context, int position) {

			// Renault Twizy:
			if (mCarData.car_type.equals("RT")) {
				switch (position) {
					case FEATURE_GPSLOGINT:
						return context.getString(R.string.lb_ft_rt_gpslogint, position);
					case FEATURE_KICKDOWN_THRESHOLD:
						return context.getString(R.string.lb_ft_rt_kickdown_threshold, position);
					case FEATURE_KICKDOWN_COMPZERO:
						return context.getString(R.string.lb_ft_rt_kickdown_compzero, position);
					case FEATURE_CHARGEMODE:
						return context.getString(R.string.lb_ft_rt_chargemode, position);
					case FEATURE_CHARGEPOWER:
						return context.getString(R.string.lb_ft_rt_chargepower, position);
					case FEATURE_SUFFSOC:
						return context.getString(R.string.lb_ft_rt_suffsoc, position);
					case FEATURE_SUFFRANGE:
						return context.getString(R.string.lb_ft_rt_suffrange, position);
					case FEATURE_MAXRANGE:
						return context.getString(R.string.lb_ft_rt_maxrange, position);
					case FEATURE_CAPACITY:
						return context.getString(R.string.lb_ft_rt_capacity, position);
					case FEATURE_CAPACITY_NOM_AH:
						return context.getString(R.string.lb_ft_rt_capacity_nom_ah, position);

					default:
						// fall through to standard
				}
			}
			// Nissan Leaf:
			if (mCarData.car_type.equals("NL")) {
				switch (position) {
					case FEATURE_SUFFSOC:
						return context.getString(R.string.lb_ft_rt_suffsoc, position);
					case FEATURE_SUFFRANGE:
						return context.getString(R.string.lb_ft_rt_suffrange, position);
					default:
						// fall through to standard
				}
			}
			// VW e-Up:
			if (mCarData.car_type.equals("VWUP")) {
				switch (position) {
					case FEATURE_CHARGEMODE:
						return context.getString(R.string.lb_ft_rt_chargemode, position);
					case FEATURE_SUFFSOC:
						return context.getString(R.string.lb_ft_rt_suffsoc, position);
					case FEATURE_MODELYEAR:
						return context.getString(R.string.lb_ft_modelyear, position);
					case FEATURE_REMOTE_AC_TEMP:
						return context.getString(R.string.lb_ft_remote_ac_temp, position);
					case FEATURE_REMOTE_AC_ON_BAT:
						return context.getString(R.string.lb_ft_remote_ac_on_bat, position);

					default:
						// fall through to standard
				}
			}

			// Standard:
			switch (position) {
				// Standard features:
				case FEATURE_SPEEDO:
					return context.getString(R.string.lb_ft_digital_speedo, position);
				case FEATURE_STREAM:
					return context.getString(R.string.lb_ft_gps_stream, position);
				case FEATURE_MINSOC:
					return context.getString(R.string.lb_ft_minimum_soc, position);
				case FEATURE_CARBITS:
					return context.getString(R.string.lb_ft_car_bits, position);
				case FEATURE_CANWRITE:
					return context.getString(R.string.lb_ft_can_write, position);

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
			tv.setText(mFeature[position]);
			
			return convertView;
		}
		
	}
}
