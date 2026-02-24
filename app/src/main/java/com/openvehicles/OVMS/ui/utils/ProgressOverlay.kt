package com.openvehicles.OVMS.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.openvehicles.OVMS.R

/**
 * ProgressOverlay: create and manage a progress_layer view
 */
class ProgressOverlay(
    rootView: View
) : View.OnClickListener {

    private val progressLayer: LinearLayout
    private val progressLabel: TextView
    private val progressBarStep: ProgressBar
    private val progressBarSubStep: ProgressBar
    private val progressBarIndeterminate: ProgressBar
    private val progressCancel: Button

    private var listener: OnCancelListener? = null

    val isVisible: Boolean
        get() = progressLayer.visibility == VISIBLE

    init {
        progressLayer = rootView.findViewById(R.id.progress_layer) as LinearLayout
        progressLabel = progressLayer.findViewById<View>(R.id.progress_label) as TextView
        progressBarStep =
            progressLayer.findViewById<View>(R.id.progress_bar_determinate) as ProgressBar
        progressBarSubStep =
            progressLayer.findViewById<View>(R.id.progress_bar_substep) as ProgressBar
        progressBarIndeterminate =
            progressLayer.findViewById<View>(R.id.progress_bar_indeterminate) as ProgressBar
        progressCancel = progressLayer.findViewById<View>(R.id.progress_cancel) as Button
        progressCancel.setOnClickListener(this)
        hide()
    }

    // set label from resource:
    fun setLabel(resId: Int) {
        progressLabel.setText(resId)
    }

    // set label from string:
    fun setLabel(text: String?) {
        progressLabel.text = text
    }

    // show indeterminate progress spinner icon:
    fun show() {
        progressBarStep.visibility = GONE
        progressBarSubStep.visibility = GONE
        progressBarIndeterminate.visibility = VISIBLE
        progressCancel.visibility = if (listener != null) VISIBLE else GONE
        progressLayer.bringToFront()
        progressLayer.visibility = VISIBLE
    }

    // show determinate progress bar:
    //   closes if pos == maxPos
    //   shows sub step bar if step < stepCnt
    fun step(pos: Int, maxPos: Int, step: Int, stepCnt: Int) {
        if (maxPos > 0 && pos == maxPos) {
            hide()
        } else {
            progressBarStep.setMax(maxPos)
            progressBarStep.progress = pos
            progressBarStep.visibility = VISIBLE
            if (step < stepCnt) {
                progressBarSubStep.setMax(stepCnt)
                progressBarSubStep.progress = step
                progressBarSubStep.visibility = VISIBLE
            } else {
                progressBarSubStep.visibility = GONE
            }
            progressBarIndeterminate.visibility = GONE
            progressCancel.visibility = if (listener != null) VISIBLE else GONE
            progressLayer.bringToFront()
            progressLayer.visibility = VISIBLE
        }
    }

    // hide:
    fun hide() {
        progressBarStep.visibility = GONE
        progressBarSubStep.visibility = GONE
        progressBarIndeterminate.visibility = GONE
        progressCancel.visibility = GONE
        progressLayer.visibility = GONE
    }

    fun setOnCancelListener(listener: OnCancelListener?) {
        this.listener = listener
    }

    override fun onClick(view: View) {
        if (view.id == R.id.progress_cancel && listener != null) {
            listener!!.onProgressCancel()
        }
    }

    /*
     * Inner types
     */

    interface OnCancelListener {
        fun onProgressCancel()
    }
}
