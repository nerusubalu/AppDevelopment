package com.example.arsmarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

val appImages: Map<String, Int> = mapOf(
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
class ApplianceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliance)
        val room = intent.extras!!.getString("room name")
        Toast.makeText(applicationContext, "${mail}***${room}", Toast.LENGTH_SHORT).show()

    }
}