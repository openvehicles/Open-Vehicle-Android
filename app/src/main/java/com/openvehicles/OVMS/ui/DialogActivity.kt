package com.openvehicles.OVMS.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * DialogActivity: custom Toast replacement triggered by an Intent, key advantages:
 * - not limited to 2 lines on API >= 31
 * - working from background (service) on Huawei devices (only on API < ?)
 * - custom title
 * - scrollable text content
 * - user can keep message on screen by interacting with it (holding/scrolling)
 *
 * Use the show() utility method to invoke.
 *
 * TODO: this may need a queue if new messages can come in while one
 * is still displayed -- not sure if that's actually needed
 */
class DialogActivity : AppCompatActivity() {

    private val timeoutHandler = Handler(Looper.getMainLooper())
    private val onTimeout = Runnable { finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent == null) {
            finish()
            return
        }
        val title = intent.getCharSequenceExtra("title")
        val text = intent.getCharSequenceExtra("text")
        Log.d(TAG, "onCreate: title=$title")
        val textView = TextView(this).apply {
            this.text = text
            setPadding(20, 10, 20, 20)
            setOnClickListener { _ -> finish() }
        }

        val scrollView = ScrollView(this)
        scrollView.addView(textView)
        setTitle(title)
        setContentView(scrollView)
        timeoutHandler.postDelayed(onTimeout, DISPLAY_TIMEOUT.toLong())
    }

    override fun onUserInteraction() {
        timeoutHandler.removeCallbacks(onTimeout)
    }

    override fun onUserLeaveHint() {
        finish()
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "DialogActivity"
        private const val DISPLAY_TIMEOUT = 5000 // Milliseconds

        fun show(context: Context, title: CharSequence?, text: CharSequence?) {
            val intent = Intent(context.applicationContext, DialogActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("title", title)
                putExtra("text", text)
            }

            context.startActivity(intent)
        }
    }
}
