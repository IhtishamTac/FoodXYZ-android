package com.example.foodxyzz

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.foodxyzz.contract.authentication.loginRequest3
import com.example.foodxyzz.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.eedtLgnPassword)
        val defaultImageDrawable = resources.getDrawable(R.drawable.ic_eyeclosed, null)
        val selectedImageDrawable = resources.getDrawable(R.drawable.ic_mata, null)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonReg.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

        binding.buttonLgn.setOnClickListener{
            val username = binding.edtLgnUsername.text.toString()
            val password = binding.eedtLgnPassword.text.toString()
            if(username.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Username atau password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try{
                thread{
                    val status = Connect.login(loginRequest3(password, username))
                    if(status){
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    }else{
                        runOnUiThread{
                            Toast.makeText(this@MainActivity, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch(ex : Exception){
                Log.d("err-login", ex.toString())
            }

        }
    }
}
