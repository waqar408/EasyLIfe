package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.easylife.easylifes.userside.activities.MainActivity
import com.easylife.easylifes.databinding.ActivitySplashBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.activities.TrainerMainActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var utilities: Utilities
    private var loggedIn =""
    private var userType = ""
    private var profileCompleted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@SplashActivity)
        utilities.setWhiteBars(this@SplashActivity)
        loggedIn = utilities.getString(this@SplashActivity,"loggedin")
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileCompleted = obj.is_profile_complete
            userType  = obj.type
        }
        if(loggedIn == "")
        {
            Handler(Looper.myLooper()!!).postDelayed({
                startActivity(Intent(this@SplashActivity, GetStartedActivity::class.java))
                finish()
            },3000)
        }else{
            if (userType == "1")
            {
                if (profileCompleted)
                {
                    Handler(Looper.myLooper()!!).postDelayed({
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    },3000)
                }else{
                    Handler(Looper.myLooper()!!).postDelayed({
                        startActivity(Intent(this@SplashActivity, GenderSelectionActivity::class.java))
                        finish()
                    },3000)
                }
            }else{
                Handler(Looper.myLooper()!!).postDelayed({
                    startActivity(Intent(this@SplashActivity, TrainerMainActivity::class.java))
                    finish()
                },3000)
            }


        }


    }
}