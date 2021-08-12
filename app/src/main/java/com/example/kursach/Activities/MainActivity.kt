package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.R
import com.example.kursach.Adapters.TeamsAdapter
import com.example.kursach.DataClasses.Team
import com.example.kursach.Tables.TableTeams
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val tableTeams = TableTeams(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add_team)

        fabAdd.setOnClickListener {
            openAddDialog()
        }

        val myRecycler = findViewById<RecyclerView>(R.id.rv_teams)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val teams = tableTeams.getTeams()

        val adapter = TeamsAdapter(teams)
        myRecycler.adapter = adapter
    }

    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Добавить команду")

        val inflater = LayoutInflater.from(this)
        val addWindow = inflater.inflate(R.layout.layout_team_add, null)
        dialog.setView(addWindow)

        val name = addWindow.findViewById<EditText>(R.id.et_team_name)
        val slogan = addWindow.findViewById<EditText>(R.id.et_slogan)
        val wins = addWindow.findViewById<EditText>(R.id.et_wins)
        val loses = addWindow.findViewById<EditText>(R.id.et_loses)
        val draws = addWindow.findViewById<EditText>(R.id.et_draws)
        val description = addWindow.findViewById<EditText>(R.id.et_description)

        dialog.setNegativeButton("Отменить") {dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.setPositiveButton("Подтвердить") {dialogInterface, which ->
            val team = Team(
                name = name.text.toString(),
                slogan = slogan.text.toString(),
                wins = wins.text.toString().toInt(),
                loses = loses.text.toString().toInt(),
                draws = draws.text.toString().toInt()
            )

            tableTeams.addTeam(team)
            tableTeams.showDB()

            updateUI()

            dialogInterface.dismiss()
        }

        dialog.show()
    }

    fun updateUI() {
        val myRecycler = findViewById<RecyclerView>(R.id.rv_teams)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val teams = tableTeams.getTeams()

        val adapter = TeamsAdapter(teams)
        myRecycler.adapter = adapter
    }
}