package com.example.parkirkampus

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityProfileBinding
import com.example.parkirkampus.response.UsersResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var userId: Int = -1
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserId()
        val gson = Gson()

        val updatedUserJson = intent.getStringExtra("updatedUserJson")
        if (updatedUserJson != null) {
            val updatedUser = gson.fromJson(updatedUserJson, UsersResponse::class.java)
            // Gunakan updatedUser sesuai kebutuhan
        }

        val name = intent.getStringExtra("name")
        userId = intent.getIntExtra("id", -1)
        Log.d("ProfileActivity", "Value of userId: $userId")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val phone = intent.getStringExtra("phone")
        val photo = intent.getStringExtra("photo")
        if (!photo.isNullOrEmpty()) {
            Glide.with(this)
                .load(photo)
                .into(binding.imageProfile)

            Log.d("GlideDebug1", "Image URL: $photo")
        }

        binding.txtNameProfile.text = name ?: "User"
        binding.txtEmail.text = email ?: "Email"
        binding.txtPhone.text = phone ?: "Phone"

        binding.edtEditProfile.setOnClickListener {
            ApiConfig.getService().getUsers(userId).enqueue(object : Callback<UsersResponse>{
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    val userResponse = response.body()
                    if (userResponse != null && userResponse.results?.isNotEmpty() == true) {
                        val user = userResponse.results.find { it?.id == userId }
                        if (user != null) {
                            val userId = user.id
                            val userName = user.name ?: "User"
                            val userEmail = user.email ?: "Email"
                            val userPassword = user.password ?: "Password"
                            val userPhone = user.phone ?: "Phone"
                            val intent = Intent(this@ProfileActivity, EditUsersProfile::class.java)
                            intent.putExtra("id", userId)
                            intent.putExtra("name", userName)
                            intent.putExtra("email", userEmail)
                            intent.putExtra("password", userPassword)
                            intent.putExtra("phone", userPhone)
                            startActivity(intent)
                        }
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })


        }



    }
    private fun getUserId(): Int {
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", -1)
    }





}