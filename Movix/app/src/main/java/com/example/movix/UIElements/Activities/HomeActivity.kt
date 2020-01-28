package com.example.movix.UIElements.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.movix.AppLogic.Models.User
import com.example.movix.R
import com.example.movix.UIElements.Fragments.FavoriteFragment
import com.example.movix.UIElements.Fragments.HistoryFragment
import com.example.movix.UIElements.Fragments.MovieFragment
import com.example.movix.UIElements.Fragments.ShowProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_registration.*

class HomeActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navMovie -> {
                    replaceFragment(MovieFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navFavorite -> {
                    replaceFragment(FavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navHistory -> {
                    replaceFragment(HistoryFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navProfile -> {
                    replaceFragment(ShowProfile())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        replaceFragment(MovieFragment())
        navBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navBottom.selectedItemId =R.id.navMovie

    }

    override fun onResume() {
        super.onResume()


    }

    private fun replaceFragment(fragment: Fragment) {

        //get User from extra

        var user = intent.getSerializableExtra("currentUser") as? User
        var bundle = Bundle()
        bundle.putSerializable("USER",user)
        fragment.arguments= bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeActivityContainer, fragment).commit()

    }
}
