package com.example.parkirkampus.auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parkirkampus.MainActivity
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityLoginBinding
import com.example.parkirkampus.request.LoginModel
import com.example.parkirkampus.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        binding.edtEmailLogin.setText(savedEmail)
        binding.edtPassLogin.setText(savedPassword)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPassLogin.text.toString()
            loginUser(email, password)
        }

        binding.txtToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginModel = LoginModel(email, password)

        val call = ApiConfig.getService().datalogin(loginModel)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {

                        val user = loginResponse.user
                        val userId = user?.id
                        val userName = user?.name
                        val userEmail = user?.email
                        val userPassword = user?.password
                        val userPhone = user?.phone
                        val userPhoto = user?.photo

                        val editor = sharedPreferences.edit()
                        editor.putInt("user_id", userId ?: -1)
                        editor.apply()


                        if (binding.chRemember.isChecked) {
                            val editor = sharedPreferences.edit()
                            editor.putString("email", loginModel.email)
                            editor.putString("password", loginModel.password)
                            editor.apply()
                        }

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("id", userId)
                        intent.putExtra("user_id",userId)
                        intent.putExtra("name", userName)
                        intent.putExtra("email", userEmail)
                        intent.putExtra("password", userPassword)
                        intent.putExtra("phone", userPhone)
                        intent.putExtra("photo", userPhoto)


                        startActivity(intent)
                        finish()
                        Toast.makeText(this@LoginActivity,"Login Berhasil", Toast.LENGTH_SHORT).show()
                        Log.d("LoginActivity", "Login Response: $loginResponse")
                    } else {
                        Toast.makeText(this@LoginActivity,"Masukkan email atau password anda terlebih dahulu", Toast.LENGTH_SHORT).show()
                        Log.e("LoginActivity", "Login Request Failed: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Login anda gagal periksan email atau password anda", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error in login request: ${t.message}")
            }
        })
    }


}
