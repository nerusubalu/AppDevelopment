package com.example.arsmarthome

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

val appImages: Map<String, Int> = mapOf(
    "bedroom" to R.drawable.bedroom,
    "master bedroom" to R.drawable.bedroom,
    "kitchen" to R.drawable.kitchen,
    "living room" to R.drawable.living,
    "balcony" to R.drawable.balcony,
    "dinning hall" to R.drawable.dinning,
    "terrace" to R.drawable.terrace,
    "garage" to R.drawable.garage,
    "garden" to R.drawable.garden,
    "farmhouse" to R.drawable.farmhouse,
    "office" to R.drawable.office,
    "industry" to R.drawable.industry,
    "aqua" to R.drawable.aqua,
    "security" to R.drawable.security
)


class ApplianceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliance)
        Toast.makeText(applicationContext, "${mail}***${room}", Toast.LENGTH_SHORT).show()
        addStoredData()
    }

    private fun addStoredData() {
        val app_names: MutableList<String> = mutableListOf()
        val app_images: MutableList<String> = mutableListOf()
        val list =
            preferences!!.getStringSet(room, setOf<String>())?.toMutableList<String>()?.sorted()
        for(li in list!!.sorted()){
            if("_" in li){
                val appName = li.split("_".toRegex()).map { it.trim() }
                val category = appName[1]
                if(li !in app_names){
                    app_names.add(li)
                    //images[category]?.let { app_images.add(it) }
                }
            }
        }
        storeAppData(room, app_names)
        Toast.makeText(applicationContext, app_names.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        addStoredData()
    }

    override fun onResume() {
        super.onResume()
        addStoredData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Toast.makeText(this,
                    item.title.toString() + " Clicked ",
                    Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this,Settings::class.java))
                true
            }
            R.id.add_room -> {
                Toast.makeText(this,
                    item.title.toString() + " Clicked ",
                    Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this,AddApplianceActivity::class.java)
                intent.putExtra("room",room)
                startActivity(intent)
                true
            }
            R.id.dial -> {
                Toast.makeText(this,
                    item.title.toString() + " Clicked",
                    Toast.LENGTH_SHORT)
                    .show()
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse("tel:"+"7997678666")
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



fun applianceData(ref: String){
    val app_names: MutableList<String> = mutableListOf()
    val app_images: MutableList<String> = mutableListOf()
    val myRef = database.getReference(ref)
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (app in dataSnapshot.children) {
                if("_" in app.key.toString()){
                    val appName = app.key.toString().split("_".toRegex()).map { it.trim() }
                    val category = appName[1]
                    if (app.key.toString() !in app_names) {
                        app_names.add(app.key.toString())
                        //appImages[category]?.let { room_images.add(it) }
                    }
                }

            }
            Log.w(TAG, "$app_names")
            storeAppData(ref.split("/".toRegex()).map { it.trim() }[1],app_names)
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    })
}


fun storeAppData(key: String, app_names: MutableList<String>) {
    val editor = preferences!!.edit()
    editor.putStringSet(key, app_names.toSet())
    editor.apply()
}