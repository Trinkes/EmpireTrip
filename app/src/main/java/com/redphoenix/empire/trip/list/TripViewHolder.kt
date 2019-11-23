package com.redphoenix.empire.trip.list

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redphoenix.empire.trip.BuildConfig
import com.redphoenix.empire.trip.R
import io.reactivex.subjects.PublishSubject

class TripViewHolder(
    itemView: View,
    private val tripClicks: PublishSubject<Int>
) : RecyclerView.ViewHolder(itemView) {
    private val pilotName: TextView = itemView.findViewById(R.id.pilot_name)
    private val pilotAvatar: ImageView = itemView.findViewById(R.id.pilot_avatar)
    private val pickUpLocation: TextView = itemView.findViewById(R.id.pick_up_location)
    private val dropOffLocation: TextView = itemView.findViewById(R.id.drop_off_location)
    private val rating: RatingBar = itemView.findViewById(R.id.rating_bar)

    fun bind(item: TripsListView.TripViewEntity) {
        pilotName.text = item.pilotName
        pickUpLocation.text = item.pickUpLocation
        dropOffLocation.text = item.dropOffLocation
        Glide.with(pilotAvatar).load("${BuildConfig.BASE_HOST}${item.pilotAvatar}")
            .into(pilotAvatar)
        if (item.pilotRating == null) {
            rating.visibility = View.GONE
        } else {
            rating.visibility = View.VISIBLE
            rating.rating = item.pilotRating
        }

        itemView.setOnClickListener { tripClicks.onNext(item.id) }
    }

}
