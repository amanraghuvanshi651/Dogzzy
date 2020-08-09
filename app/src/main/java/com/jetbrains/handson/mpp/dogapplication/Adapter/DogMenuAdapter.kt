package com.jetbrains.handson.mpp.dogapplication.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.jetbrains.handson.mpp.dogapplication.Activity.SearchedDogActivity
import com.jetbrains.handson.mpp.dogapplication.Model.DogsApi
import com.jetbrains.handson.mpp.dogapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dogs_rv_layout.view.*
import org.json.JSONObject


class DogMenuAdapter(val context: Context?, private var receivedNameList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dogs_rv_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return receivedNameList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dogsImages = ArrayList<DogsApi>()

        val name = receivedNameList[position]
        var logo_img: String


        AndroidNetworking.initialize(context);

        //Calling the url to get image list in json format
        AndroidNetworking.get("https://dog.ceo/api/breed/$name/images")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    val result = JSONObject(response)
                    val image = result.getJSONArray("message")

                    //iterate the array to get individual item
                    for (i in 0 until image.length()) {
                        val list = image.get(i)

                        dogsImages.add(
                            DogsApi(list.toString())
                        )
                    }

//                    val info = "position : " + position.toString() + " Dog Name : " + name +" Image Link" + dogsImages[0].toString()
//
//                    Log.i("data", "onResponse: $info")

                    if (dogsImages.isEmpty()) {
                        Toast.makeText(context, "No Images Found!!!", Toast.LENGTH_SHORT).show()
                        Log.i("data", "onResponse: No Images Found!!!")
                    } else {

                        logo_img = dogsImages[0].message
                        Picasso.get().load(logo_img).into(holder.itemView.dogImage)
                        Log.i("load", "receivedList: $receivedNameList")
                        val size = receivedNameList.size - 1
                        Log.i("load", "Size of the received List: $size")
                        Log.i("load", "position: $position")

                        if (receivedNameList.isNotEmpty()) {
//                            Log.i("load", "name at the position: ${receivedNameList[position]}")
                            if (size == 0) {
                                val newPosition = 0
                                Log.i("load", "onResponse: newPosition assigned")
                                val inlistdog = receivedNameList[newPosition]
                                val dogName = inlistdog.capitalize()
                                holder.itemView.dogBreadName.setText(dogName)
                            } else {
                                val inlistdog = receivedNameList[position]
                                val dogName = inlistdog.capitalize()
                                holder.itemView.dogBreadName.setText(dogName)
                            }
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("data", "onError: Error occurred")
                }

            })
    }


    inner class ViewHolder(v: View?) : RecyclerView.ViewHolder(v!!), View.OnClickListener {

        override fun onClick(v: View?) {

            //onclick function here

            if (v != null) {
                val position_adapter = adapterPosition
                val name = receivedNameList[position_adapter]
                Log.i("data", "onClick: $position_adapter $name")
                val intent = Intent(
                    context,
                    SearchedDogActivity::class.java
                )
                intent.putExtra("bread_name", "$name")
                context?.startActivity(intent)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}
