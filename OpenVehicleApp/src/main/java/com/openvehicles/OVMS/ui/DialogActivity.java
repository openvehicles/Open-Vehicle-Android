package com.openvehicles.OVMS.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * DialogActivity: custom Toast replacement triggered by an Intent, key advantages:
 * 	- not limited to 2 lines on API >= 31
 * 	- working from background (service) on Huawei devices (only on API < ?)
 * 	- custom title
 * 	- scrollable text content
 * 	- user can keep message on screen by interacting with it (holding/scrolling)
 *
 * Use the show() utility method to invoke.
 *
 * TODO: this may need a queue if new messages can come in while one
 *       is still displayed -- not sure if that's actually needed
 */
public class DialogActivity extends AppCompatActivity {
	private static final String TAG = "DialogActivity";

	private final int DISPLAY_TIMEOUT = 5000;		// Milliseconds

	private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());
	private final Runnable onTimeout = this::finish;

	public static void show(Context context, CharSequence title, CharSequence text) {
		Intent intent = new Intent(context.getApplicationContext(), DialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}

		CharSequence title = intent.getCharSequenceExtra("title");
		CharSequence text = intent.getCharSequenceExtra("text");
		Log.d(TAG, "onCreate: title=" + title);

		TextView textView = new TextView(this);
		textView.setText(text);
		textView.setPadding(20,10,20,20);
		textView.setOnClickListener(v -> finish());

		ScrollView scrollView = new ScrollView(this);
		scrollView.addView(textView);

		setTitle(title);
		setContentView(scrollView);

		mTimeoutHandler.postDelayed(onTimeout, DISPLAY_TIMEOUT);
	}

	@Override
	public void onUserInteraction() {
		mTimeoutHandler.removeCallbacks(onTimeout);
	}

	@Override
	protected void onUserLeaveHint() {
		finish();
	}
}
