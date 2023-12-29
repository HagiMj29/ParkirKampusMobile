package com.example.parkirkampus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.auth.LoginActivity
import com.example.parkirkampus.databinding.ActivityMainBinding
import com.example.parkirkampus.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var userId: Int = -1
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userId = getUserId()

        binding.imageMenu

        userId = intent.getIntExtra("id", -1)
        Log.d("MainActivity", "Value of userId: $userId")
        val name = intent.getStringExtra("name")
        val photo = intent.getStringExtra("photo")
        if (!photo.isNullOrEmpty()) {

            Glide.with(this)
                .load(photo)
                .into(binding.imageMenu)

            Log.d("GlideDebug", "Image URL: $photo");
        } else {

            Glide.with(this)
                .load(R.drawable.images)
                .into(binding.imageMenu);

            Log.d("GlideDebug", "Using default image");
        }

        binding.txtName.text = name ?: "User"




        binding.parkingSlotsInfoPage.setOnClickListener {
            val intent = Intent(this@MainActivity, ParkingSlotActivity::class.java)
            startActivity(intent)
        }

        binding.attendancesPages.setOnClickListener {
            val intent = Intent(this@MainActivity, AttendancesActivity::class.java)
            startActivity(intent)
        }

        binding.bookingPage.setOnClickListener {
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
                            val userPhoto = user.photo ?: ""
                            val intent = Intent(this@MainActivity, ParkingActivitieActivity::class.java)
                            intent.putExtra("id", userId)
                            intent.putExtra("user_id",userId)
                            intent.putExtra("name", userName)
                            intent.putExtra("email", userEmail)
                            intent.putExtra("password", userPassword)
                            intent.putExtra("phone", userPhone)
                            intent.putExtra("photo", userPhoto)
                            startActivity(intent)
                        }
                    }

                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }

        binding.historyParkPage.setOnClickListener {
            val intent = Intent(this@MainActivity, ParkingHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.complaintPage.setOnClickListener {
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
                            val userPhoto = user.photo ?: ""
                            val intent = Intent(this@MainActivity, ComplaintsActivity::class.java)
                            intent.putExtra("id", userId)
                            intent.putExtra("user_id",userId)
                            intent.putExtra("name", userName)
                            intent.putExtra("email", userEmail)
                            intent.putExtra("password", userPassword)
                            intent.putExtra("phone", userPhone)
                            intent.putExtra("photo", userPhoto)
                            startActivity(intent)
                        }
                    }

                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }

        binding.mapPark.setOnClickListener {
            val googleMapsLink = "https://goo.gl/maps/h4PLLxgN7TZQHcUL6"
            val gmmIntentUri = Uri.parse(googleMapsLink)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(this@MainActivity, "Gagal",Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun getUserId(): Int {
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", -1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navbarmenuitem, menu)
        return  true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuBio ->{
         ApiConfig.getService().getUsers(userId).enqueue(object : Callback<UsersResponse>{
             override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                 val userResponse = response.body()
                 if (userResponse != null && userResponse.results?.isNotEmpty() == true) {
                     val user = userResponse.results.find { it?.id == userId }
                     if (user != null) {
                         val userId = user.id
                         val userName = user.name ?: "User"
                         val userEmail = user.email ?: "Email"
                         val userPassword = user.password ?: "Password"
                         val userPhone = user.phone ?: "Phone"
                         val userPhoto = user.photo ?: ""
                         binding.txtName.text = userName
                             val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                             intent.putExtra("id",userId)
                             intent.putExtra("name", userName)
                             intent.putExtra("email", userEmail)
                            intent.putExtra("password", userPassword)
                             intent.putExtra("phone", userPhone)
                             intent.putExtra("photo", userPhoto)
                             startActivity(intent)
                         }
                     }
             }

             override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                 TODO("Not yet implemented")
             }
         })
            }
            R.id.menuLogout -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Logout berhasil",Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }


}