package com.jetbrains.handson.mpp.dogapplication.Activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jetbrains.handson.mpp.dogapplication.Adapter.DogMenuAdapter
import com.jetbrains.handson.mpp.dogapplication.R


class MainActivity : AppCompatActivity() {

    private lateinit var dogsRV: RecyclerView

    private lateinit var arrayAdapter: ArrayAdapter<String>
    val nameList = ArrayList<String>()
    val displayList = ArrayList<String>()
    val sortedNameList = ArrayList<String>()
    val displyimageList = ArrayList<String>()
    private lateinit var head_tv: TextView
    private lateinit var sub_text: TextView
    private lateinit var search_text: EditText
    private lateinit var searchBtn: FloatingActionButton
    private lateinit var notFound: RelativeLayout
    private lateinit var progress_bar:ProgressBar
//    private lateinit var search_btn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Loading data
        loadData()


        //sorting the List
        nameList.sort()

        //Hooks to the views
        dogsRV = findViewById(R.id.recyclerView)
        head_tv = findViewById(R.id.head)
        sub_text = findViewById(R.id.sub_text)
        search_text = findViewById(R.id.search_et)
        searchBtn = findViewById(R.id.nav_bar_search)
        notFound = findViewById(R.id.not_found)
        progress_bar = findViewById(R.id.progress)


        //Animations
        val animation_out: Animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        val animation_in: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)


        //setting up staggered layout manager
        dogsRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        //default value to the recycler view
        dogsRV.adapter = DogMenuAdapter(this@MainActivity, displayList)

        //searchFilter
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)

        //Search button on click fun
        searchBtn.setOnClickListener {
            var visiblity = View.GONE
            head_tv.startAnimation(animation_out)
            head_tv.visibility = visiblity
            head_tv.startAnimation(animation_out)
            sub_text.visibility = visiblity
            sub_text.startAnimation(animation_out)
            searchBtn.visibility = visiblity
            searchBtn.startAnimation(animation_out)

            var visiblity_opposite = View.VISIBLE
            search_text.visibility = visiblity_opposite
            search_text.startAnimation(animation_in)

            search_text.isEnabled = true
            search_text.requestFocus()

            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        }

        //Search view on text change listener
        search_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i2: Int,
                i3: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i2: Int,
                i3: Int
            ) {

//                val notVisible = View.INVISIBLE
//                val visible = View.VISIBLE
//
//
//                val str = charSequence.toString()
//                Log.i("editTextChange", "onTextChanged: ${str}")
//                if (charSequence!!.isNotEmpty()) {
//                    displayList.clear()
//                    displyimageList.clear()
//
//                    val dogName = charSequence.toString()
//
//                    val search = dogName.toLowerCase()
//                    nameList.forEach {
//                        if (it.toLowerCase().contains(search)) {
//                            displayList.add(it)
//                            notFound.visibility = notVisible
//                            Log.i("load", "onTextChanged: $it")
//                        }
//                    }
//
//                } else {
//                    notFound.visibility = notVisible
//                    displayList.clear()
//                    displayList.addAll(nameList)
//                    dogsRV.adapter?.notifyDataSetChanged()
//                }
            }

            override fun afterTextChanged(edit: Editable) {
//                if (edit.length != 0) {
//                    // Business logic for search here
//
//                    val notVisible = View.INVISIBLE
//                    val visible = View.VISIBLE
//
//
//                    Log.i("load", "displayList: $displayList")
//                    if (displayList.isEmpty()) {
//                        notFound.visibility = visible
//                        dogsRV.adapter?.notifyDataSetChanged()
//                    } else {
//                        notFound.visibility = notVisible
//                        dogsRV.adapter?.notifyDataSetChanged()
//                    }
//                }
                filter(edit.toString())
            }
        })
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        displayList.clear()
//        val filterdNames: ArrayList<String> = ArrayList()

        //looping through existing elements
        for (s in nameList) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                displayList.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        dogsRV.adapter?.notifyDataSetChanged()
    }

    private fun loadData() {
        nameList.add("affenpinscher")
        nameList.add("african")
        nameList.add("airedale")
        nameList.add("akita")
        nameList.add("appenzeller")
        nameList.add("basenji")
        nameList.add("beagle")
        nameList.add("bluetick")
        nameList.add("borzoi")
        nameList.add("bouvier")
        nameList.add("boxer")
        nameList.add("brabancon")
        nameList.add("briard")
        nameList.add("cairn")
        nameList.add("chihuahua")
        nameList.add("chow")
        nameList.add("clumber")
        nameList.add("cockapoo")
        nameList.add("coonhound")
        nameList.add("cotondetulear")
        nameList.add("dachshund")
        nameList.add("dalmatian")
        nameList.add("dhole")
        nameList.add("dingo")
        nameList.add("doberman")
        nameList.add("elkhound")
        nameList.add("entlebucher")
        nameList.add("eskimo")
        nameList.add("germanshepherd")
        nameList.add("groenendael")
        nameList.add("havanese")
        nameList.add("husky")
        nameList.add("keeshond")
        nameList.add("kelpie")
        nameList.add("komondor")
        nameList.add("kuvasz")
        nameList.add("labrador")
        nameList.add("leonberg")
        nameList.add("lhasa")
        nameList.add("malamute")
        nameList.add("malinois")
        nameList.add("maltese")
        nameList.add("mexicanhairless")
        nameList.add("mix")
        nameList.add("newfoundland")
        nameList.add("otterhound")
        nameList.add("papillon")
        nameList.add("pekinese")
        nameList.add("pembroke")
        nameList.add("pitbull")
        nameList.add("pomeranian")
        nameList.add("pug")
        nameList.add("puggle")
        nameList.add("pyrenees")
        nameList.add("redbone")
        nameList.add("rottweiler")
        nameList.add("saluki")
        nameList.add("samoyed")
        nameList.add("schipperke")
        nameList.add("shiba")
        nameList.add("shihtzu")
        nameList.add("stbernard")
        nameList.add("vizsla")
        nameList.add("weimaraner")
        nameList.add("whippet")
        nameList.sort()
        displayList.addAll(nameList)
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    //OnBack pressed fun
    override fun onBackPressed() {

        //Animations
        val animation_out: Animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        val animation_in: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)

        var visible_onback = View.INVISIBLE
        var notVisible_onback = View.VISIBLE

        if (head_tv.visibility != notVisible_onback) {

            search_text.startAnimation(animation_out)
            search_text.visibility = visible_onback

            //reloading the view
            head_tv.visibility = notVisible_onback
            head_tv.startAnimation(animation_in)
            sub_text.visibility = notVisible_onback
            sub_text.startAnimation(animation_in)
            searchBtn.visibility = notVisible_onback
            searchBtn.startAnimation(animation_in)

            Handler().postDelayed({

                progress_bar.visibility = visible_onback
                //reloading the home page
                displayList.clear()
                displayList.addAll(nameList)
                dogsRV.adapter?.notifyDataSetChanged()

            },600)

            progress_bar.visibility = notVisible_onback

        } else {
            super.onBackPressed()
        }


    }
}