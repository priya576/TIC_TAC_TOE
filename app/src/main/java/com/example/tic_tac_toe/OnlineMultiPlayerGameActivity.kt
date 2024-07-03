package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.exitProcess

var isMyMove = isCodeMaker
class OnlineMultiPlayerGameActivity : AppCompatActivity() {
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
    lateinit var turnTV: TextView
    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptycells = ArrayList<Int>()
    var activeuser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_multi_player_game)

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
        turnTV = findViewById(R.id.turn)

        resetbtn.setOnClickListener {
            reset()
        }

        FirebaseDatabase.getInstance().reference.child("data").child(code).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data = snapshot.value
                if (isMyMove == true) {
                    isMyMove = false
                    moveonline(data.toString(), isMyMove)
                } else {
                    isMyMove = true
                    moveonline(data.toString(), isMyMove)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                Toast.makeText(this@OnlineMultiPlayerGameActivity,"Game Reset",Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun moveonline(data: String, move: Boolean) {
        val audio = MediaPlayer.create(this, R.raw.ms)

        if (move) {
            var buttonselected: Button?
            buttonselected = when (data.toInt()) {
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
            buttonselected.text = "O"
            turnTV.text = "Turn : You"
            buttonselected.setTextColor(Color.parseColor("#FFFFFF"))
            player2.add(data.toInt())
            emptycells.add(data.toInt())
            audio.start()
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            android.os.Handler().postDelayed(Runnable { audio.release() }, 200)
            buttonselected.isEnabled = false
            checkwinner()
        }
    }

    fun checkwinner(): Int {
        val audio = MediaPlayer.create(this, R.raw.ps)
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8))) {
            player1count+= 1
            buttonDisable()
            audio.start()
            disableReset()
            android.os.Handler().postDelayed(Runnable { audio.release() }, 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("You Have Won!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                audio.release()
                removeCode()
                exitProcess(1)

            }
            android.os.Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1

        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8))) {
            player2count+= 1
            audio.start()
            buttonDisable()
            disableReset()
            android.os.Handler().postDelayed(Runnable { audio.release() }, 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Opponent Have Won!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            android.os.Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1

        } else if (emptycells.contains(1) && emptycells.contains(2) && emptycells.contains(3) && emptycells.contains(4)
            && emptycells.contains(5) && emptycells.contains(6) && emptycells.contains(7) &&
            emptycells.contains(8) && emptycells.contains(9)) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Game Draw" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                audio.release()
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            build.show()
            return 1

        }
        return 0
    }

    fun playNow(buttonselected: Button,curCell:Int) {
        val audio = MediaPlayer.create(this,R.raw.ms)
        buttonselected.text = "X"
        emptycells.remove(curCell)
        turnTV.text = "Turn : Opponent"
        buttonselected.setTextColor(Color.parseColor("#010310"))   //4
        player1.add(curCell)
        emptycells.add(curCell)
        audio.start()
        buttonselected.isEnabled = false
        android.os.Handler().postDelayed(Runnable{audio.release()},200)
        checkwinner()
    }

    fun reset() {
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
            player1Tv.text = "You : $player1count"
            player2Tv.text = "Opponent : $player2count"
            isMyMove = isCodeMaker
            if (isCodeMaker) {
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
            }
        }
    }

    fun buttonDisable() {
//        player1.clear()
//        player2.clear()
//        emptycells.clear()
//        activeuser = 1
        for(i in 1..9) {
            val buttonselected = when (i) {
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
            if (buttonselected.isEnabled == true) {
                buttonselected.isEnabled = false
            }

        }
    }

    fun removeCode() {
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("codes").child(keyvalue).removeValue()
        }
    }

    fun errorMsg(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    fun disableReset() {
        resetbtn.isEnabled = false
        android.os.Handler().postDelayed(Runnable { resetbtn.isEnabled = true }, 2200)
    }

    fun updateDatabase(cellId: Int) {
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellId);
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        removeCode()
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        }
        exitProcess(0)
    }

    fun buttonClick(view: View) {
        if (isMyMove) {
            val but = view as Button
            var cellonline = 0
            when (but.id) {
                R.id.button1 -> cellonline = 1
                R.id.button2 -> cellonline = 2
                R.id.button3 -> cellonline = 3
                R.id.button4 -> cellonline = 4
                R.id.button5 -> cellonline = 5
                R.id.button6 -> cellonline = 6
                R.id.button7 -> cellonline = 7
                R.id.button8 -> cellonline = 8
                R.id.button9 -> cellonline = 9
                else -> {
                    cellonline = 0
                }
            }
            playerturn = false
            android.os.Handler().postDelayed(Runnable { playerturn = true },600)
            playNow(but,cellonline)
            updateDatabase(cellonline)
        }
    }
}
