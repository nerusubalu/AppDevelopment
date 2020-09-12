package com.example.arsmarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_appliance.*

class AddApplianceActivity : AppCompatActivity() {
    var pos = -1
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("rooms")
    var category: String = ""
    /*private val images = listOf<Int>(R.drawable.bulb,R.drawable.bedlamp,R.drawable.fan,R.drawable.ac,
        R.drawable.tv,R.drawable.curtain,R.drawable.washingmachine,
        R.drawable.barlight,R.drawable.exhaust,R.drawable.gas,R.drawable.geyser,R.drawable.socket_inside
        ,R.drawable.computer,R.drawable.tap)*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_appliance)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = parent?.getItemAtPosition(position).toString()
                pos = position
                //img_view.setImageResource(images[position])
            }
        }
    }


    fun addRoom(view: View) {
        val app = app_name.text.toString()+"_$category"
        Toast.makeText(this, "$mail/$room/$app", Toast.LENGTH_SHORT).show()
        myRef = database.getReference("$mail/$room/$app")
        myRef.setValue(false)
        applianceData("$mail/$room")
        //Toast.makeText(this, "$room$pos", Toast.LENGTH_SHORT).show()
    }
}