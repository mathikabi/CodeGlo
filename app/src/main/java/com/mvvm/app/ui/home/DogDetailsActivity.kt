package com.mvvm.app.ui.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gatebot.app.BR
import com.gatebot.app.R
import com.gatebot.app.databinding.ActivityDogDetailBinding
import com.gatebot.app.databinding.ItemDogBinding
import com.mvvm.app.base.BaseActivity
import com.mvvm.app.base.BaseNavigator
import com.mvvm.app.base.adapter.BaseRecyclerViewAdapter
import com.mvvm.app.base.adapter.OnDataBindCallback
import com.mvvm.app.data.remote.model.devices.DogsListResponse
import com.mvvm.app.data.remote.model.devices.MData
import java.io.*
import java.net.MalformedURLException
import java.net.URL


class DogDetailsActivity : BaseActivity<ActivityDogDetailBinding, DogDetailVM>(), BaseNavigator {

    val CHANNEL_ID = "Code Glo"
    val CHANNEL_NAME = "Code Glo ContentWriting"
    val CHANNEL_DESCRIPTION = "Code Glo NOTIFICATION"

    private lateinit var viewModel: DogDetailVM

    private lateinit var res: DogsListResponse

    private lateinit var imgList: BaseRecyclerViewAdapter<MData, ItemDogBinding>

    override fun getBindingVariable(): Int {
        return BR.dogDetailVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dog_detail
    }

    override fun getViewModel(): DogDetailVM {
        viewModel = ViewModelProvider(this)[DogDetailVM::class.java]
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val images = intent.getStringArrayListExtra("imageList")
        if (!images.isNullOrEmpty()) {
            viewModel.addList(images)
        }
        adapter()
        viewModel.setNavigator(this)
    }

    private fun adapter() {
        imgList = BaseRecyclerViewAdapter(R.layout.item_dog, BR.itemDogVM,
            ArrayList(), object : OnDataBindCallback<ItemDogBinding> {
                override fun onItemClick(view: View, position: Int, v: ItemDogBinding) {

                }

                override fun onDataBind(v: ItemDogBinding, onClickListener: View.OnClickListener) {
                    v.imgDog.setOnClickListener(onClickListener)
                }
            })
        mViewDataBinding?.tvSelectedAnimal?.layoutManager = GridLayoutManager(this, 2)
        mViewDataBinding?.tvSelectedAnimal?.adapter = imgList
        imgList.clearAndAddDataSet(viewModel.list)
    }

    override fun onClickView(v: View?) {
        when (v?.id) {
            R.id.download -> {
                viewModel.list.forEach {
                    downloadImageFromPath(it.message)
                }
            }
        }
    }

    override fun goTo(clazz: Class<*>, mExtras: Bundle?) {

    }

    private fun downloadImageFromPath(path: String?) {
        val imageurl = URL(path)
        val bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream())
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }

        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Saved to Gallery", Toast.LENGTH_SHORT).show()
            downloadMessageNotification()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
        }
    }


    private fun downloadMessageNotification() {
        val intent = codeGloIntent("CodeGlo")

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }

        notificationChannel()

        val nBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("CodeGlo")
            .setContentText("Download complete")
            .setSmallIcon(R.drawable.ic_notifications)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            // adding action button
            // .addAction(0, "LET CONTRIBUTE", pendingIntent2)
            .build()
        // finally notifying the notification
        val nManager = NotificationManagerCompat.from(this)
        nManager.notify(1, nBuilder)
    }

    private fun notificationChannel() {
        // check if the version is equal or greater
        // than android oreo version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // creating notification channel and setting
            // the description of the channel
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            // registering the channel to the System
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun codeGloIntent(link: String): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(link)
        return intent
    }
}