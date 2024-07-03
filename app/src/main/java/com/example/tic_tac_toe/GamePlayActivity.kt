package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.nio.file.attribute.AttributeView
import java.util.logging.Handler
import kotlin.system.exitProcess

var playerturn = true
class GamePlayActivity : AppCompatActivity() {
    lateinit var player1Tv: TextView
    lateinit var player2Tv: TextView
    lateinit var box1btn: Button
    lateinit var box2btn: Button
    lateinit var box3btn: Button
    lateinit var box4btn: Button
    lateinit var box5btn: Button
    lateinit var box6btn: Button
    lateinit var box7btn: Button
    lateinit var box8btn: Button
    lateinit var box9btn: Button
    lateinit var resetbtn: Button
    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptycells = ArrayList<Int>()
    var activeuser = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)
        player1Tv = findViewById(R.id.textView4)
        player2Tv = findViewById(R.id.textView5)
        box1btn = findViewById(R.id.button1)
        box2btn = findViewById(R.id.button2)
        box3btn = findViewById(R.id.button3)
        box4btn = findViewById(R.id.button4)
        box5btn = findViewById(R.id.button5)
        box6btn = findViewById(R.id.button6)
        box7btn = findViewById(R.id.button7)
        box8btn = findViewById(R.id.button8)
        box9btn = findViewById(R.id.button9)
        resetbtn = findViewById(R.id.button10)

        resetbtn.setOnClickListener {
            reset()
        }

    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptycells.clear()
        activeuser = 1
        for(i in 1..9)
        {
            var buttonselected : Button?
             buttonselected = when(i){
                1 -> box1btn
                2 -> box2btn
                3 -> box3btn
                4 -> box4btn
                5 -> box5btn
                6 -> box6btn
                7 -> box7btn
                8 -> box8btn
                9 -> box9btn
                else -> {box1btn}
            }
            buttonselected.isEnabled = true
            buttonselected.text = ""
            player1Tv.text = "Player 1 : $player1count"
            player2Tv.text = "Player 2 : $player2count"
        }
    }

    fun buttonClick(view: View) {

        if (playerturn) {
            val but = view as Button
            var cellID = 0
            when (but.id) {
                R.id.button1 -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9
            }
            playerturn = false;
            android.os.Handler().postDelayed(Runnable { playerturn = true }, 600)
            playnow(but,cellID)
        }
    }

        @SuppressLint("SuspiciousIndentation")
        private fun playnow(buttonSelected:Button, currCell:Int) {
              val audio = MediaPlayer.create(this , R.raw.ms)
                if(activeuser == 1) {
                    buttonSelected.text = "X"
                    buttonSelected.setTextColor(Color.parseColor("#010310"))
                    player1.add(currCell)
                    emptycells.add(currCell)
                    audio.start()
                    buttonSelected.isEnabled = false
                    android.os.Handler().postDelayed(Runnable { audio.release() }, 200)
                    val checkWinner = checkwinner()
                    if (checkWinner == 1) {
                        android.os.Handler().postDelayed(Runnable { reset() }, 2000)
                    } else if (singleuser) {
                        android.os.Handler().postDelayed(Runnable { robot() }, 500)
                    } else {
                        activeuser = 2
                    }
                }else {
                        buttonSelected.text = "O"
                        audio.start()
                        buttonSelected.setTextColor(Color.parseColor("#FFFFFF"))
                        activeuser = 1
                        player2.add(currCell)
                        emptycells.add(currCell)
                        android.os.Handler().postDelayed(Runnable { audio.release() } , 200)
                        buttonSelected.isEnabled = false
                        val checkWinner  = checkwinner()
                        if(checkWinner == 1) {
                            android.os.Handler().postDelayed(Runnable { reset() }, 4000)
                        }
                }
        }



    private fun robot() {
        val rnd = (1..9).random()
        if(emptycells.contains(rnd)) {
            robot()
        }else {
            var buttonselected : Button?
            buttonselected = when(rnd) {
                1 -> box1btn
                2 -> box2btn
                3 -> box3btn
                4 -> box4btn
                5 -> box5btn
                6 -> box6btn
                7 -> box7btn
                8 -> box8btn
                9 -> box9btn
                else -> {box1btn}
            }
//            emptycells.add(rnd);
            // move audio
            val audio = MediaPlayer.create(this , R.raw.ms)
            buttonselected.text = "O"
            audio.start()
            buttonselected.setTextColor(Color.parseColor("#FFFFFF"))
            emptycells.add(rnd);
            player2.add(rnd)
            android.os.Handler().postDelayed(Runnable { audio.release() } , 200)
//            buttonselected.setTextColor(Color.parseColor("#EAE2E24"))
//            player2.add(rnd)
           buttonselected.isEnabled = false
            val checkWinner = checkwinner()
            if(checkWinner == 1) {
                android.os.Handler().postDelayed(Runnable { reset() }, 2000)
            }
        }
    }

    private fun checkwinner() : Int {
        val audio = MediaPlayer.create(this,R.raw.ps)
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8))) {
            player1count+=1
            buttonDisable()
            audio.start()
            disableReset()
            android.os.Handler().postDelayed(Runnable { audio.release() },4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialog, which->
                audio.release()
                exitProcess(1)
            }
            android.os.Handler().postDelayed(Runnable { build.show() },200)
            return 1
        }
        else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8))) {
            player2count+=1
            audio.start()
            buttonDisable()
            disableReset()
            android.os.Handler().postDelayed(Runnable { audio.release() },4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialog, which->
                audio.release()
                exitProcess(1)
            }
            android.os.Handler().postDelayed(Runnable { build.show() },200)
            return 1
        }
        else if (emptycells.contains(1) && emptycells.contains(2) && emptycells.contains(3) && emptycells.contains(4)
            && emptycells.contains(5) && emptycells.contains(6) && emptycells.contains(7) && emptycells.contains(8)
            && emptycells.contains(9)) {
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Game draw\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which->
                exitProcess(1)
            }
            build.show()
            return 1
        }
        return 0
    }

    private fun buttonDisable() {
        player1.clear()
        player2.clear()
        emptycells.clear()
        activeuser = 1
        for(i in 1..9) {
            var buttonselected: Button?
            buttonselected = when (i) {
                1 -> box1btn
                2 -> box2btn
                3 -> box3btn
                4 -> box4btn
                5 -> box5btn
                6 -> box6btn
                7 -> box7btn
                8 -> box8btn
                9 -> box9btn
                else -> {
                    box1btn
                }
            }
            buttonselected.isEnabled = true
            buttonselected.text = ""
            player1Tv.text = "Player 1 : $player1count"
            player2Tv.text = "Player 2 : $player2count"
        }
    }

    private fun disableReset() {
        resetbtn.isEnabled = false
        android.os.Handler().postDelayed(Runnable { resetbtn.isEnabled = true },2200)
    }

}
