package com.example.arsmarthome

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


var email = ""
var password = ""
var mail = ""
val TAG = ""
private var mAuth: FirebaseAuth? = null
val database = FirebaseDatabase.getInstance()
val roomNames: MutableList<String> = mutableListOf()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
    }

    fun signIn(view: View) {
        email = Email.editText?.text.toString()
        password = Password.editText?.text.toString()
        email = "nerusubalu@gmail.com"
        password = "I1oveindi@"
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    mail = email.split("@".toRegex()).map { it.trim() }[0]
                    Toast.makeText(applicationContext, "$mail", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, RoomActivity::class.java))
                    SimpleAsyncTask().execute()

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
}
class SimpleAsyncTask() : AsyncTask<Void?, Void?, Boolean?>(){

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        /*for(category in categories) {
            val myRef = database.getReference(mail)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var roomlist: MutableList<String> = mutableListOf()
                    for (room in dataSnapshot.children) {
                        roomlist.add(room.toString())
                        room_names.add(room.toString())
                    }
                    roomNames[category.toString()] = roomlist
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }*/
        return true
    }

    override fun onPostExecute(result: Boolean?) {
        val context: SimpleAsyncTask = this
        if(result!!){
            Log.d("Result:", result.toString())
            //Log.d("Result:", roomNames.toString())
        }
    }

}