package com.example.arsmarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_room.*
import java.util.*

class AddRoomActivity : AppCompatActivity() {
    var room = ""
    var pos = -1
    var database = FirebaseDatabase.getInstance()

    val images = listOf<Int>(R.drawable.bedroom,R.drawable.kitchen,R.drawable.livingroom,R.drawable.diningroom,
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
                img_view.setImageResource(images[position])
            }
        }
    }

    fun addRoom(view: View) {
        val room = room_name.text.toString()
        val email = intent.extras?.get("main").toString()
        val myRef = database.getReference("$email/$room/dummy")
        myRef.setValue(false)
        room_names.add(room)
        storedata()
        Toast.makeText(this, "$room$pos", Toast.LENGTH_SHORT).show()
    }
}