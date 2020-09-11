package com.example.arsmarthome

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
import androidx.recyclerview.widget.LinearLayoutManager
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
    "dinning hall" to R.drawable.diningroom,
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
    }
    private fun addStoredData() {
        val list =
            preferences!!.getStringSet("imageNames", setOf<String>())?.toMutableList<String>()?.sorted()
        for(li in list!!.sorted()){
            val roomName = li.split("_".toRegex()).map { it.trim() }
            val category = roomName[1].toLowerCase(Locale.ROOT)
            if(li !in room_names){
                room_names.add(li)
                images[category]?.let { room_images.add(it) }
            }
        }
        storeData()
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
        addStoredData()
    }

    override fun onResume() {
        super.onResume()
        addStoredData()
    }

    override fun onStart() {
        super.onStart()
        if (room_images.isNotEmpty() && room_names.isNotEmpty()){
            setPage()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!mAuth.currentUser!!.isEmailVerified) {
            finishAffinity()
        }
    }
}

fun roomData(mail: String): Boolean {
    val myRef = database.getReference(mail)
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (room in dataSnapshot.children) {
                val roomName = room.key.toString().split("_".toRegex()).map { it.trim() }
                val category = roomName[1].toLowerCase(Locale.ROOT)
                if (room.key.toString() !in room_names) {
                    room_names.add(room.key.toString())
                    images[category]?.let { room_images.add(it) }
                }
            }
            storeData()
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
    return true
}

fun storeData() {
    val editor = preferences!!.edit()
    editor.putStringSet("imageNames", room_names.toSet())
    editor.apply()
}