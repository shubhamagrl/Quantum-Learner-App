package com.quanttmex.quantumcomputingbasics

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TopicsAdapter(private val topics: ArrayList<TopicData>) : RecyclerView.Adapter<TopicsAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return TopicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val currentItem = topics[position]
        holder.imageTitle.setImageResource(currentItem.image)
        holder.titleTitle.text = currentItem.title

        // Set click listener to each item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ReadingActivity::class.java)
            // Pass the string ID as an extra
            intent.putExtra("INDEX", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTitle: ImageView = itemView.findViewById(R.id.image_title)
        val titleTitle: TextView = itemView.findViewById(R.id.title_title)
    }
}
