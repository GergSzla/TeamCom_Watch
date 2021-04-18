package ie.wit.teamcomwatch.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.activities.EventsActivity
import ie.wit.teamcomwatch.activities.MeetingsActivity
import ie.wit.teamcomwatch.activities.RemindersActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainApp : WearableActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference
        setContentView(R.layout.activity_main)
        // Enables Always-on
        setAmbientEnabled()

        txtViewEvents.setOnClickListener{
//            startActivity(intentFor<EventsActivity>())
        }
        txtViewMeetings.setOnClickListener{
            startActivity(Intent(this, MeetingsActivity::class.java))
        }
        txtViewEvents.setOnClickListener{
            startActivity(Intent(this, RemindersActivity::class.java))
        }
    }
}