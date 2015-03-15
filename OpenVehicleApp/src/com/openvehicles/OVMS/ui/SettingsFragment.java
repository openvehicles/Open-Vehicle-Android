package com.openvehicles.OVMS.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.settings.CarEditorFragment;
import com.openvehicles.OVMS.ui.settings.CarInfoFragment;
import com.openvehicles.OVMS.ui.settings.ControlFragment;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;

public class SettingsFragment extends BaseFragment implements
		OnItemClickListener {
	private ListView mListView;
	private AppPrefes appPrefes;
	private Database database;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mListView = new ListView(container.getContext());
		appPrefes = new AppPrefes(container.getContext(), "ovms");
		database = new Database(container.getContext());
		return mListView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(new SettingsAdapter(getActivity(), CarsStorage
				.get().getStoredCars()));
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.add, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mi_add) {
			edit(-1);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void update(CarData pCarData) {
		int count = mListView.getCount();
		for (int i = 0; i < count; i++) {
			if (pCarData == mListView.getItemAtPosition(i)) {
				mListView.setItemChecked(i, true);
				break;
			}
		}
		mListView.invalidateViews();
	}

	@Override
	public void onServiceAvailable(ApiService pService) {
		if (pService.isLoggedIn())
			update(pService.getCarData());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (view.getId()) {
			case R.id.btn_edit:
				edit(position);
				return;
			case R.id.btn_control:
				control(position);
				return;
			case R.id.btn_info:
				info(position);
				return;
			default:
				CarData carData = (CarData) parent.getAdapter().getItem(position);
				CarsStorage.get().setSelectedCarId(carData.sel_vehicleid);
				appPrefes.SaveData("sel_vehicle_label", carData.sel_vehicle_label);
				appPrefes.SaveData("autotrack", "on");
				appPrefes.SaveData("Id", database.getConnectionFilter(carData.sel_vehicle_label));
				changeCar(carData);
		}
	}

	private void edit(int pPosition) {
		Bundle args = new Bundle();
		args.putInt("position", pPosition);
		BaseFragmentActivity.show(getActivity(), CarEditorFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}

	private void control(int pPosition) {
		Bundle args = new Bundle();
		args.putInt("position", pPosition);
		BaseFragmentActivity.show(getActivity(), ControlFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}

	private void info(int pPosition) {
		Bundle args = new Bundle();
		args.putInt("position", pPosition);
		BaseFragmentActivity.show(getActivity(), CarInfoFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}

	private static class SettingsAdapter extends BaseAdapter implements
			OnClickListener {
		private final LayoutInflater mInflater;
		private final ArrayList<CarData> mItems;
		private ListView mListView;

		public SettingsAdapter(Context pContext, ArrayList<CarData> pItems) {
			mItems = pItems;
			mInflater = LayoutInflater.from(pContext);
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_car, null);
			}

			ImageButton btnEdit = (ImageButton) convertView
					.findViewById(R.id.btn_edit);
			ImageButton btnControl = (ImageButton) convertView
					.findViewById(R.id.btn_control);
			ImageButton btnInfo = (ImageButton) convertView
					.findViewById(R.id.btn_info);
			btnEdit.setOnClickListener(this);
			btnControl.setOnClickListener(this);
			btnInfo.setOnClickListener(this);
			btnEdit.setTag(position);
			btnControl.setTag(position);
			btnInfo.setTag(position);

			CarData it = mItems.get(position);
			ImageView iv = (ImageView) convertView.findViewById(R.id.img_car);
			iv.setImageResource(Ui.getDrawableIdentifier(parent.getContext(),
					it.sel_vehicle_image));
			((TextView) convertView.findViewById(R.id.txt_title))
					.setText(it.sel_vehicle_label);

			if (mListView == null && parent instanceof ListView) {
				mListView = (ListView) parent;
			}
			if (mListView == null)
				return convertView;

			iv = (ImageView) convertView.findViewById(R.id.img_signal_rssi);
			if (mListView.isItemChecked(position)) {
				convertView.setBackgroundColor(0x8033B5E5);
				btnInfo.setVisibility(View.VISIBLE);
				btnControl.setVisibility(View.VISIBLE);
				iv.setVisibility(View.VISIBLE);
				iv.setImageResource(Ui.getDrawableIdentifier(
						parent.getContext(), "signal_strength_"
								+ it.car_gsm_bars));
			} else {
				convertView.setBackgroundColor(0);
				iv.setVisibility(View.INVISIBLE);
				btnInfo.setVisibility(View.INVISIBLE);
				btnControl.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			if (mListView == null || mListView.getOnItemClickListener() == null)
				return;
			mListView.getOnItemClickListener().onItemClick(mListView, v,
					(Integer) v.getTag(), v.getId());
		}
	}
}
