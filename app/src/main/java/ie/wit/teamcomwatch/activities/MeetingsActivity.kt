package ie.wit.teamcomwatch.activities

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.adapters.MeetingListener
import ie.wit.teamcomwatch.adapters.MeetingsAdapter
import ie.wit.teamcomwatch.main.MainApp
import ie.wit.teamcomwatch.models.Meeting
import kotlinx.android.synthetic.main.activity_view_meetings.*
import java.util.ArrayList

class MeetingsActivity : WearableActivity(), MeetingListener {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var app: MainApp
    var meetingList = ArrayList<Meeting>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meetings)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

        // Enables Always-on
        setAmbientEnabled()

        getAllMeetings()
    }

    fun getAllMeetings() {
        database.child("channels").child("1-test").child("meetings")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    //hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {

                        val meeting = it.getValue<Meeting>(Meeting::class.java)
                        meetingList.add(meeting!!)

                        meetingsRecyclerView.apply {
                            // To align the edge children (first and last) with the center of the screen
                            isEdgeItemsCenteringEnabled = true


                            layoutManager = WearableLinearLayoutManager(this@MeetingsActivity)
                        }

                        meetingsRecyclerView.adapter = MeetingsAdapter(
                            meetingList,
                            this@MeetingsActivity
                        )
                        meetingsRecyclerView.adapter?.notifyDataSetChanged()

                        database.child("channels").child("1-test").child("meetings")
                            .removeEventListener(this)
                    }
                }
            })
    }

    override fun onMeetingClicked(meeting: Meeting) {
        TODO("Not yet implemented")
    }
}