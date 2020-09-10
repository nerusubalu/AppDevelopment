package com.example.arsmarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_room.*
import java.util.*


class AddRoomActivity : AppCompatActivity() {
    var room = ""
    var pos = -1

    val imagesvalues = listOf(R.drawable.bedroom,R.drawable.kitchen,R.drawable.livingroom,R.drawable.diningroom,
        R.drawable.bedroom,R.drawable.balcony,R.drawable.garage,R.drawable.office,
        R.drawable.aqua,R.drawable.industry,R.drawable.terrace)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                room = parent?.getItemAtPosition(position).toString()
                pos = position
                img_view.setImageResource(imagesvalues[position])
            }
        }
    }

    fun addRoom(view: View) {
        val room = room_name.text.toString()+"_$room"
        //val email = intent.extras?.get("main").toString()
        val myRef = database.getReference("$mail/$room/dummy")
        myRef.setValue(false)
        val roomName = room.split("_".toRegex()).map { it.trim() }
        val category = roomName[1].toLowerCase(Locale.ROOT)
        room_names.add(room)
        if (category in images.keys){
            images[category].let { room_images.add(it!!) }
        }
        else{
            images["garden"].let { room_images.add(it!!) }
        }
        Toast.makeText(this, "$room$pos", Toast.LENGTH_SHORT).show()
    }
}