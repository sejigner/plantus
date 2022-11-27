package com.example.plantus

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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

@IgnoreExtraProperties
data class Tip(val tip: String? = null) {}
data class Reaction(val reaction: String? = null) {}
data class Message(val message: String? = null) {}
data class Help(val help: String? = null) {}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvMessage = findViewById<TextView>(R.id.tv_message)
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        val fadeIn = AnimationUtils.loadAnimation(this,R.anim.fadein)
        val database = Firebase.database("https://plantus-d6eea-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val messageRef = database.getReference("message")
        val reactionRef = database.getReference("reaction")
        val tipRef = database.getReference("tip")
        val helpRef = database.getReference("help")

        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("message", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<Message>()
                if(data!=null) {
                    val message = data.message
                    if(message!!.isNotEmpty()) {
                        tvMessage.visibility = View.VISIBLE
                        tvMessage.text = message.toString()
                        messageFadeIn(tvMessage, fadeIn)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }

        val helpListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue<Help>()
                if(data!=null) {
                    val help = data.help
                    if(!help.isNullOrEmpty()) {
                        setStatus(help)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Exception", "loadPost:onCancelled", error.toException())
            }
        }
        val reactionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue<Reaction>()
                if(data!=null) {
                    val reaction = data.reaction
                    if(!reaction.isNullOrEmpty()) {
                        showReaction(reaction)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }

        val tipListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<Tip>()
                if(data!=null) {
                    val tip = data.tip
                    showHint(tip, tvHint)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }
        messageRef.addValueEventListener(messageListener)
        tipRef.addValueEventListener(tipListener)
        reactionRef.addValueEventListener(reactionListener)
        helpRef.addValueEventListener(helpListener)
    }

    private fun messageFadeIn(tv: TextView, anim: Animation) {
        tv.startAnimation(anim)
    }

    private fun setCharacter(status : String) {
        val ivCharacter = findViewById<ImageView>(R.id.iv_character)
        if(status=="default") {
            Glide.with(this).load(R.raw.tornaduck_default).into(ivCharacter)
        } else if(status=="happy") {
            Glide.with(this).load(R.raw.tornaduck_happy).into(ivCharacter)
        } else Glide.with(this).load(R.raw.tornaduck_annoyed).into(ivCharacter)
    }

    private fun setHints(type: String) {
        val tvMessage = findViewById<TextView>(R.id.tv_message)
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        when (type) {
            "sun" -> {
                tvMessage.text = resources.getString(R.string.message_sun)
                tvHint.text = resources.getString(R.string.hint_sun)
                setCharacter("annoyed")
            }
            "water" -> {
                tvMessage.text = resources.getString(R.string.message_water)
                tvHint.text = resources.getString(R.string.hint_water)
                setCharacter("annoyed")
            }
            "wind" -> {
                tvMessage.text = resources.getString(R.string.message_wind)
                tvHint.text = resources.getString(R.string.hint_wind)
                setCharacter("annoyed")
            }
            else -> {
                tvMessage.text = resources.getString(R.string.blank)
                tvHint.text = resources.getString(R.string.blank)
                setCharacter("default")
            }
        }
    }

    private fun showHappiness(message: String?, way: String) {
        setCharacter("happy")
        val tvMessage = findViewById<TextView>(R.id.tv_message)
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        tvMessage.text = message
        tvHint.text = resources.getString(R.string.hint_happiness)
        setStatus(way)
    }

    private fun showDefault() {
        setCharacter("default")
        val tvMessage = findViewById<TextView>(R.id.tv_message)
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        tvMessage.text = "..."
        tvHint.text = resources.getString(R.string.blank)
        setStatus("default")
    }

    private fun showHint(hint: String?, tvHint: TextView) {
        tvHint.text = hint
    }

    private fun setStatus(type: String) {
        val default = 5
        when (type) {
            "sun" -> {
                val pb = findViewById<ProgressBar>(R.id.pb_sunshine)
                pb.incrementProgressBy(default)
            }
            "water" -> {
                val pb = findViewById<ProgressBar>(R.id.pb_water)
                pb.incrementProgressBy(default)
            }
            "wind" -> {
                val pb = findViewById<ProgressBar>(R.id.pb_wind)
                pb.incrementProgressBy(default)
            }
        }
    }

    private fun showReaction(type: String) {
        when (type) {
            "happy" -> {
                setCharacter("happy")
            }
            "annoyed" -> {
                setCharacter("annoyed")
            }
            else -> {
                setCharacter("default")
            }
        }
    }
}