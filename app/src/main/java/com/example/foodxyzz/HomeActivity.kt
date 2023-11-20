package com.example.foodxyzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.foodxyzz.ul.HomeFragment
import com.example.foodxyzz.ul.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var frameHome : FrameLayout
    private lateinit var botNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        frameHome = findViewById(R.id.frameHome)
        botNav = findViewById(R.id.botNav)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction()
            .add(frameHome.id, homeFragment)
            .commit()

        botNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(frameHome.id, homeFragment)
                        .commit()
                    true
                }
                R.id.menu_item2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(frameHome.id, profileFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}