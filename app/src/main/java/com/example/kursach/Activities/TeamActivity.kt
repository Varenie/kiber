package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Adapters.PlayersAdapter
import com.example.kursach.DataClasses.Player
import com.example.kursach.R
import com.example.kursach.Tables.TablePlayers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.properties.Delegates

class TeamActivity : AppCompatActivity() {
    var team_id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val intent = intent
        team_id = intent.getIntExtra("TEAM_ID", 0)


        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_player_add)

        fabAdd.setOnClickListener {
            openAddDialog()
        }

        updateUI()
    }

    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Добавить игрока")

        val inflater = LayoutInflater.from(this)
        val addWindow = inflater.inflate(R.layout.layout_player_add, null)
        dialog.setView(addWindow)

        val fullname = addWindow.findViewById<EditText>(R.id.et_player_name)
        val nickname = addWindow.findViewById<EditText>(R.id.et_nickname)
        val games = addWindow.findViewById<EditText>(R.id.et_games)
        val description = addWindow.findViewById<EditText>(R.id.et_description)


        dialog.setNegativeButton("Отменить") {dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.setPositiveButton("Подтвердить") {dialogInterface, which ->
            val tablePlayers = TablePlayers(this)

            val descriptionText = if (description.text.isNullOrBlank()){
                ""
            } else {
                description.text.toString()
            }

            val player = Player(
                team_id = 1,
                fullname = fullname.text.toString(),
                nickname = nickname.text.toString(),
                games = games.text.toString(),
                description = descriptionText
            )

            tablePlayers.addPlayer(player)
            tablePlayers.showDB()

            updateUI()

            dialogInterface.dismiss()
        }

        dialog.show()
    }

    private fun updateUI() {
        val tablePlayers = TablePlayers(this)

        val myRecycler = findViewById<RecyclerView>(R.id.rv_players)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val players = tablePlayers.getPlayers(team_id)

        val adapter = PlayersAdapter(players)
        myRecycler.adapter = adapter
    }
}