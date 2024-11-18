package com.example.vehiculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val brandEditText = findViewById<EditText>(R.id.brandEditText)
        val modelEditText = findViewById<EditText>(R.id.modelEditText)
        val yearEditText = findViewById<EditText>(R.id.yearEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val viewDataButton = findViewById<Button>(R.id.viewDataButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        saveButton.setOnClickListener {
            val brand = brandEditText.text.toString()
            val model = modelEditText.text.toString()
            val year = yearEditText.text.toString().toInt()
            val price = priceEditText.text.toString().toDouble()
            val description = descriptionEditText.text.toString()

            if (brand.isEmpty() || model.isEmpty() || year == 0 || price == 0.0 || description.isEmpty()) {
                Toast.makeText(
                    this@FormActivity, "Por favor ingrese todos los datos",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            val url = "http://192.168.3.8/api.php?action=insert"
            val requestBody = FormBody.Builder()
                .add("marca", brand)
                .add("modelo", model)
                .add("año", year.toString())
                .add("precio", price.toString())
                .add("descripcion", description)
                .build()

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@FormActivity, "Error al guardar",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        e.printStackTrace()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        Toast.makeText(
                            this@FormActivity, "Vehículo guardado",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }

        clearButton.setOnClickListener {
            brandEditText.text.clear()
            modelEditText.text.clear()
            yearEditText.text.clear()
            priceEditText.text.clear()
            descriptionEditText.text.clear()
        }

        viewDataButton.setOnClickListener {
            val intent = Intent(this, ViewDataActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}
