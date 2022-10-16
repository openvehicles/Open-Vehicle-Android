package com.openvehicles.OVMS.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.CommandActivity;
import com.openvehicles.OVMS.entities.StoredCommand;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.BaseFragmentActivity;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.fragment.app.FragmentActivity;


/**
 * StoredCommand editor & selector
 *
 * This fragment is called from the NotificationsFragment and from the CreateShortcutActivity
 * for management & selection of stored commands for execution or bookmark creation.
 */
public class StoredCommandFragment extends BaseFragment
		implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	private static final String TAG = "StoredCommandFragment";

	public static final int REQUEST_SELECT_EXECUTE = 1;		// select a command for immediate execution
	public static final int REQUEST_SELECT_SHORTCUT = 2;	// select a command for building a shortcut

	private AppPrefes appPrefes;
	private Database mDatabase;
	private ArrayList<StoredCommand> mStoredCommands;
	private ListView mListView;
	private StoredCommandAdapter mAdapter;
	private int mRequestCode = 0;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) return null;
		Context context = getContext();
		if (context == null) return null;

		// Get our operation mode:

		Bundle args = getArguments();
		if (args != null) {
			mRequestCode = args.getInt(BaseFragmentActivity.EXT_REQUEST_CODE, 0);
		}

		// Load stored commands from database:

		appPrefes = new AppPrefes(context, "ovms");
		mDatabase = new Database(context);
		mStoredCommands = mDatabase.getStoredCommands();

		// Create user interface:

		FragmentActivity activity = getActivity();
		if (activity != null)
			activity.setTitle(R.string.stored_commands_title);

		setHasOptionsMenu(true);

		mListView = new ListView(container.getContext());
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mAdapter = new StoredCommandAdapter(context, R.layout.item_stored_command, mStoredCommands, inflater);
		mListView.setAdapter(mAdapter);

		return mListView;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.stored_command_list_options, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int menuId = item.getItemId();
		if (menuId == R.id.mi_add) {
			showItemEditor(null);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(@NonNull AdapterView<?> parent, View view, int position, long id) {
		if (id == R.id.btn_select) {
			selectItem(position);
		} else {
			showItemEditor(mAdapter.getItem(position));
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = getContext();
		if (context == null) return false;
		AlertDialog dialog = new AlertDialog.Builder(context)
				.setMessage(R.string.stored_commands_pin)
				.setNegativeButton(R.string.Cancel, null)
				.setPositiveButton(R.string.PinShortcut, (dialog1, which) -> {
					StoredCommand cmd = mAdapter.getItem(position);
					if (cmd != null) pinCommand(cmd);
				})
				.create();
		dialog.show();
		return true;
	}

	private void showItemEditor(@Nullable StoredCommand cmd) {
		Context context = getContext();
		if (context == null) return;
		View view = LayoutInflater.from(context).inflate(R.layout.dlg_stored_command, null);

		if (cmd == null) {
			// Create new command:
			AlertDialog dialog = new AlertDialog.Builder(context)
					.setMessage(R.string.stored_commands_add)
					.setView(view)
					.setNegativeButton(R.string.Cancel, null)
					.setPositiveButton(R.string.Save, (dialog1, which) -> {
						StoredCommand cmd1 = new StoredCommand(
								Ui.getValue(view, R.id.etxt_input_title),
								Ui.getValue(view, R.id.etxt_input_command));
						saveCommand(cmd1);
					})
					.create();
			dialog.show();
		} else {
			// Edit existing command:
			view.setTag(cmd);
			Ui.setValue(view, R.id.etxt_input_title, cmd.mTitle);
			Ui.setValue(view, R.id.etxt_input_command, cmd.mCommand);
			AlertDialog dialog = new AlertDialog.Builder(context)
					.setMessage(R.string.stored_commands_edit)
					.setView(view)
					.setNegativeButton(R.string.Delete, (dialog12, which) -> {
						StoredCommand cmd12 = (StoredCommand) view.getTag();
						deleteCommand(cmd12);
					})
					.setPositiveButton(R.string.Save, (dialog13, which) -> {
						StoredCommand cmd13 = (StoredCommand) view.getTag();
						cmd13.mTitle = Ui.getValue(view, R.id.etxt_input_title);
						cmd13.mCommand = Ui.getValue(view, R.id.etxt_input_command);
						saveCommand(cmd13);
					})
					.setNeutralButton(R.string.PinShortcut, (dialog14, which) -> {
						StoredCommand cmd14 = (StoredCommand) view.getTag();
						pinCommand(cmd14);
					})
					.create();
			dialog.show();
		}
	}


	public void saveCommand(@NonNull StoredCommand cmd) {
		if (mDatabase.saveStoredCommand(cmd)) {
			int index = mStoredCommands.indexOf(cmd);
			if (index < 0)
				mStoredCommands.add(cmd);
			else
				mStoredCommands.set(index, cmd);
			Collections.sort(mStoredCommands);
			mAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(getContext(), R.string.DatabaseError, Toast.LENGTH_LONG).show();
		}
	}

	public void deleteCommand(@NonNull StoredCommand cmd) {
		if (mDatabase.deleteStoredCommand(cmd)) {
			mStoredCommands.remove(cmd);
			mAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(getContext(), R.string.DatabaseError, Toast.LENGTH_LONG).show();
		}
	}

	public void pinCommand(@NonNull StoredCommand cmd) {
		Intent intent;
		ShortcutInfoCompat shortcut;
		Context context = getContext();
		if (context == null) return;

		intent = new Intent(context, CommandActivity.class);
		intent.setAction("com.openvehicles.OVMS.action.COMMAND");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		intent.putExtra("apikey", appPrefes.getData("APIKey"));
		intent.putExtra("key", cmd.mKey);
		intent.putExtra("title", cmd.mTitle);
		intent.putExtra("command", cmd.mCommand);

		shortcut = new ShortcutInfoCompat.Builder(context, "StoredCommand_" + cmd.mKey)
				.setShortLabel(cmd.mTitle)
				.setIcon(IconCompat.createWithResource(context, R.drawable.ic_remote_control))
				.setIntent(intent)
				.build();

		boolean ok = ShortcutManagerCompat.requestPinShortcut(context, shortcut, null);
		if (ok) {
			// Beginning with API 26 (Android 8), the system will ask the user for permission
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
				Toast.makeText(context, R.string.PinRequestSuccess, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, R.string.PinRequestFailure, Toast.LENGTH_LONG).show();
		}
	}

	private void selectItem(int position) {
		Activity activity = getActivity();
		if (activity == null) return;

		// Check if we're expected to return a result:
		if (mRequestCode == 0) return;

		// OK, return the command selected:
		StoredCommand cmd = mAdapter.getItem(position);
		if (cmd == null) return;

		Log.d(TAG, "selectItem: reqCode=" + mRequestCode + " returning command: " + cmd.mCommand);

		Intent result = new Intent();
		result.putExtra("key", cmd.mKey);
		result.putExtra("title", cmd.mTitle);
		result.putExtra("command", cmd.mCommand);

		if (activity.getParent() != null)
			activity.getParent().setResult(Activity.RESULT_OK, result);
		else
			activity.setResult(Activity.RESULT_OK, result);

		activity.finish();
	}


	private class StoredCommandAdapter extends ArrayAdapter<StoredCommand>
			implements View.OnClickListener, View.OnLongClickListener {

		private final LayoutInflater mInflater;

		public StoredCommandAdapter(@NonNull Context context, int resource, @NonNull List<StoredCommand> objects, LayoutInflater mInflater) {
			super(context, resource, objects);
			this.mInflater = mInflater;
		}

		@NonNull
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_stored_command, null);
				Ui.setOnClick(convertView, R.id.linearLayout1, position, this, this);
				Ui.setOnClick(convertView, R.id.btn_select, position, this, this);
				if (mRequestCode == REQUEST_SELECT_SHORTCUT) {
					Drawable icon = ResourcesCompat.getDrawable(getResources(),
							R.drawable.ic_arrow_forward, null);
					Ui.setButtonImage(convertView, R.id.btn_select, icon);
				}
			}
			StoredCommand cmd = getItem(position);
			if (cmd != null) {
				Ui.setValue(convertView, R.id.title, cmd.mTitle);
				Ui.setValue(convertView, R.id.text, cmd.mCommand);
			}
			return convertView;
		}

		@Override
		public void onClick(View view) {
			onItemClick(mListView, view, (Integer) view.getTag(), view.getId());
		}

		@Override
		public boolean onLongClick(View view) {
			return onItemLongClick(mListView, view, (Integer) view.getTag(), view.getId());
		}
	}

}
