package com.example.kursach.Tables

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.kursach.DBHelpers.DBHelperPlayers
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_FULLNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_GAMES
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_NICKNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_TEAM_ID
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.TABLE_NAME
import com.example.kursach.DataClasses.Player

class TablePlayers(context: Context) {
    private val TAG = "MYCHECK"

    private val dbHelper = DBHelperPlayers(context)
    private val db = dbHelper.writableDatabase

    private var cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

    private val indexId = cursor.getColumnIndex(COLUMN_ID)
    private val indexTeamId = cursor.getColumnIndex(COLUMN_TEAM_ID)
    private val indexFullname = cursor.getColumnIndex(COLUMN_FULLNAME)
    private val indexNickname = cursor.getColumnIndex(COLUMN_NICKNAME)
    private val indexGames = cursor.getColumnIndex(COLUMN_GAMES)
    private val indexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION)

    fun addPlayer(player: Player) {
        val values = ContentValues().apply {
            put(COLUMN_TEAM_ID, player.team_id)
            put(COLUMN_FULLNAME, player.fullname)
            put(COLUMN_NICKNAME, player.nickname)
            put(COLUMN_GAMES, player.games)
            put(COLUMN_DESCRIPTION, player.description)
        }

        db.insert(TABLE_NAME, null, values)
    }

    fun showDB() {
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val player = Player(
                cursor.getInt(indexId),
                cursor.getInt(indexTeamId),
                cursor.getString(indexFullname),
                cursor.getString(indexNickname),
                cursor.getString(indexGames),
                cursor.getString(indexDescription)
            )

            Log.d(TAG, player.toString())
            cursor.moveToNext()
        }
    }
}