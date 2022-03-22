package com.example.smartactivitytracker.fragments

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smartactivitytracker.R

object FragmentManager {
    var currentFrag: Fragment? = null

    fun setFragment(fragment: Fragment, activity: AppCompatActivity) {
        activity.supportFragmentManager.beginTransaction().replace(R.id.place_holder, fragment).commit()
        currentFrag = fragment
    }
}