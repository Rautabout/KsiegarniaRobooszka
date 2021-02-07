package com.example.ksiegarniarobooszka.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user.*


class MainActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        val fragmentTransaction:FragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, UserFragment())
        fragmentTransaction.commit()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()
        }
    }
    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_mainsearch -> selectedFragment = SearchFragment()
                R.id.nav_basket -> selectedFragment = BasketFragment()
                R.id.nav_user -> {
                    selectedFragment = if(mAuth.currentUser?.email ==null) {
                        LoginFragment()
                    }else{
                        UserFragment()
                    }

                }

            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }
}