package com.mvvm.app.base

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gatebot.app.R
import com.mvvm.app.MApplication

abstract class BaseFragment<T : ViewDataBinding, out V : BaseViewModel<*>> : Fragment() {
    var baseActivity: BaseActivity<*, *>? = null
        private set
    var mRootView: View? = null
    private var mRootViewGrp: ViewGroup? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

    private var progressBar: ProgressBar? = null
    private var mToast: Toast? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    fun getVMProvider(): ViewModelProvider.AndroidViewModelFactory {
        return ViewModelProvider.AndroidViewModelFactory
            .getInstance(MApplication())
    }

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            //activity!!.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        mRootViewGrp = container
        viewModel.alertMsg.observeEvent(this, Observer {
            if (it != null){
                showQuickAlert(it)
            }
        })
        return mRootView
    }

    override fun onDetach() {
        hideKeyboard()
        dismissProgressBar()
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            //mActivity.openActivityOnTokenExpire();
        }
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    fun popBackStack(){
        fragmentManager?.popBackStack()
    }

    /**
     * common toast show for all screens
     *
     */
    fun showToast(message: String?) {
        if (mToast != null) mToast?.cancel();
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast?.show();
    }


    /**
     * common show progress bar for all screens
     *
     * @param parent layout of screen
     */
    fun showProgressBar(parent: ViewGroup) {
        baseActivity!!.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progressBar = ProgressBar(baseActivity, null, android.R.attr.progressBarStyleLarge)
        progressBar?.indeterminateDrawable?.setColorFilter(
            ContextCompat.getColor(baseActivity!!, R.color.colorAccent),
            android.graphics.PorterDuff.Mode.SRC_ATOP
        )
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
            is androidx.coordinatorlayout.widget.CoordinatorLayout -> {
                val params =
                    androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams(130, 130)
                params.gravity = Gravity.CENTER
                parent.addView(progressBar, params)
            }
            is ConstraintLayout -> {
                val params = ConstraintLayout.LayoutParams(130, 130)
                params.topToTop = ConstraintSet.PARENT_ID
                params.startToStart = ConstraintSet.PARENT_ID
                params.endToEnd = ConstraintSet.PARENT_ID
                params.bottomToBottom = ConstraintSet.PARENT_ID
                parent.addView(progressBar, params)
            }
        }
        progressBar?.visibility = View.VISIBLE  //To show ProgressBar
    }

    fun showQuickAlert(msg: String){
        /*val bottomAlert = BottomAlert(baseActivity!!)
        bottomAlert.showOnlyTextDialog(msg, true)*/
    }

    fun showTimePickerAlert(msg: String){
        /*val bottomAlert = BottomAlert(baseActivity!!)
        bottomAlert.showTimeSettingDialog(msg)*/
    }

    /**
     * common dismiss progress bar for all screens
     *
     */
    fun dismissProgressBar() {
        if(progressBar?.visibility == View.VISIBLE) {
            progressBar?.visibility = View.GONE     // To Hide ProgressBar
            //baseActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        baseActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}