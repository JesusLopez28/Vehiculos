package com.example.vehiculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ViewDataActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehicleAdapter
    private val vehicles = mutableListOf<Vehicle>()
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        btnRegresar = findViewById(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.vehiclesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VehicleAdapter(vehicles)
        recyclerView.adapter = adapter

        fetchVehicles()
    }

    private fun fetchVehicles() {
        val url = "http://192.168.137.91/api.php?action=getAll"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@ViewDataActivity,
                        "Error al obtener datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseData ->
                    val gson = Gson()
                    val vehiclesList =
                        gson.fromJson(responseData, Array<Vehicle>::class.java).toList()
                    runOnUiThread {
                        vehicles.clear()
                        vehicles.addAll(vehiclesList)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
