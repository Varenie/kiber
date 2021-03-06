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

class TablePlayers(context: Context) { //класс, где прописаны все функции, используемы для взаимодействия с бд
    private val TAG = "MYCHECK"

    // инициализируем dbHelper И получаем от него бд
    private val dbHelper = DBHelperPlayers(context)
    private val db = dbHelper.writableDatabase

    //инициализируем курсор, с помощью которого будем читать данные
    private var cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

    //создаем индексы для более удобного считывания инфы
    private val indexId = cursor.getColumnIndex(COLUMN_ID)
    private val indexTeamId = cursor.getColumnIndex(COLUMN_TEAM_ID)
    private val indexFullname = cursor.getColumnIndex(COLUMN_FULLNAME)
    private val indexNickname = cursor.getColumnIndex(COLUMN_NICKNAME)
    private val indexGames = cursor.getColumnIndex(COLUMN_GAMES)
    private val indexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION)

    fun addPlayer(player: Player) {
        //создаем contentValues, для загрузки туда данных, и указываем какие данные в какую колнку идут
        val values = ContentValues().apply {
            put(COLUMN_TEAM_ID, player.team_id)
            put(COLUMN_FULLNAME, player.fullname)
            put(COLUMN_NICKNAME, player.nickname)
            put(COLUMN_GAMES, player.games)
            put(COLUMN_DESCRIPTION, player.description)
        }

        // а затем добавляем эти значения в бд
        db.insert(TABLE_NAME, null, values)
    }

    fun getPlayers(team_id: Int): ArrayList<Player> {

        val players = arrayListOf<Player>()
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TEAM_ID = ?", arrayOf(team_id.toString()))
        //перемещаемся к первому элементу в ответе
        cursor.moveToFirst()

        //пока курсор не дошел до последнего элемента, добавляем их в массив
        while (!cursor.isAfterLast) {
            val player = Player(
                cursor.getInt(indexId),
                cursor.getInt(indexTeamId),
                cursor.getString(indexFullname),
                cursor.getString(indexNickname),
                cursor.getString(indexGames),
                cursor.getString(indexDescription)
            )

            players.add(player)
            //двигаем курсор на следующий элемент
            cursor.moveToNext()
        }

        return players
    }

    fun isPlayersExist(team_id: Int): Boolean {
        //проверяет есть ли игроки у команды, чтобы  потом не словить краш при создании списка
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TEAM_ID = ?", arrayOf(team_id.toString()))
        cursor.moveToFirst()
        return cursor.count != 0
    }

    fun deletePlayer(id: Int) { //удалаяет игрока по Id
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deletePlayersByTeamId(team_id: Int) { // удаляет игроков по id команды, используется при удалени команды
        db.delete(TABLE_NAME, "$COLUMN_TEAM_ID = ?", arrayOf(team_id.toString()))
    }

    fun updatePlayer(player: Player) {
        //здесь также, как и при добавлении
        val values = ContentValues().apply {
            put(COLUMN_TEAM_ID, player.team_id)
            put(COLUMN_FULLNAME, player.fullname)
            put(COLUMN_NICKNAME, player.nickname)
            put(COLUMN_GAMES, player.games)
            put(COLUMN_DESCRIPTION, player.description)
        }

        //ищем элемент с нужным id и обновляем у него данные
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(player.id.toString()))
    }

    fun searchPlayer(team_id: Int, nickname: String): ArrayList<Player> { //ищет в базе игрока по id команды и никнейму, возвращает спсок, так как элементов может быть несколько
        val players = arrayListOf<Player>()
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TEAM_ID = ? AND $COLUMN_NICKNAME = ?", arrayOf(team_id.toString(), nickname))
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

            players.add(player)
            cursor.moveToNext()
        }

        return players
    }

    fun showDB() {
        //функция показа содержимого бд в logcat, в целом нужна только на время разработки, поэтому можешь удалить, если хочешь
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

    fun cleanDB() { //очищает бд, нужна была в процессе разработки, тоже можешь удалить
        db.rawQuery("DELETE FROM $TABLE_NAME", null)
    }
}