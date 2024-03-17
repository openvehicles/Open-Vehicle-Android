package com.openvehicles.OVMS.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.CommandActivity;
import com.openvehicles.OVMS.utils.AppPrefes;
import com.openvehicles.OVMS.ui.settings.StoredCommandFragment;

/**
 * This Activity receives the CREATE_SHORTCUT intent, it allows the user to create
 * a shortcut from a StoredCommand for external Apps like Automagic & KWGT.
 *
 * Note: the shortcut isn't added to the launcher by this Activity, see
 * StoredCommandFragment.pinCommand() for this.
 */
public class CreateShortcutActivity extends Activity {
	private static final String TAG = "CreateShortcutActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate: intent " + getIntent());

		BaseFragmentActivity.showForResult(this, StoredCommandFragment.class,
				StoredCommandFragment.REQUEST_SELECT_SHORTCUT,null,
				Configuration.ORIENTATION_UNDEFINED);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d(TAG, "onActivityResult: reqCode=" + requestCode + ", resCode=" + resultCode);

		if (resultCode != RESULT_OK || data == null) {
			setResult(Activity.RESULT_CANCELED);
			finish();
			return;
		}

		AppPrefes appPrefes = new AppPrefes(this, "ovms");

		long key = data.getLongExtra("key", 0);
		String title = data.getStringExtra("title");
		String command = data.getStringExtra("command");

		Log.d(TAG, "onActivityResult: command: " + command);

		if (key == 0 || title == null || command == null) {
			setResult(Activity.RESULT_CANCELED);
			finish();
			return;
		}

		Intent intent;
		ShortcutInfoCompat shortcut;
		Context appContext = getApplicationContext();

		intent = new Intent(appContext, CommandActivity.class);
		intent.setAction("com.openvehicles.OVMS.action.COMMAND");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		intent.putExtra("apikey", appPrefes.getData("APIKey"));
		intent.putExtras(data);

		shortcut = new ShortcutInfoCompat.Builder(appContext, "StoredCommand_" + key)
				.setShortLabel(title)
				.setIcon(IconCompat.createWithResource(appContext, R.drawable.ic_remote_control))
				.setIntent(intent)
				.build();

		setResult(Activity.RESULT_OK, ShortcutManagerCompat.createShortcutResultIntent(this, shortcut));

		finish();
	}
}
