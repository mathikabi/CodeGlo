package com.mvvm.app.base

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.gatebot.app.R
import com.mvvm.app.utilities.Alert
import com.mvvm.app.utilities.NetworkUtils

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {

    var mViewDataBinding: T? = null
        private set
    var mViewModel: V? = null
        private set
    var networkChangeReceiver: NetworkChangeReceiver? = null
    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    private var progressBar: ProgressBar? = null

    private var fragment: Fragment? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private val handler = Handler()
    private val interval = 500
    private var runnable: Runnable? = null
    private var isNoNetAlertVisible = false

    companion object {
        const val INTERNET_RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        mViewModel?.alertMsg?.observe(this, Observer {
            if (!it.isNullOrEmpty()){
                showQuickAlert(it)
            }
        })
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }
    /**
     * common toast show for all screens
     *
     */
    fun putToast(message: String?) {
        Toast.makeText(applicationContext, "" + message, Toast.LENGTH_SHORT).show()
    }

    /**
     * common show progress bar for all screens
     *
     * @param parent layout of screen
     */
    fun showProgressBar(parent: ViewGroup) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        progressBar?.indeterminateDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                android.graphics.PorterDuff.Mode.SRC_ATOP)
        when (parent) {
            is RelativeLayout -> {
                val params = RelativeLayout.LayoutParams(130, 130)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                parent.addView(progressBar, params)
            }
            is FrameLayout -> {
                val params = FrameLayout.LayoutParams(130, 130)
                params.gravity = Gravity.CENTER
                parent.addView(progressBar, params)
            }
            is CoordinatorLayout -> {
                val params = CoordinatorLayout.LayoutParams(130, 130)
                params.gravity = Gravity.CENTER
                parent.addView(progressBar, params)
            }
            is ConstraintLayout -> {
                val params = ConstraintLayout.LayoutParams(130, 130)
                params.topToTop = ConstraintSet.PARENT_ID
                params.startToStart = ConstraintSet.PARENT_ID
                params.endToEnd = ConstraintSet.PARENT_ID
                params.bottomToBottom = ConstraintSet.PARENT_ID
                parent.elevation = 10f
                parent.addView(progressBar, params)
            }
        }
        progressBar?.visibility = View.VISIBLE  //To show ProgressBar
    }

    /**
     * common dismiss progress bar for all screens
     *
     */
    fun dismissProgressBar() {
        if (progressBar?.visibility == View.VISIBLE) {
            progressBar?.visibility = View.GONE     // To Hide ProgressBar
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_pop_enter, R.anim.anim_pop_exit)
    }

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //showNetworkError()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    override fun onResume() {
        super.onResume()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    fun okAlert(msg: String) {
        Alert.createOkAlert(this, "Alert", msg,
                object : Alert.OnAlertClickListener {
                    override fun onPositive(dialog: DialogInterface) {
                        dialog.dismiss()
                    }

                    override fun onNegative(dialog: DialogInterface) {
                        dialog.dismiss()
                    }

                }).show()
    }

    fun showNetworkError(): Boolean {
        // Show popup when network is no available
        val isNetwork = NetworkUtils.isNetworkConnected(this)
        if (!isNoNetAlertVisible && !NetworkUtils.isNetworkConnected(this)) {
            isNoNetAlertVisible = true
            Alert.createOkAlert(this@BaseActivity, "Alert", "No internet connection or network failure",
                    object : Alert.OnAlertClickListener {
                        override fun onPositive(dialog: DialogInterface) {
                            dialog.dismiss()
                            isNoNetAlertVisible = false
                        }

                        override fun onNegative(dialog: DialogInterface) {
                            dialog.dismiss()
                        }

                    }).show()
        }
        return isNetwork
    }

    fun showQuickAlert(msg: String){

    }

    fun showToast(msg: String) {
       // toast(msg)
    }

    fun isSameFragmentAttached(tag: String): Boolean {
        val fragmentList = supportFragmentManager.fragments
        if (fragmentList.size > 0) {
            return fragmentList[fragmentList.size - 1].tag != tag
        }
        return true
    }

}