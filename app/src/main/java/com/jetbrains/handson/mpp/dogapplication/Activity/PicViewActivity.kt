package com.jetbrains.handson.mpp.dogapplication.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jetbrains.handson.mpp.dogapplication.R
import com.jetbrains.handson.mpp.dogapplication.ZoomLayout
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import android.os.Handler


class PicViewActivity : AppCompatActivity() {

    private val WRITE_EXTERNAL_STORAGE_CODE = 1

    private lateinit var full_screen_img: ImageView
    private lateinit var downloadBtn: RelativeLayout
    private lateinit var zoomlayout:ZoomLayout

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_view)

        val visible = View.VISIBLE
        val invisible = View.INVISIBLE

        full_screen_img = findViewById(R.id.dogImage_full_screen)
        downloadBtn = findViewById(R.id.download_btn)
        zoomlayout = findViewById(R.id.zoomLayout)


        Handler().postDelayed({

            downloadBtn.visibility = visible

        },500 )

        val intent: Intent = intent
        var rec_message = intent.getStringExtra("image_link")

        Picasso.get().load(rec_message).into(full_screen_img)

        downloadBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),WRITE_EXTERNAL_STORAGE_CODE)
                }else{
                    saveImage()
                }
            }
        }
    }

    private fun saveImage() {
        bitmap = (full_screen_img.getDrawable() as BitmapDrawable).bitmap

        val time: String = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis())
        val path: File = Environment.getExternalStorageDirectory()
        val dir = File(path.toString() + "/DCIM")
        dir.mkdirs()
        val imagename = "$time.PNG"
        val file = File(dir, imagename)
        val out: OutputStream

        try {
            out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush()
            out.close()

            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error, Image not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                Toast.makeText(this,"Permission not granted!!!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}