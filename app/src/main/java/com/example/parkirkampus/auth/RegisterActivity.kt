package com.example.parkirkampus.auth


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityRegisterBinding
import com.example.parkirkampus.request.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegist.setOnClickListener {
            val name  = binding.edtNameRegist.text.toString()
            val email = binding.edtEmailRegist.text.toString()
            val password = binding.edtPassRegist.text.toString()
            val confirmPassword = binding.edtCinfirmPassRegist.text.toString()
            val registerModel = RegisterModel(name, email, password, confirmPassword)

            ApiConfig.getService().dataRegister(registerModel).enqueue(object : Callback<RegisterModel> {
                override fun onResponse(
                    call: Call<RegisterModel>,
                    response: Response<RegisterModel>
                ) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                        Log.d("RegisterActivity", "Register response: $registerModel")
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registrasi Gagal. Periksa akun yang akan anda daftar terlebih dahulu", Toast.LENGTH_SHORT).show()
                        Log.e("RegisterActivity", "Register request failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Registrasi Gagal. Periksa jaringan atau hubungi admin", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Register request error: ${t.message}")
                }
            })

        }
    }

}