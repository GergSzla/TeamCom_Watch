package ie.wit.teamcomwatch.activities

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.main.MainApp

class EventsActivity : WearableActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as MainApp
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

        // Enables Always-on
        setAmbientEnabled()

        getAllEvents()
    }

    fun getAllEvents() {

    }
}