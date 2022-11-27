package com.example.plantus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

data class Humidity(val Humidity_value: String? = null) {}
data class Lux(val Lux_value: String? = null) {}
data class Soil(val Soil_value: String? = null) {}
data class Temperature(val Temperature_value: String? = null) {}

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val database = Firebase.database("https://asdsadsa-1d482-default-rtdb.firebaseio.com/")
        val humidityRef = database.getReference("Humidity_value")
        val luxRef = database.getReference("Lux_value")
        val soilRef = database.getReference("Soil_value")
        val temperatureRef = database.getReference("Temperature_value")

        val tvHumidity = findViewById<TextView>(R.id.tv_value_humidity)
        val tvLux = findViewById<TextView>(R.id.tv_value_lux)
        val tvSoil = findViewById<TextView>(R.id.tv_value_soil)
        val tvTemperature = findViewById<TextView>(R.id.tv_value_temperature)

        val humidityListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<String>()
                if (data != null) {
                    tvHumidity.text = data
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }
        val luxListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<String>()
                if (data != null) {
                    tvLux.text = data
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }
        val soilListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<String>()
                if (data != null) {
                    tvSoil.text = data
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }
        val temperatureListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<String>()
                if (data != null) {
                    tvTemperature.text = data
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Exception", "loadPost:onCancelled", databaseError.toException())
            }
        }

        humidityRef.addValueEventListener(humidityListener)
        luxRef.addValueEventListener(luxListener)
        soilRef.addValueEventListener(soilListener)
        temperatureRef.addValueEventListener(temperatureListener)

    }
}