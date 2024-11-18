package com.example.vehiculos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val saveLoginCheckBox = findViewById<CheckBox>(R.id.saveLoginCheckBox)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")
        usernameEditText.setText(savedUsername)
        passwordEditText.setText(savedPassword)
        saveLoginCheckBox.isChecked = savedUsername!!.isNotEmpty()

        loginButton.setOnClickListener {
            if (usernameEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show()
            }

            if (saveLoginCheckBox.isChecked) {
                val editor = sharedPreferences.edit()
                editor.putString("username", usernameEditText.text.toString())
                editor.putString("password", passwordEditText.text.toString())
                editor.apply()
            }

            if (usernameEditText.text.toString() != "admin" || passwordEditText.text.toString() != "admin") {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            usernameEditText.text.clear()
            passwordEditText.text.clear()
            saveLoginCheckBox.isChecked = false
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}
