package ie.wit.teamcomwatch.activities

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.adapters.ReminderListener
import ie.wit.teamcomwatch.adapters.RemindersAdapter
import ie.wit.teamcomwatch.main.MainApp
import ie.wit.teamcomwatch.models.Reminder
import kotlinx.android.synthetic.main.activity_view_meetings.*
import kotlinx.android.synthetic.main.activity_view_reminders.*
import java.util.*

class RemindersActivity : WearableActivity(), ReminderListener {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var app: MainApp
    var reminderList = ArrayList<Reminder>()
    var userList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reminders)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference

        // Enables Always-on
        setAmbientEnabled()
        getAllUsers()
    }

    fun getAllReminders() {
        userList.forEach { it_ ->
            database.child("channels").child("1-test").child("reminders")
                .child(it_)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        //hideLoader(loader)
                        val children = snapshot.children
                        children.forEach {
                            val reminder = it.getValue<Reminder>(Reminder::class.java)
                            reminderList.add(reminder!!)

                            remindersRecyclerView.apply {
                                // To align the edge children (first and last) with the center of the screen
                                isEdgeItemsCenteringEnabled = false


                                layoutManager = WearableLinearLayoutManager(this@RemindersActivity)
                            }

                            remindersRecyclerView.adapter = RemindersAdapter(
                                reminderList,
                                this@RemindersActivity
                            )
                            remindersRecyclerView.adapter?.notifyDataSetChanged()

                            database.child("channels").child("1-test").child("reminders")
                                .child(it_)
                                .removeEventListener(this)
                        }
                    }
                })
        }
    }

    fun getAllUsers() {
        database.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    //hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {

                        userList.add(it.key.toString())

                        database.child("users")
                            .removeEventListener(this)
                        getAllReminders()
                    }
                }
            })
    }

    override fun onReminderClick(reminder: Reminder) {
        TODO("Not yet implemented")
    }
}