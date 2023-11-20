package com.example.foodxyzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodxyzz.contract.authentication.registerRequest
import com.example.foodxyzz.databinding.ActivityRegisterBinding
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegSuPuA.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        }

        binding.btnRegRegister.setOnClickListener{
            var address = binding.edtRegAlamat.text.toString()
            var name = binding.edtRegNamaLengkap.text.toString()
            var password = binding.edtRegPassword.text.toString()
            var password_confirmation = binding.edtRegConfPassword.text.toString()
            var username = binding.edtRegUsername.text.toString()
            if(username.isNullOrEmpty() || password.isNullOrEmpty() || address.isNullOrEmpty() || name.isNullOrEmpty() || password_confirmation.isNullOrEmpty()){
                Toast.makeText(this@RegisterActivity, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
            if(password_confirmation != password ){
                Toast.makeText(this@RegisterActivity, "Password tidak sama!", Toast.LENGTH_SHORT).show()
            }
            try {
                thread {
                    val status = Connect.register(registerRequest(address, name, password, password_confirmation, username))
                    if(status){
                        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    }else{
                        runOnUiThread{
                            Toast.makeText(this@RegisterActivity, "Terjadi kesalahan!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (ex : Exception){
                Log.d("err-register", ex.toString())
            }
        }
    }
}