package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData


open class QuickAction {
    private var commandInProgress = false
    private var actionOn: Boolean = false
    val id: String
    var label: String? = null
    private val icon: Int
    private var actionOnTint: Int? = null
    private var actionOffTint: Int? = null
    private var actionOnIconTint: Int? = null
    private var actionOffIconTint: Int? = null
    private var carData: CarData? = null
    private var actionIsRunningCommand = false
    lateinit var progressBar: CircularProgressIndicator
    lateinit var button: MaterialButton
    var context: Context? = null
    private var getApiService: () -> ApiService?

    constructor(
        id: String,
        icon: Int,
        getApiService: () -> ApiService?,
        actionOnTint: Int? = null,
        actionOffTint: Int? = null,
        label: String? = null,
        actionOnIconTint: Int? = null,
        actionOffIconTint: Int? = null
    ) {
        this.id = id
        this.icon = icon
        this.actionOnTint = actionOnTint
        this.actionOffTint = actionOffTint
        this.actionOnIconTint = actionOnIconTint
        this.actionOffIconTint = actionOffIconTint
        this.getApiService = getApiService
        this.label = label
    }


    fun initAction(view: View, clickCallback: () -> Boolean, editMode: Boolean) {
        // Init elements and let quick action do handling
        context = view.context
        button = view.findViewById(R.id.action_button) as MaterialButton
        progressBar = view.findViewById(R.id.action_progress) as CircularProgressIndicator
        button.setOnClickListener {
            if (clickCallback()) {
                onAction()
            }
        }
        actionOn = getStateFromCarData()
        renderAction(editMode)
        if (editMode) {
            return
        }
        setCommandInProgress(actionIsRunningCommand)
    }

    fun getCarData(): CarData? {
        return carData
    }

    fun setCommandInProgress(value: Boolean) {
        this.commandInProgress = value
        this.progressBar.visibility = if (value) View.VISIBLE else View.GONE
        this.button.isEnabled = commandsAvailable() && !value
    }

    private fun commandCallback(command: String, result: Int, details: String?) {
        actionIsRunningCommand = false
        setCommandInProgress(actionIsRunningCommand)
        renderAction(false)
        if (context != null) {
            when (result) {
                0 -> onCommandSuccess(command, details)
                1 -> onCommandFailed(command, details)
                2 -> onCommandUnsupported(command)
                3 -> onCommandUnimplemented(command)
            }
        }
    }

    open fun getLiveCarIcon(state: Boolean, context: Context): Drawable? {
        return AppCompatResources.getDrawable(context, getLiveCarIconId(state))
    }

    open fun getLiveCarIconId(state: Boolean): Int {
        return icon
    }

    open fun onCommandSuccess(command: String, details: String?) {
        actionIsRunningCommand = false
        if (context != null)
            Toast.makeText(context, details ?: context!!.getString(R.string.msg_ok), Toast.LENGTH_SHORT).show()
    }

    open fun getStateFromCarData(): Boolean {
        return false
    }

    open fun onCommandFailed(command: String, details: String?) {
        actionIsRunningCommand = false
        setCommandInProgress(actionIsRunningCommand)
        if (context != null)
            Toast.makeText(context, details ?: context!!.getString(R.string.err_command_failed), Toast.LENGTH_LONG).show()
    }

    open fun onCommandUnsupported(command: String) {
        actionIsRunningCommand = false
        setCommandInProgress(actionIsRunningCommand)
        if (context != null)
            Toast.makeText(context, R.string.err_unsupported_operation, Toast.LENGTH_LONG).show()
    }

    open fun onCommandUnimplemented(command: String) {
        actionIsRunningCommand = false
        setCommandInProgress(actionIsRunningCommand)
        if (context != null)
            Toast.makeText(context, R.string.err_unimplemented_operation, Toast.LENGTH_LONG).show()
    }

    open fun onAction() {
        throw Exception("OVERRIDE!")
    }

    fun updateCarData(carData: CarData) {
        this.carData = carData
        actionOn = getStateFromCarData()
        renderAction(false)
    }

    fun setActionState(value: Boolean) {
        this.actionOn = value
        renderAction(false)
    }

    fun setCarData(carData: CarData?): QuickAction {
        this.carData = carData
        return this
    }

    open fun sendCommand(command: String) {
        val service = getApiService()
            ?: return
        actionIsRunningCommand = true
        setCommandInProgress(true)
        service.sendCommand(command, object : OnResultCommandListener {
            override fun onResultCommand(result: Array<String>) {
                if (result.isEmpty()) return
                val resCode = result[1].toInt()
                val resText = if (result.size > 2 && result[2] != "") result[2] else null
                commandCallback(command, resCode, resText)
            }
        })
    }

    open fun commandsAvailable(): Boolean {
        return false
    }

    open fun renderAction(editMode: Boolean) {
        val commandsAvailable = commandsAvailable()
        button.isEnabled = commandsAvailable || editMode

        val iconDrawable = getLiveCarIcon(actionOn, context!!)

        if (actionOnTint != null && actionOffTint != null && !editMode) {
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

            // Icon Tint Handling for custom states
            if (actionOnIconTint != null && actionOffIconTint != null) {
                try {
                    button.iconTint =
                        context?.resources?.getColor(if (actionOn) actionOnIconTint!! else actionOffIconTint!!)
                            ?.let { ColorStateList.valueOf(it) }
                } catch (ignored: java.lang.Exception) {
                    val typedValue = TypedValue()
                    val theme = context!!.theme
                    theme.resolveAttribute(if (actionOn) actionOnIconTint!! else actionOffIconTint!!, typedValue, true)
                    @ColorInt val color = typedValue.data
                    button.iconTint = ColorStateList.valueOf(color)
                }
            } else if (iconDrawable !is LayerDrawable) {
                val typedValue = TypedValue()
                context!!.theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
                button.iconTint = ColorStateList.valueOf(typedValue.data)
            } else {
                button.iconTint = null
            }
        } else {
            // Use default Material3 Secondary Container colors for edit mode or standard state
            val backgroundColor = MaterialColors.getColor(button, R.attr.colorSecondaryContainer)
            button.backgroundTintList = ColorStateList.valueOf(backgroundColor)

            if (iconDrawable !is LayerDrawable) {
                val iconColor = MaterialColors.getColor(button, R.attr.colorOnSecondaryContainer)
                button.iconTint = ColorStateList.valueOf(iconColor)
            } else {
                button.iconTint = null
            }
        }

        button.icon = iconDrawable
    }
}