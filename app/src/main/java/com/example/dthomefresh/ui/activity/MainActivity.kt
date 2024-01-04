package com.example.dthomefresh.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.ActivityMainBinding
import com.example.dthomefresh.ui.fragment.CategoriesFragment
import com.example.dthomefresh.ui.fragment.LoginFragment
import com.example.dthomefresh.ui.fragment.ProfileFragment
import com.example.dthomefresh.utils.loggedInCheck
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener {  menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    navController?.navigate(R.id.categoriesFragment)
                    true
                }
                R.id.bottom_profile -> {
                    if (loggedInCheck())
                        navController?.navigate(R.id.profileFragment)
                    else
                        navController?.navigate(R.id.loginFragment)
                    true
                }
                R.id.bottom_all_vendors -> {
//                    replaceFragment(SellerListFragment())
                    Snackbar.make(binding.root, "Seller list pressed", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_menu -> {
                    navController?.navigate(R.id.menuFragment)
                    true
                }
                else -> false
            }
        }

        navController?.navigate(R.id.categoriesFragment)

    }

//    //    OPTIMISE- make a custom dialogue box
//    private fun showExitConfirmationDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Exit Confirmation")
//            .setMessage("Are you sure you want to exit?")
//            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
//                finish() //NOTE: Closes the app
//            }
//            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
//                dialogInterface.dismiss()  // NOTE: Dismisses the dialog
//            }
//            .show()
//    }
}