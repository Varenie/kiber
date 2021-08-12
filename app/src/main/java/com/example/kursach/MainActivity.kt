package com.example.kursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fb_add_team)

        fabAdd.setOnClickListener {
            //openAddDialog()
        }

        val myRecycler = findViewById<RecyclerView>(R.id.rv_teams)
        myRecycler.layoutManager = LinearLayoutManager(this)
        myRecycler.setHasFixedSize(true)

        val adapter = TeamsAdapter()
        myRecycler.adapter = adapter
    }

    private fun openAddDialog() {
        val dialog = AlertDialog.Builder(this)
    }
}