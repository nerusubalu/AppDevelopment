package com.example.arsmarthome

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_room.*
import java.util.*

val images: Map<String, Int> = mapOf(
    "bedroom" to R.drawable.bedroom,
    "master bedroom" to R.drawable.bedroom,
    "kitchen" to R.drawable.kitchen,
    "living room" to R.drawable.livingroom,
    "balcony" to R.drawable.balcony,
    "dinning" to R.drawable.diningroom,
    "terrace" to R.drawable.terrace,
    "garage" to R.drawable.garage,
    "garden" to R.drawable.garden,
    "farmhouse" to R.drawable.farmhouse,
    "office" to R.drawable.office,
    "industry" to R.drawable.industry,
    "aqua" to R.drawable.aqua,
    "security" to R.drawable.security
)

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        mAuth = FirebaseAuth.getInstance()
        preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        if (!preferences!!.getStringSet("imageNames", mutableSetOf())?.isEmpty()!!){
            addStoredData()
        }
        else{
            RoomData()
        }
    }
    fun RoomData(){
        val myRef = database.getReference(mail)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (room in dataSnapshot.children) {
                    //Toast.makeText(applicationContext, room.key + "*******" + room.value, Toast.LENGTH_SHORT).show()
                    val roomName = room.key.toString().split("_".toRegex()).map { it.trim() }
                    val category = roomName[1].toLowerCase(Locale.ROOT)
                    if (room.key.toString() !in room_names) {
                        room_names.add(room.key.toString())
                        images[category]?.let { room_images.add(it) }
                        //addImages(room.key.toString())
                    }
                }
                storedata()
                if (room_images.isNotEmpty() && room_names.isNotEmpty()) {
                    setPage()
                }
                //Toast.makeText(applicationContext, "$room_images+***+$room_names", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
/*
    private fun addImages(room: String) {
        for (category in images.keys) {
            if (category in room) {
                images[category]?.let { room_images.add(it) }
            }
        }
    }*/
    private fun addStoredData() {
        val list =
            preferences!!.getStringSet("imageNames", setOf<String>())?.toMutableList<String>()?.sorted()
        for(li in list!!.sorted()){
            //Toast.makeText(applicationContext, li, Toast.LENGTH_SHORT).show()
            val roomName = li.split("_".toRegex()).map { it.trim() }
            val category = roomName[1].toLowerCase(Locale.ROOT)
            if(li !in room_names){
                room_names.add(li)
                images[category]?.let { room_images.add(it) }
            }
            //Toast.makeText(applicationContext, room_names.toString(), Toast.LENGTH_SHORT).show()
            //addImages(li)
        }
        storedata()
        if (room_images.isNotEmpty() && room_names.isNotEmpty()){
            setPage()
        }
    }
    private fun setPage() {
        val adapter =MyRoomAdapter(this, room_images, room_names, mail)
        recycleview.adapter = adapter
        val orientation =resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycleview.layoutManager = GridLayoutManager(this, 2)
        } else {
            recycleview.layoutManager = GridLayoutManager(this, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Toast.makeText(
                    this,
                    item.title.toString() + " Clicked ",
                    Toast.LENGTH_SHORT
                )
                    .show()
                startActivity(Intent(this, Settings::class.java))
                true
            }
            R.id.add_room -> {
                Toast.makeText(
                    this,
                    item.title.toString() + " Clicked ",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val intent = Intent(this, AddRoomActivity::class.java)
                intent.putExtra("main", mail)
                startActivity(intent)
                true
            }
            R.id.dial -> {
                Toast.makeText(
                    this,
                    item.title.toString() + " Clicked",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse("tel:" + "7997678666")
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (!preferences!!.getStringSet("imageNames", mutableSetOf())?.isEmpty()!!){
            addStoredData()
        }
        else{
            RoomData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!preferences!!.getStringSet("imageNames", mutableSetOf())?.isEmpty()!!){
            addStoredData()
        }
        else{
            RoomData()
        }
    }

    override fun onStart() {
        super.onStart()
        if (room_images.isNotEmpty() && room_names.isNotEmpty()){
            setPage()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!mAuth!!.currentUser!!.isEmailVerified) {
            finishAffinity()
        }
    }
}

fun RoomData(){
    val myRef = database.getReference(mail)
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (room in dataSnapshot.children) {
                //Toast.makeText(applicationContext, room.key + "*******" + room.value, Toast.LENGTH_SHORT).show()
                val roomName = room.key.toString().split("_".toRegex()).map { it.trim() }
                val category = roomName[1].toLowerCase(Locale.ROOT)
                if (room.key.toString() !in room_names) {
                    room_names.add(room.key.toString())
                    images[category]?.let { room_images.add(it) }
                    //addImages(room.key.toString())
                }
            }
            storedata()
            //Toast.makeText(applicationContext, "$room_images+***+$room_names", Toast.LENGTH_SHORT).show()
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
}

fun storedata() {
    val editor = preferences!!.edit()
    editor.putStringSet("imageNames", room_names.toSet())
    editor.apply()
}

