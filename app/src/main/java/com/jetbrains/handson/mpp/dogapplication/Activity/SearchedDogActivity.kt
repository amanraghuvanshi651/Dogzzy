package com.jetbrains.handson.mpp.dogapplication.Activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.jetbrains.handson.mpp.dogapplication.Adapter.DogsAdapter
import com.jetbrains.handson.mpp.dogapplication.Model.DogsApi
import com.jetbrains.handson.mpp.dogapplication.R
import org.json.JSONObject

class SearchedDogActivity : AppCompatActivity() {

    val imageList = ArrayList<DogsApi>()

    private lateinit var dogsRV: RecyclerView
    private lateinit var dogNameText: TextView
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_dog)

        dogsRV = findViewById(R.id.recyclerView)
        dogNameText = findViewById(R.id.dogName)

        val intent: Intent = intent
        var name = intent.getStringExtra("bread_name")
        Log.i("data", "massage received: $name")

        val titleName = name.capitalize()

        dogNameText.text = titleName

        searchDog(name)

        //setting up staggered layout manager
        dogsRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        //default value to the recycler view
        dogsRV.adapter = DogsAdapter(this, imageList)

//            //searchFilter
//            arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,d)
//            dogNameText.setAdapter(arrayAdapter)

//        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar!!.setDisplayShowCustomEnabled(true)
//        supportActionBar!!.setCustomView(R.layout.coustom_action_bar)
//        supportActionBar!!.elevation = 0f
//        val view = supportActionBar!!.customView
    }

    private fun searchDog(name: String) {

        imageList.clear()

        AndroidNetworking.initialize(applicationContext);

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

                        imageList.add(
                            DogsApi(list.toString())
                        )
                    }

                    if (imageList.isEmpty()){
                        Toast.makeText(this@SearchedDogActivity,"No Images Found!!!",Toast.LENGTH_SHORT).show()
                    }else{

                        dogsRV.adapter = DogsAdapter(this@SearchedDogActivity, imageList)
                        Log.i("data", "imageList: $imageList")

                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("data", "onError: Error occurred")
                }

            })
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
