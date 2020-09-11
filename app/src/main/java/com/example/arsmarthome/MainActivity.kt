package com.example.arsmarthome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


var email = ""
var password = ""
var mail = ""
const val TAG = ""
lateinit var mAuth: FirebaseAuth
val database = FirebaseDatabase.getInstance()
val room_names: MutableList<String> = mutableListOf()
val room_images: MutableList<Int> = mutableListOf()
var preferences: SharedPreferences? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        login.setOnClickListener {
            email = Email.editText?.text.toString()
            password = Password.editText?.text.toString()
            if(email.isEmpty()){
                Email.error = "Enter Email Address"
                Email.requestFocus()
            } else if(password.isEmpty()){
                Password.error = "Enter Password"
                Password.requestFocus()
            } else if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, "Fields Empty!", Toast.LENGTH_SHORT).show()
            } else if(!(email.isEmpty() && password.isEmpty())){
                signIn(email, password)
            }
        }
    }
    private fun signIn(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    setContentView(R.layout.ar_layout)
                    if(roomData(mAuth.currentUser!!.email.toString().split("@".toRegex()).map { it.trim() }[0])){
                        val handler = Handler()
                        handler.postDelayed(Runnable {
                            val user = mAuth.currentUser
                            updateUI(user)
                        }, 2000)
                    }
                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }

    }
    private fun updateUI(user: FirebaseUser?) {
        if (user!=null){
            setContentView(R.layout.ar_layout)
            val handler = Handler()
            handler.postDelayed(Runnable {
                mail = user.email.toString().split("@".toRegex()).map { it.trim() }[0]
                startActivity(Intent(this@MainActivity, RoomActivity::class.java))
            }, 250)
        }

    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }
}
