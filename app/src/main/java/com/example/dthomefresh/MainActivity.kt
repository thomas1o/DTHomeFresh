package com.example.dthomefresh

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.dthomefresh.databinding.ActivityMainBinding
import com.example.dthomefresh.ui.CategoriesFragment

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

//            TODO- navigate to profile if logged in
            navController?.navigate(R.id.action_categoriesFragment_to_loginFragment)
        }

        binding.contactUsItem.setOnClickListener {
            closeDrawer()
            navController?.navigate(R.id.action_categoriesFragment_to_contactUsFragment)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val navController = supportFragmentManager.findFragmentById(R.id.navHostFragment)?.findNavController()

            // Check if the current destination is CategoriesFragment
            if (navController?.currentDestination?.id == R.id.categoriesFragment) {
                showExitConfirmationDialog()
            } else {
                super.onBackPressed()
            }
        }
    }

//    OPTIMISE- make a custom dialogue box
    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                finish() //NOTE: Closes the app
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()  // NOTE: Dismisses the dialog
            }
            .show()
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}