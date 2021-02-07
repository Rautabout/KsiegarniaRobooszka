package com.example.ksiegarniarobooszka.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.Fragments.BasketFragment
import com.example.ksiegarniarobooszka.View.Fragments.HomeFragment
import com.example.ksiegarniarobooszka.View.Fragments.SearchFragment
import com.example.ksiegarniarobooszka.View.Fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

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
                R.id.nav_user -> selectedFragment = UserFragment()

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