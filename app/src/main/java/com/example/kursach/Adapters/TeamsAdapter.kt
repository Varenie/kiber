package com.example.kursach.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Activities.TeamActivity
import com.example.kursach.DataClasses.Team
import com.example.kursach.R

class TeamsAdapter(val teams: ArrayList<Team>): RecyclerView.Adapter<TeamsAdapter.VHolder>() {

    class VHolder(itemView: View, val teams: ArrayList<Team>): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_team_name)
        val slogan = itemView.findViewById<TextView>(R.id.tv_slogan)
        val wins = itemView.findViewById<TextView>(R.id.tv_wins)
        val loses = itemView.findViewById<TextView>(R.id.tv_loses)
        val draws = itemView.findViewById<TextView>(R.id.tv_draws)
        val description = itemView.findViewById<TextView>(R.id.tv_description)

        init {
            super.itemView
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, TeamActivity::class.java)
                intent.putExtra("TEAM_ID", teams[adapterPosition].id)
                context.startActivity(intent)
            }
        }

        fun bind(position: Int){
            name.text = teams[position].name
            slogan.text = teams[position].slogan
            wins.text = "Количество побед: ${teams[position].wins}"
            loses.text = "Количество поражений: ${teams[position].loses}"
            draws.text = "Количество ничьих: ${teams[position].draws}"
            description.text = teams[position].description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_teams, parent, false)

        return VHolder(view, teams)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}