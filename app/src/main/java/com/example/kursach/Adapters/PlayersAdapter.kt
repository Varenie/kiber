package com.example.kursach.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Activities.PlayerUpdateActivity
import com.example.kursach.Activities.TeamActivity
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_FULLNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_GAMES
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_NICKNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_TEAM_ID
import com.example.kursach.DataClasses.Player
import com.example.kursach.R
import com.example.kursach.Tables.TablePlayers

class PlayersAdapter(players: ArrayList<Player>, val context: Context, val team_id: Int): RecyclerView.Adapter<PlayersAdapter.VHolder>(), ItemTouchHelperAdapter {
    var players = players

    class VHolder(itemView: View, val players: ArrayList<Player>): RecyclerView.ViewHolder(itemView) {

        val fullname = itemView.findViewById<TextView>(R.id.tv_fullname)
        val nickname = itemView.findViewById<TextView>(R.id.tv_nickname)
        val games = itemView.findViewById<TextView>(R.id.tv_games)
        val description = itemView.findViewById<TextView>(R.id.tv_player_description)

        init {
            super.itemView
            itemView.setOnLongClickListener {
                val context = itemView.context
                val intent = Intent(context, PlayerUpdateActivity::class.java)

                intent.putExtra(COLUMN_ID, players[adapterPosition].id)
                intent.putExtra(COLUMN_TEAM_ID, players[adapterPosition].team_id)
                intent.putExtra(COLUMN_FULLNAME, players[adapterPosition].fullname)
                intent.putExtra(COLUMN_NICKNAME, players[adapterPosition].nickname)
                intent.putExtra(COLUMN_GAMES, players[adapterPosition].games)
                intent.putExtra(COLUMN_DESCRIPTION, players[adapterPosition].description)

                context.startActivity(intent)
                true
            }
        }

        fun bind(position: Int){
            fullname.text = players[position].fullname
            nickname.text = players[position].nickname
            games.text = "Игрет в: ${players[position].games}"
            description.text = players[position].description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_players, parent, false)

        return VHolder(view, players)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    override fun onItemDismiss(position: Int) {
        val tablePlayers = TablePlayers(context)

        //костыль, но немного чинит баг
        players = tablePlayers.getPlayers(team_id)
        tablePlayers.deletePlayer(players[position].id)
        players.removeAt(position)

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, players.size)
    }


}