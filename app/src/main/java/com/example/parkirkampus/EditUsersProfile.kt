package com.example.parkirkampus

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parkirkampus.api.ApiConfig
import com.example.parkirkampus.databinding.ActivityEditUsersProfileBinding
import com.example.parkirkampus.request.EditUsersModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUsersProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditUsersProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUsersProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val phone = intent.getStringExtra("phone")

        binding.editName.setText(name)
        binding.editEmail.setText(email)
        binding.editPassword.setText(password)
        binding.editPhone.setText(phone)


        binding.btnProceed.setOnClickListener {
            val newName =  binding.editName.text.toString()
            val newEmail = binding.editEmail.text.toString()
            val newPassword = binding.editPassword.text.toString()
            val newPhone = binding.editPhone.text.toString()

            val editModel = EditUsersModel(newName,newEmail,newPassword,newPhone)

            ApiConfig.getService().getEdit(userId, editModel).enqueue(object : Callback<EditUsersModel>{
                override fun onResponse(
                    call: Call<EditUsersModel>,
                    response: Response<EditUsersModel>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@EditUsersProfile, "Berhasil Update Profile", Toast.LENGTH_LONG).show()
                        Log.d("Update Profile", "Status: ${response.body()}")

                    }else{

                    }
                }

                override fun onFailure(call: Call<EditUsersModel>, t: Throwable) {
                    Toast.makeText(this@EditUsersProfile, "Gagal mengirim permintaan: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Update Profile", "Gagal mengirim permintaan: ${t.message}")
                }

            })
        }

    }


}