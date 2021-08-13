package com.example.kursach.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.DataClasses.Player
import com.example.kursach.R

class PlayersAdapter(val players: ArrayList<Player>): RecyclerView.Adapter<PlayersAdapter.VHolder>() {
    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val fullname = itemView.findViewById<TextView>(R.id.tv_fullname)
        val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
        val games = itemView.findViewById<TextView>(R.id.tv_games)
        val description = itemView.findViewById<TextView>(R.id.tv_player_description)

        fun bind(position: Int, players: ArrayList<Player>){
            fullname.text = players[position].fullname
            nickname.text = players[position].nickname
            games.text = "Игрет в: ${players[position].games}"
            description.text = players[position].description

            Log.d("MYCHECK", players[position].description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_players, parent, false)

        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position, players)
    }

    override fun getItemCount(): Int {
        return players.size
    }
}