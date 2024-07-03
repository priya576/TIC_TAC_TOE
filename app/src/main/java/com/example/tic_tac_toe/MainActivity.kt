package com.example.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

var singleuser = false
class MainActivity : AppCompatActivity() {

    lateinit var singleplayerbtn: Button
    lateinit var multiplayerbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singleplayerbtn = findViewById(R.id.btnSinglePlayer)
        multiplayerbtn = findViewById(R.id.btnMultiplayer)

        singleplayerbtn.setOnClickListener {
            singleuser = true
            startActivity(Intent(this,GamePlayActivity::class.java))
        }

        multiplayerbtn.setOnClickListener {
            singleuser = false
            startActivity(Intent(this,MultiPlayerGameSelectionActivity::class.java))
        }
    }
}