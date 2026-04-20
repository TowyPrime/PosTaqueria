package com.example.postacos.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.postacos.R
import com.example.postacos.databinding.ActivityMainBinding
import com.example.postacos.ui.fragments.AuthFragment
import com.example.postacos.ui.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerFragment) as NavHostFragment

        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    fun setTitleTlb(title: String){
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerFragment) as NavHostFragment

        val navController = navHostFragment.navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}