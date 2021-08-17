package com.example.kursach.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_DESCRIPTION
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_FULLNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_GAMES
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_ID
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_NICKNAME
import com.example.kursach.DBHelpers.DBHelperPlayers.Companion.COLUMN_TEAM_ID
import com.example.kursach.DataClasses.Player
import com.example.kursach.R
import com.example.kursach.Tables.TablePlayers
import com.example.kursach.Tables.TableTeams

class PlayerUpdateActivity : AppCompatActivity() {
    lateinit var fullname: EditText
    lateinit var nickname: EditText
    lateinit var games: EditText
    lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_update)

        //принмаем все даннные переданные через intent, чтобы поля уже были заполнены старыми значениями
        val intent = intent
        val player = Player(
            intent.getIntExtra(COLUMN_ID, 0),
            intent.getIntExtra(COLUMN_TEAM_ID, 0),
            intent.getStringExtra(COLUMN_FULLNAME).toString(),
            intent.getStringExtra(COLUMN_NICKNAME).toString(),
            intent.getStringExtra(COLUMN_GAMES).toString(),
            intent.getStringExtra(COLUMN_DESCRIPTION).toString()
        )

        setUI(player)

        val btnUpdate = findViewById<Button>(R.id.btn_update_player)

        btnUpdate.setOnClickListener {
            updatePlayer(player)
        }
    }

    private fun setUI(player: Player) { //функция которая инициализирует поля ввода и заполняет их старыми значниями
        fullname = findViewById(R.id.et_player_name)
        nickname = findViewById(R.id.et_nickname)
        games = findViewById(R.id.et_games)
        description = findViewById(R.id.et_player_description)

        fullname.setText(player.fullname)
        nickname.setText(player.nickname)
        games.setText(player.games)
        description.setText(player.description)
    }

    private fun updatePlayer(player: Player) {
        val tablePlayers = TablePlayers(this)

        when {
            fullname.text.isNullOrBlank() -> {
                Toast.makeText(this, "Поле имени пусто", Toast.LENGTH_SHORT).show()
            }
            nickname.text.isNullOrBlank() -> {
                Toast.makeText(this, "Поле имени пусто", Toast.LENGTH_SHORT).show()
            }
            games.text.isNullOrBlank() -> {
                Toast.makeText(this, "Поле имени пусто", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val descriptionText = if (description.text.isNullOrBlank()) {
                    ""
                } else {
                    description.text.toString()
                }

                val mPlayer = Player(
                    player.id,
                    player.team_id,
                    fullname.text.toString(),
                    nickname.text.toString(),
                    games.text.toString(),
                    descriptionText
                )

                //считваем данные с полей и обновляем элемент, после чего, возвращаемся в прошлое активти
                tablePlayers.updatePlayer(mPlayer)

                onBackPressed()
            }
        }
    }
}