package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Adapters.SimpleTouchHelperCallback
import com.example.kursach.R
import com.example.kursach.Adapters.TeamsAdapter
import com.example.kursach.DataClasses.Team
import com.example.kursach.Tables.TableTeams
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var tableTeams: TableTeams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tableTeams = TableTeams(this)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add_team)

        fabAdd.setOnClickListener {
            openAddDialog()
        }


        updateUI(tableTeams.getTeams())

        val searchView = findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    updateUI(tableTeams.searchTeam(query))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun openAddDialog() {
        val tableTeams = TableTeams(this)
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
            when {
                name.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Поле навзания пусто", Toast.LENGTH_SHORT).show()
                }
                slogan.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Поле девиза пусто", Toast.LENGTH_SHORT).show()
                }
                wins.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Поле побед пусто", Toast.LENGTH_SHORT).show()
                }
                loses.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Поле поражений пусто", Toast.LENGTH_SHORT).show()
                }
                draws.text.isNullOrBlank() -> {
                    Toast.makeText(this, "Поле ничьихЛ пусто", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val descriptionText = if (description.text.isNullOrBlank()){
                        ""
                    } else {
                        description.text.toString()
                    }

                    val team = Team(
                        name = name.text.toString(),
                        slogan = slogan.text.toString(),
                        wins = wins.text.toString().toInt(),
                        loses = loses.text.toString().toInt(),
                        draws = draws.text.toString().toInt(),
                        description = descriptionText
                    )

                    tableTeams.addTeam(team)
                    tableTeams.showDB()

                    updateUI(tableTeams.getTeams())
                }
            }

            dialogInterface.dismiss()
        }

        dialog.show()
    }

    private fun updateUI(teams: ArrayList<Team>) {

        val myRecycler = findViewById<RecyclerView>(R.id.rv_teams)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val adapter = TeamsAdapter(teams, this)
        myRecycler.adapter = adapter

        val callback = SimpleTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(myRecycler)
    }

    override fun onResume() {
        super.onResume()

        updateUI(tableTeams.getTeams())
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        updateUI(tableTeams.getTeams())
    }
}