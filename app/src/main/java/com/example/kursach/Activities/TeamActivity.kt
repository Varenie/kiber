package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Adapters.PlayersAdapter
import com.example.kursach.Adapters.SimpleTouchHelperCallback
import com.example.kursach.DataClasses.Player
import com.example.kursach.R
import com.example.kursach.Tables.TablePlayers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class TeamActivity : AppCompatActivity() {
    var team_id by Delegates.notNull<Int>()

    lateinit var tablePlayers: TablePlayers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        tablePlayers = TablePlayers(this)

        val intent = intent
        team_id = intent.getIntExtra("TEAM_ID", 0)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_player_add)

        fabAdd.setOnClickListener {
            openAddDialog()
        }

        if (tablePlayers.isPlayersExist(team_id)) {
            val players = tablePlayers.getPlayers(team_id)
            updateUI(players)
        }
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
        val description = addWindow.findViewById<EditText>(R.id.et_player_description)


        dialog.setNegativeButton("Отменить") {dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.setPositiveButton("Подтвердить") {dialogInterface, which ->
            when {
                fullname.text.isNullOrBlank() -> {
                    Snackbar.make(addWindow, "Поле имени пусто", Snackbar.LENGTH_SHORT).show()
                    openAddDialog()
                }
            }
            val descriptionText = if (description.text.isNullOrBlank()){
                ""
            } else {
                description.text.toString()
            }

            val player = Player(
                team_id = team_id,
                fullname = fullname.text.toString(),
                nickname = nickname.text.toString(),
                games = games.text.toString(),
                description = descriptionText
            )

            tablePlayers.addPlayer(player)
            tablePlayers.showDB()

            val players = tablePlayers.getPlayers(team_id)
            updateUI(players)

            dialogInterface.dismiss()
        }

        dialog.show()
    }

    private fun updateUI(players: ArrayList<Player>) {
        val myRecycler = findViewById<RecyclerView>(R.id.rv_players)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val adapter = PlayersAdapter(players, this, team_id)
        myRecycler.adapter = adapter


        val callback = SimpleTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(myRecycler)
    }

    override fun onResume() {
        super.onResume()

        val players = tablePlayers.getPlayers(team_id)
        updateUI(players)
    }
}