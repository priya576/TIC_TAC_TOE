package com.example.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyvalue : String = "null"
class OnlineCodeGeneratorActivity : AppCompatActivity() {
    lateinit var headTV: TextView
    lateinit var codeEdt: EditText
    lateinit var createCodeBtn: Button
    lateinit var joinCodeBtn: Button
    lateinit var loadingPB: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_code_generator)

        headTV = findViewById(R.id.textView6)
        codeEdt = findViewById(R.id.editTextText)
        createCodeBtn = findViewById(R.id.create)
        joinCodeBtn = findViewById(R.id.join)
        loadingPB = findViewById(R.id.progressBar)

        createCodeBtn.setOnClickListener {
            code = "null"
            codeFound = false
            checkTemp = true
            keyvalue = "null"
            code = codeEdt.text.toString()
            createCodeBtn.visibility = View.GONE
            joinCodeBtn.visibility = View.GONE
            headTV.visibility = View.GONE
            codeEdt.visibility = View.GONE
            loadingPB.visibility = View.VISIBLE      //1
            if (code!="null" && code!="") {
                isCodeMaker = true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check = isValueAvaliable(snapshot, code)
                        android.os.Handler().postDelayed({
                            if (check == true) {
                                createCodeBtn.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE
                                Toast.makeText(this@OnlineCodeGeneratorActivity,"Enter a new Code",Toast.LENGTH_SHORT).show()  //3
                            } else {
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code) //2
                                isValueAvaliable(snapshot,code)
                                checkTemp = false
                                android.os.Handler().postDelayed({
                                    accepted()
                                    Toast.makeText(this@OnlineCodeGeneratorActivity,"Please Dont Go Back",Toast.LENGTH_SHORT).show()
                                },300)
                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            } else {
                createCodeBtn.visibility = View.VISIBLE
                joinCodeBtn.visibility = View.VISIBLE
                codeEdt.visibility = View.VISIBLE
                headTV.visibility = View.VISIBLE
                loadingPB.visibility = View.GONE
                Toast.makeText(this,"Please Enter a Valid Code",Toast.LENGTH_SHORT).show()
            }
        }

        joinCodeBtn.setOnClickListener {
            code = "null"
            codeFound = false
            checkTemp = true
            keyvalue = "null"
            code = codeEdt.text.toString()
            if (code!="null" && code!="") {
                createCodeBtn.visibility = View.GONE
                joinCodeBtn.visibility = View.GONE
                codeEdt.visibility = View.GONE
                headTV.visibility = View.GONE
                loadingPB.visibility = View.VISIBLE
                isCodeMaker = false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data : Boolean = isValueAvaliable(snapshot, code)
                        android.os.Handler().postDelayed({
                            if (data == true) {
                                codeFound = true
                                accepted()
                                createCodeBtn.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE
                            } else {
                                createCodeBtn.visibility = View.VISIBLE
                                joinCodeBtn.visibility = View.VISIBLE
                                codeEdt.visibility = View.VISIBLE
                                headTV.visibility = View.VISIBLE
                                loadingPB.visibility = View.GONE
                                Toast.makeText(this@OnlineCodeGeneratorActivity,"Invalid Code",Toast.LENGTH_SHORT).show()
                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            } else {
                Toast.makeText(this,"Please Enter a Valid Code",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun accepted() {
        startActivity(Intent(this,OnlineMultiPlayerGameActivity::class.java))
        createCodeBtn.visibility = View.VISIBLE
        joinCodeBtn.visibility = View.VISIBLE
        codeEdt.visibility = View.VISIBLE
        headTV.visibility = View.VISIBLE
        loadingPB.visibility = View.GONE
    }

    fun isValueAvaliable(snapshot: DataSnapshot,code : String): Boolean {
        var data = snapshot.children
        data.forEach{
            var value = it.getValue().toString()
            if (value == code) {
                keyvalue = it.key.toString()
                return true
            }
        }
        return false
    }
}