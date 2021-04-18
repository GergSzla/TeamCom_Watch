package ie.wit.teamcomwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.models.Meeting
import kotlinx.android.synthetic.main.card_meeting.view.*


interface MeetingListener {
    fun onMeetingClicked(meeting: Meeting)
}

class MeetingsAdapter constructor(
    var meetings: ArrayList<Meeting>,
    private val listener: MeetingListener
): RecyclerView.Adapter<MeetingsAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.card_meeting, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val meeting = meetings[holder.adapterPosition]
        holder.bind(meeting, listener)
    }

    override fun getItemCount(): Int = meetings.size

    fun removeAt(position: Int) {
        meetings.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(meeting: Meeting, listener: MeetingListener) {
            itemView.tag = meeting

            itemView.txtMTitle.text = meeting.meeting_title
            itemView.txtMDesc.text = meeting.meeting_desc
            itemView.txtMDateAndTime.text = "${meeting.meeting_date_as_string} @ ${meeting.meeting_time_as_string}"
            itemView.txtMDateAndTime_end.text = "${meeting.meeting_date_as_string_end} @ ${meeting.meeting_time_as_string_end}"

            if(meeting.requested_meeting){
                itemView.txt_request.text = "[REQUESTED]"
            } else {
                itemView.txt_request.text = ""
            }

            if(meeting.online){
                itemView.txtMLocationElsePlatform.text = "Online via: ${meeting.meeting_platform}"
            } else {
                itemView.txtMLocationElsePlatform.text = "Location: ${meeting.meeting_location}"
            }

            itemView.setOnClickListener {
                listener.onMeetingClicked(meeting)
            }
        }
    }
}