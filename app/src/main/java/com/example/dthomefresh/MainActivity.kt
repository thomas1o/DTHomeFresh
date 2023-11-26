package com.example.dthomefresh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.dthomefresh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = supportFragmentManager.findFragmentById(R.id.navHostFragment)?.findNavController()
        drawerLayout = binding.drawerLayout

        binding.profileItem.setOnClickListener {
            closeDrawer()

            //TODO- navigate to profile if logged in
            navController?.navigate(R.id.action_categoriesFragment_to_loginFragment)
        }

        binding.contactUsItem.setOnClickListener {
            closeDrawer()
            navController?.navigate(R.id.action_categoriesFragment_to_contactUsFragment)
        }

    }
    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}