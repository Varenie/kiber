package com.example.kursach.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DRAWS
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_LOSES
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_NAME
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_SLOGAN
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_WINS
import com.example.kursach.DataClasses.Team
import com.example.kursach.R
import com.example.kursach.Tables.TableTeams

class TeamUpdateActivity : AppCompatActivity() {
    lateinit var name: EditText
    lateinit var slogan: EditText
    lateinit var wins: EditText
    lateinit var loses: EditText
    lateinit var draws: EditText
    lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_update)

        val intent = intent

        val team = Team(
            intent.getIntExtra(COLUMN_ID, 0),
            intent.getStringExtra(COLUMN_NAME).toString(),
            intent.getStringExtra(COLUMN_SLOGAN).toString(),
            intent.getIntExtra(COLUMN_WINS, 0),
            intent.getIntExtra(COLUMN_LOSES, 0),
            intent.getIntExtra(COLUMN_DRAWS, 0),
            intent.getStringExtra(COLUMN_DESCRIPTION).toString()
        )

        setUI(team)

        val btnUpdate = findViewById<Button>(R.id.btn_update_team)

        btnUpdate.setOnClickListener {
            updateTeam(team.id)
        }
    }

    private fun setUI(team: Team) {
        name = findViewById(R.id.et_team_name)
        slogan = findViewById(R.id.et_slogan)
        wins = findViewById(R.id.et_wins)
        loses = findViewById(R.id.et_loses)
        draws = findViewById(R.id.et_draws)
        description = findViewById(R.id.et_description)

        Log.d("MYCHECK", team.toString())

        name.setText(team.name)
        slogan.setText(team.slogan)
        wins.setText(team.wins.toString())
        loses.setText(team.loses.toString())
        draws.setText(team.draws.toString())
        description.setText(team.description)
    }

    private fun updateTeam(team_id: Int) {
        val tableTeams = TableTeams(this)
        val descriptionText = if (description.text.isNullOrBlank()){
            ""
        } else {
            description.text.toString()
        }

        val team = Team(
            team_id,
            name.text.toString(),
            slogan.text.toString(),
            wins.text.toString().toInt(),
            loses.text.toString().toInt(),
            draws.text.toString().toInt(),
            descriptionText
        )

        tableTeams.updateTeam(team)
        tableTeams.showDB()

        onBackPressed()
    }
}