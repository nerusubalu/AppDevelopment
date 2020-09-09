package com.example.arsmarthome

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_room.*


val images: Map<String,Int> = mapOf(
    "bedroom" to R.drawable.bedroom,
    "kitchen" to R.drawable.kitchen,
    "living" to R.drawable.livingroom,
    "balcony" to R.drawable.balcony
)
class RoomActivity : AppCompatActivity() {

    val room_names: MutableList<String> = mutableListOf()
    val room_images: MutableList<Int> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        //Toast.makeText(applicationContext, "$roomNames", Toast.LENGTH_SHORT).show()
        RoomData()
    }

    fun RoomData(){
        val myRef = database.getReference(mail)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (room in dataSnapshot.children) {
                    Toast.makeText(
                        applicationContext,
                        room.key + "*******" + room.value,
                        Toast.LENGTH_SHORT
                    ).show()
                    room_names.add(room.key.toString())
                    for (category in images.keys) {
                        if (room.key.toString() in category) {
                            images[category]?.let { room_images.add(it) }
                        }
                    }
                }
                setPage()
                Toast.makeText(applicationContext, "$room_images+***+$room_names", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun setPage() {
        val adapter =MyRoomAdapter(this, room_images, room_names, mail)
        recycleview.adapter = adapter
        val orientation =resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycleview.layoutManager = GridLayoutManager(this, 2)
        } else {
            recycleview.layoutManager = GridLayoutManager(this,1)
        }
    }

}

