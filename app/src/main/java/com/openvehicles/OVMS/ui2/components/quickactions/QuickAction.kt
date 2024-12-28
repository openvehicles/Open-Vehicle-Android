package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData


open class QuickAction {
    private var commandInProgress = false
    private var actionOn: Boolean = false
    private val name: String
    private val icon: Int
    private var actionOnTint: Int? = null
    private var actionOffTint: Int? = null
    private var carData: CarData? = null
    lateinit var progressBar: CircularProgressIndicator
    lateinit var button: FloatingActionButton
    var context: Context? = null
    private var getApiService: () -> ApiService?

    constructor(
        name: String,
        icon: Int,
        getApiService: () -> ApiService?,
        actionOnTint: Int? = null,
        actionOffTint: Int? = null,
    ) {
        this.name = name
        this.icon = icon
        this.actionOnTint = actionOnTint
        this.actionOffTint = actionOffTint
        this.getApiService = getApiService
    }


    fun initAction(view: View) {
        // Init elements and let quick action do handling
        context = view.context
        button = view.findViewById(R.id.action_button) as FloatingActionButton
        progressBar = view.findViewById(R.id.action_progress) as CircularProgressIndicator
        button.setOnClickListener {
            onAction()
        }
        actionOn = getStateFromCarData()
        renderAction()
    }

    fun getCarData(): CarData? {
        return carData
    }

    fun setCommandInProgress(value: Boolean) {
        this.commandInProgress = value
        this.progressBar.visibility = if (value) View.VISIBLE else View.INVISIBLE
        this.button.isEnabled = commandsAvailable() && !value
    }

    private fun commandCallback(command: String, result: Int, details: String?) {
        setCommandInProgress(false)
        renderAction()
        if (context != null) {
            when (result) {
                0 -> onCommandFinish(command)
                1 -> onCommandFailed(command, details)
                2 -> onCommandUnsupported(command)
                3 -> onCommandUnimplemented(command)
            }
        }
        onCommandFinish(command)
    }

    open fun getLiveCarIcon(state: Boolean): Int {
        return icon
    }

    open fun onCommandFinish(command: String) {

    }

    open fun getStateFromCarData(): Boolean {
        return false
    }

    open fun onCommandFailed(command: String, details: String?) {
        if (context != null)
            Toast.makeText(context, details ?: context!!.getString(R.string.err_command_failed), Toast.LENGTH_LONG).show()
    }

    open fun onCommandUnsupported(command: String) {
        if (context != null)
            Toast.makeText(context, R.string.err_unsupported_operation, Toast.LENGTH_LONG).show()
    }

    open fun onCommandUnimplemented(command: String) {
        if (context != null)
            Toast.makeText(context, R.string.err_unimplemented_operation, Toast.LENGTH_LONG).show()
    }

    open fun onAction() {
        throw Exception("OVERRIDE!")
    }

    fun updateCarData(carData: CarData) {
        this.carData = carData
        actionOn = getStateFromCarData()
        renderAction()
    }

    fun setActionState(value: Boolean) {
        this.actionOn = value
        renderAction()
    }

    fun setCarData(carData: CarData?) {
        this.carData = carData
    }

    open fun sendCommand(command: String) {
        val service = getApiService()
            ?: return
        setCommandInProgress(true)
        service.sendCommand(command, object : OnResultCommandListener {
            override fun onResultCommand(result: Array<String>) {
                if (result.isEmpty()) return
                val resCode = result[1].toInt()
                val resText = if (result.size > 2) result[2] else null
                commandCallback(command, resCode, resText)
            }
        })
    }

    open fun commandsAvailable(): Boolean {
        return false
    }

    open fun renderAction() {
        val commandsAvailable = commandsAvailable()
        button.isEnabled = commandsAvailable
        Log.e("NEWUI", "TABCONFIG: "+name+actionOnTint+actionOffTint)
        if (actionOnTint != null && actionOffTint != null) {
            try {
                button.backgroundTintList =
                    context?.resources?.getColor(if (actionOn) actionOnTint!! else actionOffTint!!)
                        ?.let { ColorStateList.valueOf(it) }
            } catch (ignored: java.lang.Exception) {
                val typedValue = TypedValue()
                val theme = context!!.theme
                theme.resolveAttribute(if (actionOn) actionOnTint!! else actionOffTint!!, typedValue, true)
                @ColorInt val color = typedValue.data
                button.backgroundTintList =
                    ColorStateList.valueOf(color)
            }
        } else {
            val typedValue = TypedValue()
            val theme = context!!.theme
            theme.resolveAttribute(R.attr.colorTertiaryContainer, typedValue, true)
            @ColorInt val color = typedValue.data
            button.backgroundTintList =
                ColorStateList.valueOf(color)
        }
        button.setImageResource(getLiveCarIcon(actionOn))
    }
}