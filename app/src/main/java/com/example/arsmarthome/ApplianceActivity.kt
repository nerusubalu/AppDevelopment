package com.example.arsmarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ApplianceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliance)
        val room = intent.extras!!.getString("room name")
        Toast.makeText(applicationContext, room, Toast.LENGTH_SHORT).show()

    }
}