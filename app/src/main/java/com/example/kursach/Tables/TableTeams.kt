package com.example.kursach.Tables

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.kursach.DBHelpers.DBHelperPlayers
import com.example.kursach.DBHelpers.DBHelperTeams
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_DRAWS
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_LOSES
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_NAME
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_SLOGAN
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.COLUMN_WINS
import com.example.kursach.DBHelpers.DBHelperTeams.Companion.TABLE_NAME
import com.example.kursach.DataClasses.Team

class TableTeams(val context: Context) {
    private val TAG = "MYCHECK"

    private val dbHelper = DBHelperTeams(context)
    private val db = dbHelper.writableDatabase

    private var cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

    private val indexId = cursor.getColumnIndex(COLUMN_ID)
    private val indexName = cursor.getColumnIndex(COLUMN_NAME)
    private val indexSlogan = cursor.getColumnIndex(COLUMN_SLOGAN)
    private val indexWins = cursor.getColumnIndex(COLUMN_WINS)
    private val indexLoses = cursor.getColumnIndex(COLUMN_LOSES)
    private val indexDraws = cursor.getColumnIndex(COLUMN_DRAWS)
    private val indexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION)

    fun addTeam(team: Team) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, team.name)
            put(COLUMN_SLOGAN, team.slogan)
            put(COLUMN_WINS, team.wins)
            put(COLUMN_LOSES, team.loses)
            put(COLUMN_DRAWS, team.draws)
            put(COLUMN_DESCRIPTION, team.description)
        }

        db.insert(TABLE_NAME, null, values)
    }

    fun getTeams(): ArrayList<Team> {
        val teams = arrayListOf<Team>()
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val team = Team(
                cursor.getInt(indexId),
                cursor.getString(indexName),
                cursor.getString(indexSlogan),
                cursor.getInt(indexWins),
                cursor.getInt(indexLoses),
                cursor.getInt(indexDraws),
                cursor.getString(indexDescription)
            )

            teams.add(team)
            cursor.moveToNext()
        }

        return teams
    }

    fun deleteTeam(id: Int) {
        val tablePlayers = TablePlayers(context)

        db.delete(TABLE_NAME, "${COLUMN_ID} = ?", arrayOf(id.toString()))
        tablePlayers.deletePlayersByTeamId(id)
    }

    fun searchTeam(team_name: String): ArrayList<Team> {
        val teams = arrayListOf<Team>()
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = ?", arrayOf(team_name))
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val team = Team(
                cursor.getInt(indexId),
                cursor.getString(indexName),
                cursor.getString(indexSlogan),
                cursor.getInt(indexWins),
                cursor.getInt(indexLoses),
                cursor.getInt(indexDraws),
                cursor.getString(indexDescription)
            )

            teams.add(team)
            cursor.moveToNext()
        }

        return teams
    }
    fun updateTeam(team: Team) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, team.name)
            put(COLUMN_SLOGAN, team.slogan)
            put(COLUMN_WINS, team.wins)
            put(COLUMN_LOSES, team.loses)
            put(COLUMN_DRAWS, team.draws)
            put(COLUMN_DESCRIPTION, team.description)
        }

        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(team.id.toString()))
    }

    fun showDB() {
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val team = Team(
                cursor.getInt(indexId),
                cursor.getString(indexName),
                cursor.getString(indexSlogan),
                cursor.getInt(indexWins),
                cursor.getInt(indexLoses),
                cursor.getInt(indexDraws),
                cursor.getString(indexDescription)
            )

            Log.d(TAG, team.toString())
            cursor.moveToNext()
        }
    }
}