package com.jetbrains.handson.mpp.dogapplication.Adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.dogapplication.Activity.PicViewActivity
import com.jetbrains.handson.mpp.dogapplication.Model.DogsApi
import com.jetbrains.handson.mpp.dogapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.searched_dogs_layout.view.*
import android.util.Pair
import androidx.cardview.widget.CardView
import androidx.core.util.component1
import androidx.core.util.component2

class DogsAdapter (val context:Context, private val dogsImages: ArrayList<DogsApi>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.searched_dogs_layout, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dogsImages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.i("data", "onBindViewHolder: assigning the images")
        Picasso.get().load(dogsImages[position].message).into(holder.itemView.dogImage_searched_layout)

    }


    inner class ViewHolder(v: View?) : RecyclerView.ViewHolder(v!!),View.OnClickListener{
        override fun onClick(v: View?) {


            //onclick function here
            val position = adapterPosition
            if (v != null) {
                val message = dogsImages[position].message
                val image = v.findViewById<ImageView>(R.id.dogImage_searched_layout)
                val card = v.findViewById<CardView>(R.id.card)
                Log.i("data", "onClick: $position $message")
                val intent = Intent(context,
                    PicViewActivity::class.java)
                val p1 = Pair.create(image as View?, "imageTransition")
                val p2 = Pair.create(card as View?, "cardTransition")
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(context as Activity, p1,p2)
                intent.putExtra("image_link","$message")
                context.startActivity(intent, options.toBundle())
            }

        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}

