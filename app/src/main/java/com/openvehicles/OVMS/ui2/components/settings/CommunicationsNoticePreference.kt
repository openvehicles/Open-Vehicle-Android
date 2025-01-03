package com.openvehicles.OVMS.ui2.components.settings

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.openvehicles.OVMS.R

class CommunicationsNoticePreference : Preference {
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        val textView = holder.itemView.findViewById<TextView>(android.R.id.summary)
        holder.itemView.findViewById<View>(android.R.id.title).visibility = View.GONE
        textView.maxLines = Int.MAX_VALUE
        textView.ellipsize = null

        textView.text = Html.fromHtml(holder.itemView.context.getString(R.string.lb_options_broadcast_info))
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

}