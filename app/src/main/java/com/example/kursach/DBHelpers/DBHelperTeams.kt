package com.example.kursach.DBHelpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperTeams(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        const val DB_NAME = "Teams.db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "Teams"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "team_name"
        const val COLUMN_SLOGAN = "slogan"
        const val COLUMN_WINS = "wins"
        const val COLUMN_LOSES = "loses"
        const val COLUMN_DRAWS = "draws"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_SLOGAN TEXT," +
                "$COLUMN_WINS INTEGER," +
                "$COLUMN_LOSES INTEGER," +
                "$COLUMN_DRAWS INTEGER," +
                "$COLUMN_DESCRIPTION TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


}