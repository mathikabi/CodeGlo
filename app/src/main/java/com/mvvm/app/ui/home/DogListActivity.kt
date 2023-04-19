package com.mvvm.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gatebot.app.BR
import com.gatebot.app.R
import com.gatebot.app.databinding.ActivityDogListBinding
import com.gatebot.app.databinding.ItemDogBinding
import com.mvvm.app.base.BaseActivity
import com.mvvm.app.base.BaseNavigator
import com.mvvm.app.base.adapter.BaseRecyclerViewAdapter
import com.mvvm.app.base.adapter.OnDataBindCallback
import com.mvvm.app.data.remote.model.base.Resource
import com.mvvm.app.data.remote.model.devices.MData


class DogListActivity : BaseActivity<ActivityDogListBinding, DogListVM>(), BaseNavigator {

    private lateinit var viewModel: DogListVM
    private lateinit var dogListAdapter: BaseRecyclerViewAdapter<MData, ItemDogBinding>


    override fun getBindingVariable(): Int {
        return BR.dogListVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dog_list
    }

    override fun getViewModel(): DogListVM {
        viewModel = ViewModelProvider(this)[DogListVM::class.java]
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter()
        observer()
        viewModel.setNavigator(this)
    }

    private fun observer() {
        viewModel.dogceoRes.observeEvent(this, Observer {
            it.let { response ->
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        if (!it.data?.message.isNullOrEmpty()) {
                            val list = viewModel.getMessageList(it.data?.message!!)
                            dogListAdapter.clearAndAddDataSet(list)
                        }
                    }
                    Resource.Status.ERROR,
                    Resource.Status.FAILURE -> {

                    }
                }
            }
        })
    }

    private fun adapter() {
        dogListAdapter = BaseRecyclerViewAdapter(R.layout.item_dog, BR.itemDogVM,
            ArrayList(), object : OnDataBindCallback<ItemDogBinding> {
                override fun onItemClick(view: View, position: Int, v: ItemDogBinding) {
                    when (view.id) {
                        R.id.imgChecked -> {
                            selectedImages(view, position)
                        }
                    }
                }

                override fun onDataBind(v: ItemDogBinding, onClickListener: View.OnClickListener) {
                    v.imgDog.setOnClickListener(onClickListener)
                    v.imgChecked.setOnClickListener(onClickListener)
                }
            })
        mViewDataBinding?.rvDogList?.layoutManager = GridLayoutManager(this, 2)
        mViewDataBinding?.rvDogList?.adapter = dogListAdapter
    }

    private fun selectedImages(view: View, position: Int) {
        val list = viewModel.updatedMessage(position)
        dogListAdapter.clearAndAddDataSet(list)
    }


    override fun onClickView(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                viewModel.selectImageList.clear()
                viewModel.list.forEach {
                    if (it.isSelected == true) {
                        viewModel.selectImageList.add(it.message ?: "")
                    }
                }
                val i = Intent(this, DogDetailsActivity::class.java)
                i.putStringArrayListExtra("imageList", viewModel.selectImageList)
                startActivity(i)
            }
        }

    }

    override fun goTo(clazz: Class<*>, mExtras: Bundle?) {

    }
}