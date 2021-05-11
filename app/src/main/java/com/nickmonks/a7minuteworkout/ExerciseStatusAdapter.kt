package com.nickmonks.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>,
                            val context: Context): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    // implements our own viewHolder class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvItem = view.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout and return the viewholder
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_exercise_status, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // assign or bind the values of each view oinside the viewholder inflated layout.
        val model : ExerciseModel = items[position]

        // the viewHolder is returned oncreated and because it has a field called tvItem we access to it
        holder.tvItem.text = model.getId().toString()

        // this will be called every time we will notify data as changed in the exercise activity
        if (model.getIsSelected()){
            holder.tvItem.background = ContextCompat.
                    getDrawable(context, R.drawable.item_circular_thin_color_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else if (model.getIsCompleted()){
            holder.tvItem.background = ContextCompat.
            getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.tvItem.background = ContextCompat.
            getDrawable(context, R.drawable.item_circular_color_grey_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }

    }

    override fun getItemCount(): Int {
       // return the amount of items in the recycker view:
        return items.size
    }

}