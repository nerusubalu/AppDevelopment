package com.example.arsmarthome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


var email = ""
var password = ""
var mail = ""
const val TAG = ""
var mAuth: FirebaseAuth? = null
val database = FirebaseDatabase.getInstance()
var preferences: SharedPreferences? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
    }

    fun signIn(view: View) {
        val user = mAuth!!.currentUser
        email = Email.editText?.text.toString()
        password = Password.editText?.text.toString()
        email = "nerusubalu@gmail.com"
        password = "I1oveindi@"
        if (user!!.isEmailVerified){
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = mAuth!!.currentUser
                        mail = email.split("@".toRegex()).map { it.trim() }[0]
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
        }
        else{
            mail = user.toString().split("@".toRegex()).map { it.trim() }[0]
            Toast.makeText(applicationContext, mail, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@MainActivity, RoomActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (!currentUser!!.isEmailVerified){
            mail = mAuth!!.currentUser!!.email!!.split("@".toRegex()).map { it.trim() }[0]
            Toast.makeText(applicationContext, mail, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@MainActivity, RoomActivity::class.java))
        }
        else{
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        }
    }
}
