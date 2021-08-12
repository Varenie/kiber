package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.Adapters.PlayersAdapter
import com.example.kursach.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_player_add)

        fabAdd.setOnClickListener {
            openAddDialog()
        }
        val myRecycler = findViewById<RecyclerView>(R.id.rv_players)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val adapter = PlayersAdapter()
        myRecycler.adapter = adapter
    }

    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Добавить игрока")

        val inflater = LayoutInflater.from(this)
        val addWindow = inflater.inflate(R.layout.layout_player_add, null)
        dialog.setView(addWindow)

        dialog.setNegativeButton("Отменить") {dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.setPositiveButton("Подтвердить") {dialogInterface, which ->
            dialogInterface.dismiss()
        }

        dialog.show()
    }
}