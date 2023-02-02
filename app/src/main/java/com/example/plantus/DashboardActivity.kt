package com.example.plantus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantus.databinding.ActivityDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


val RestAPIKey = "20221212PWK1VQ3ENERZMKRB9F47G"

data class Humidity(val Humidity_value: String? = null) {}
data class Lux(val Lux_value: String? = null) {}
data class Soil(val Soil_value: String? = null) {}
data class Temperature(val Temperature_value: String? = null) {}


class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingMain = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)


        val database = Firebase.database("https://asdsadsa-1d482-default-rtdb.firebaseio.com/")
        val humidityRef = database.getReference("dashboard/Humidity_value")
        val luxRef = database.getReference("dashboard/Lux_value")
        val soilRef = database.getReference("dashboard/Soil_value")
        val temperatureRef = database.getReference("dashboard/Temperature_value")

        val tvHumidity = bindingMain.tvValueHumidity
        val tvLux = bindingMain.tvValueLux
        val tvSoil = bindingMain.tvValueSoil
        val tvTemperature = bindingMain.tvValueTemperature
        val rvAPIResponse = bindingMain.rvApiResponse

        val humidityListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("need", "Message received")
                // Get Post object and use the values to update the UI
                val data = dataSnapshot.getValue<String>()
                if (data != null) {
                    tvHumidity.text = "$data %"
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
                    tvLux.text = "$data %"
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
                    tvSoil.text = "$data %"
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
                    tvTemperature.text = "$data ºC"
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

        Thread {
            try {
                val plantList = APIResponse().getData()// 하단의 getData 메소드를 통해 데이터를 파싱
                runOnUiThread(Runnable {
                    val adapter = PlantListAdapter(plantList)
                    val layoutManager = LinearLayoutManager(this)
                    rvAPIResponse.layoutManager = layoutManager
                    rvAPIResponse.adapter = adapter
                    rvAPIResponse.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))

                })
            } catch(e: InterruptedException) {
                Log.d("DataFetchingOnThread",e.toString())
            }

        }.start()
    }
}


