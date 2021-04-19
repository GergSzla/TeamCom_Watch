package ie.wit.teamcomwatch.activities

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.adapters.EventAdapter
import ie.wit.teamcomwatch.adapters.EventListener_
import ie.wit.teamcomwatch.main.MainApp
import ie.wit.teamcomwatch.models.Event
import kotlinx.android.synthetic.main.activity_view_events.*
import kotlinx.android.synthetic.main.activity_view_meetings.*
import java.util.ArrayList

class EventsActivity : WearableActivity(), EventListener_ {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var app: MainApp
    var daysList = ArrayList<String>()
    var eventsList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_events)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

        // Enables Always-on
        setAmbientEnabled()

        getAllDays()
    }

    fun getAllEvents() {
        daysList.forEach { it_ ->
            database.child("channels").child("1-test").child("events").child("2021")
                .child("04").child(it_)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot.children
                        children.forEach {
                            val event = it.getValue<Event>(Event::class.java)
                            eventsList.add(event!!)

                            eventsRecyclerView.apply {
                                // To align the edge children (first and last) with the center of the screen
                                isEdgeItemsCenteringEnabled = true


                                layoutManager = WearableLinearLayoutManager(this@EventsActivity)
                            }

                            eventsRecyclerView.adapter = EventAdapter(
                                eventsList,
                                this@EventsActivity
                            )
                            eventsRecyclerView.adapter?.notifyDataSetChanged()

                            database.child("channels").child("1-test").child("events").child("2021")
                                .child("04").child(it_)
                                .removeEventListener(this)
                        }
                    }
                })
        }
    }

    fun getAllDays(){
        database.child("channels").child("1-test").child("events").child("2021").child("04")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    //hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {

                        daysList.add(it.key.toString())

                        database.child("channels").child("1-test").child("events").child("2021").child("04")
                            .removeEventListener(this)
                        getAllEvents()
                    }
                }
            })
    }
}