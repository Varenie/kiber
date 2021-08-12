package com.example.kursach.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Activities.TeamActivity
import com.example.kursach.R

class TeamsAdapter: RecyclerView.Adapter<TeamsAdapter.VHolder>() {

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wins = itemView.findViewById<TextView>(R.id.tv_wins)

        init {
            super.itemView
            itemView.setOnClickListener {
                val context = itemView.context
                context.startActivity(Intent(context, TeamActivity::class.java))
            }
        }
        fun bind(position: Int){
            wins.text = "Количество побед: $position"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_teams, parent, false)

        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 5
    }
}