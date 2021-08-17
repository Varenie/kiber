package com.example.kursach.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Activities.TeamActivity
import com.example.kursach.Activities.TeamUpdateActivity
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DRAWS
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_LOSES
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_NAME
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_SLOGAN
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_WINS
import com.example.kursach.DataClasses.Team
import com.example.kursach.R
import com.example.kursach.Tables.TablePlayers
import com.example.kursach.Tables.TableTeams

class TeamsAdapter(teams: ArrayList<Team>, val context: Context): RecyclerView.Adapter<TeamsAdapter.VHolder>(), ItemTouchHelperAdapter {
    var teams = teams

    class VHolder(itemView: View, val teams: ArrayList<Team>): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_team_name)
        val slogan = itemView.findViewById<TextView>(R.id.tv_slogan)
        val wins = itemView.findViewById<TextView>(R.id.tv_wins)
        val loses = itemView.findViewById<TextView>(R.id.tv_loses)
        val draws = itemView.findViewById<TextView>(R.id.tv_draws)
        val description = itemView.findViewById<TextView>(R.id.tv_description)

        init {
            super.itemView
            val context = itemView.context
            itemView.setOnClickListener { // обработка клика на элемент, передает в следующие активти id элемента, чтобы подгрузить игроков для соответсвтующей команды
                val intent = Intent(context, TeamActivity::class.java)
                intent.putExtra("TEAM_ID", teams[adapterPosition].id)
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener { // при длинном нажатии переводит на активти изменения, и подтягивает сатрые данные через intent
                val intent = Intent(context, TeamUpdateActivity::class.java)

                intent.putExtra(COLUMN_ID, teams[adapterPosition].id)
                intent.putExtra(COLUMN_NAME, teams[adapterPosition].name)
                intent.putExtra(COLUMN_SLOGAN, teams[adapterPosition].slogan)
                intent.putExtra(COLUMN_WINS, teams[adapterPosition].wins)
                intent.putExtra(COLUMN_LOSES, teams[adapterPosition].loses)
                intent.putExtra(COLUMN_DRAWS, teams[adapterPosition].draws)
                intent.putExtra(COLUMN_DESCRIPTION, teams[adapterPosition].description)

                context.startActivity(intent)
                true
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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    override fun onItemDismiss(position: Int) {
        val tableTeams = TableTeams(context)

        //костыль, но немного чинит баг
        teams = tableTeams.getTeams()
        tableTeams.deleteTeam(teams[position].id)
        teams.removeAt(position)

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, teams.size)
    }
}