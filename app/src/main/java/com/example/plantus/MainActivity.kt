package com.example.plantus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.plantus.dataClass.Dashboard
import com.example.plantus.dataClass.PlantIndex
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivDashboard = findViewById<ImageView>(R.id.iv_dashboard)
        val fadeIn = AnimationUtils.loadAnimation(this,R.anim.fadein)
        val database = Firebase.database("https://asdsadsa-1d482-default-rtdb.firebaseio.com/")
        val dashboardRef = database.getReference("dashboard")
//        val messageRef = database.getReference("character/message")
//        val reactionRef = database.getReference("character/reaction")
//        val tipRef = database.getReference("character/hint")
//        val helpRef = database.getReference("character/help")

        ivDashboard.setOnClickListener {
            startActivity(Intent(this@MainActivity,DashboardActivity::class.java))
        }

        showFeedback("normal")

        val dashboardListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue<Dashboard>()
                Log.d("debugging", data.toString())
                if(data!=null) {
                    val soil = data.Soil_value
                    val temp = data.Temperature_value
                    val lux = data.Lux_value
                    val humi = data.Humidity_value
                    Log.d("soil", "soil: $soil")
                    Log.d("temp", "temp: $temp")
                    Log.d("lux", "lux: $lux")

                    if (lux.toFloat() < 50) {
                        showFeedback("low_lux")
                    } else if (lux.toFloat() in 50.0..69.0) {
                        showFeedback("moderate_lux")
                    } else if (lux.toFloat() >= 70) {
                        showFeedback("high_lux")
                    }
//                    if (soil.toFloat() < 10.0) {
//                        showFeedback("thirsty")
//                    } else if (soil.toFloat() >60.0) {
//                        showFeedback("overwatered")
//                    }
//
//                    if(temp.toFloat() > 27.0) {
//                        showFeedback("high_temp")
//                    }
//                    else if(temp.toFloat() < 18.0) {
//                        showFeedback("low_temp")
//                    }

                }
            }
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//            }
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//            }
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }

//        val messageListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                Log.d("message", "Message received")
//                // Get Post object and use the values to update the UI
//                val data = dataSnapshot.getValue<String>()
//                if(data!=null) {
//                    val message = data
//                    if(message.isNotEmpty()) {
//                        tvMessage.visibility = View.VISIBLE
//                        tvMessage.text = message.toString()
//                        messageFadeIn(tvMessage, fadeIn)
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
//            }
//        }

//        val helpListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.getValue<String>()
//                if(data!=null) {
//                    val help = data
//                    if(help.isNotEmpty()) {
//                        incrementExp()
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("Exception", "loadPost:onCancelled", error.toException())
//            }
//        }
//        val reactionListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val data = dataSnapshot.getValue<String>()
//                if(data!=null) {
//                    val reaction = data
//                    if(reaction.isNotEmpty()) {
//                        showReaction(reaction)
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
//            }
//        }

//        val tipListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                Log.d("need", "Message received")
//                // Get Post object and use the values to update the UI
//                val tip = dataSnapshot.getValue<String>()
//                if(tip!=null) {
//                    showHint(tip, tvHint)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
        dashboardRef.addValueEventListener(dashboardListener)
//        messageRef.addValueEventListener(messageListener)
//        tipRef.addValueEventListener(tipListener)
//        reactionRef.addValueEventListener(reactionListener)
//        helpRef.addValueEventListener(helpListener)
    }

    private fun messageFadeIn(tv: TextView, anim: Animation) {
        tv.startAnimation(anim)
    }

    private fun setCharacter(status : String) {
        val ivCharacter = findViewById<ImageView>(R.id.iv_character)
        val tvMessage = findViewById<TextView>(R.id.tv_message)
        if(status=="default") {
            Glide.with(this).load(R.raw.tornaduck_default).into(ivCharacter)
            tvMessage.visibility = View.INVISIBLE
        } else if(status=="high_lux") {
            Glide.with(this).load(R.raw.tornaduck_happy).into(ivCharacter)
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = "빛이 밝아서 기분이 좋아"
        } else if(status=="low_lux") {
            Glide.with(this).load(R.raw.tornaduck_annoyed).into(ivCharacter)
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = "너무 어두워!"
        }
    }

    private fun showFeedback(status: String) {
        when (status) {
            "low_lux" -> {
                setCharacter("low_lux")
                setHints("너무 어두워요. 자연광이 제일 좋지만 식물등도 괜찮아요")
            }

            "moderate_lux" -> {
                setCharacter("default")
                setHints("식물이 자라나기 쾌적한 환경이에요")
            }

            "high_lux" -> {
                setCharacter("high_lux")
                setHints("식물이 좋아하는 조도에요")
            }
        }
    }

    private fun setHints(text: String) {
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        tvHint.text = text
    }

    private fun incrementExp() {
        val default = 10
        val pb = findViewById<ProgressBar>(R.id.pb_exp)
        pb.incrementProgressBy(default)
    }
}