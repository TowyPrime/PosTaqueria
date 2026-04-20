package com.example.postacos.ui

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.postacos.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val splashTime: Long = 3000

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setupBinding()
        setupSplash()
    }
    private fun setupBinding(){
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupSplash(){
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            openActivity(MainActivity::class.java)

        },splashTime)
    }

    private fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
        finish()
    }
}