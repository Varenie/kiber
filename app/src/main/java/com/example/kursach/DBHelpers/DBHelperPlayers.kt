package com.example.kursach.DBHelpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperPlayers(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "Players.bd"
        const val DB_VERSION = 1
        const val TABLE_NAME = "Players"
        const val COLUMN_ID = "_id"
        const val COLUMN_TEAM_ID = "team_id"
        const val COLUMN_FULLNAME = "fullname"
        const val COLUMN_NICKNAME = "nickname"
        const val COLUMN_GAMES = "games"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TEAM_ID INTEGER," +
                "$COLUMN_FULLNAME TEXT," +
                "$COLUMN_NICKNAME TEXT," +
                "$COLUMN_GAMES TEXT," +
                "$COLUMN_DESCRIPTION TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}