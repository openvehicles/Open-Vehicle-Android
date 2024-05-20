package com.openvehicles.OVMS.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_UNDEFINED
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.openvehicles.OVMS.R

class BaseFragmentActivity : ApiActivity() {

    private val contentId: Int
        get() = R.id.content

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(contentId)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent == null) {
            finish()
            return
        }
        val fragmentClassName = intent.getStringExtra(EXT_FRAGMENT_CLASS_NAME)
        if (fragmentClassName.isNullOrEmpty()) {
            finish()
            return
        }
        val orientation = intent.getIntExtra(EXT_ONLY_ORIENTATION, ORIENTATION_UNDEFINED)
        if (orientation != ORIENTATION_UNDEFINED
            && orientation != getResources().configuration.orientation) {
            finish()
            return
        }
        setDefaultContentView()
        if (currentFragment == null) {
            setStartFragment(fragmentClassName)
        }
        current = this
    }

    override fun onDestroy() {
        current = null
        super.onDestroy()
    }

    private fun setDefaultContentView() {
        val fl = FrameLayout(this)
        fl.setId(R.id.content)
        setContentView(fl)
    }

    fun setStartFragment(clazz: Class<out Fragment?>): Fragment {
        return setStartFragment(clazz, intent.extras)
    }

    private fun setStartFragment(className: String?): Fragment {
        return setStartFragment(className, contentId, intent.extras)
    }

    fun setStartFragment(clazz: Class<out Fragment?>, id: Int): Fragment {
        return setStartFragment(clazz, id, intent.extras)
    }

    private fun setStartFragment(clazz: Class<out Fragment?>, args: Bundle?): Fragment {
        return setStartFragment(clazz, contentId, args)
    }

    private fun setStartFragment(clazz: Class<out Fragment?>, id: Int, args: Bundle?): Fragment {
        return setStartFragment(clazz.getName(), id, args)
    }

    private fun setStartFragment(className: String?, id: Int, args: Bundle?): Fragment {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var fragment = fm.findFragmentById(id)
        if (fragment == null) {
            fragment = Fragment.instantiate(this, className!!, args)
            ft.add(id, fragment)
        } else if (fragment.javaClass.getName() != className) {
            fragment = Fragment.instantiate(this, className!!, args)
            ft.replace(id, fragment)
        }
        ft.commit()
        return fragment
    }

    fun replaceFragment(clazz: Class<out Fragment?>, id: Int, args: Bundle?): Fragment {
        return replaceFragment(clazz.getName(), id, args)
    }

    private fun replaceFragment(className: String?, id: Int, args: Bundle?): Fragment {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val fragment = Fragment.instantiate(this, className!!, args)
        ft.replace(id, fragment)
        ft.commit()
        return fragment
    }

    fun setNextFragment(clazz: Class<out Fragment?>): Fragment {
        return setNextFragment(clazz, intent.extras)
    }

    fun setNextFragment(clazz: Class<out Fragment?>, args: Bundle?): Fragment {
        val fragment = Fragment.instantiate(this, clazz.getName(), args)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(contentId, fragment)
        ft.addToBackStack(null)
        ft.setTransition(TRANSIT_FRAGMENT_FADE)
        ft.commit()
        return fragment
    }

    /*
     * Inner types
     */

    companion object {

        private const val EXT_FRAGMENT_CLASS_NAME = "ext_fragmentclassname"
        private const val EXT_ONLY_ORIENTATION = "ext_only_orientation"
        private const val EXT_FOR_RESULT = "ext_for_result"
        const val EXT_REQUEST_CODE = "ext_request_code"

        private var current: BaseFragmentActivity? = null

        @JvmStatic
        fun show(
            context: Context,
            clazz: Class<out Fragment?>,
            args: Bundle?,
            onlyOrientation: Int
        ) {
            val intent = Intent(context, BaseFragmentActivity::class.java)
            intent.putExtra(EXT_FRAGMENT_CLASS_NAME, clazz.getName())
            intent.putExtra(EXT_ONLY_ORIENTATION, onlyOrientation)
            if (args != null) {
                intent.putExtras(args)
            }
            context.startActivity(intent)
        }

        @JvmStatic
        fun showForResult(
            fragment: Fragment,
            clazz: Class<out Fragment?>,
            requestCode: Int,
            args: Bundle?,
            onlyOrientation: Int
        ) {
            val intent = Intent(fragment.activity, BaseFragmentActivity::class.java)
            intent.putExtra(EXT_FRAGMENT_CLASS_NAME, clazz.getName())
            intent.putExtra(EXT_ONLY_ORIENTATION, onlyOrientation)
            intent.putExtra(EXT_FOR_RESULT, true)
            intent.putExtra(EXT_REQUEST_CODE, requestCode)
            if (args != null) {
                intent.putExtras(args)
            }
            fragment.startActivityForResult(intent, requestCode)
        }

        @JvmStatic
        fun showForResult(
            activity: Activity,
            clazz: Class<out Fragment?>,
            requestCode: Int,
            args: Bundle?,
            onlyOrientation: Int
        ) {
            val intent = Intent(activity, BaseFragmentActivity::class.java)
            intent.putExtra(EXT_FRAGMENT_CLASS_NAME, clazz.getName())
            intent.putExtra(EXT_ONLY_ORIENTATION, onlyOrientation)
            intent.putExtra(EXT_FOR_RESULT, true)
            intent.putExtra(EXT_REQUEST_CODE, requestCode)
            if (args != null) {
                intent.putExtras(args)
            }
            activity.startActivityForResult(intent, requestCode)
        }

        @JvmStatic
        fun finishCurrent() {
            if (current != null) {
                current!!.finish()
            }
        }
    }
}
