package com.example.arsmarthome

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
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    updateUI(user)
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
            }, 1000)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    /*fun signIn(view: View) {
        val user = mAuth!!.currentUser
        email = Email.editText?.text.toString()
        password = Password.editText?.text.toString()
        email = "nerusubalu@gmail.com"
        password = "I1oveindi@"
        val currentUser = mAuth!!.currentUser
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    mail = email.split("@".toRegex()).map { it.trim() }[0]
                    RoomData()
                    Toast.makeText(applicationContext, mail, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, RoomActivity::class.java))
                    //SimpleAsyncTask().execute()
                    //RoomActivity().RoomData()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }*/
}


