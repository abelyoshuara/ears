package com.example.earsproject.data.uii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.earsproject.HomeFragment
import com.example.earsproject.LocationFragment
import com.example.earsproject.R
import com.example.earsproject.SettingFragment
import com.example.earsproject.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        fragments.add(HomeFragment())
        fragments.add(LocationFragment())
        fragments.add(SettingFragment())

//        val adapter = ViewPageAdapter(this, fragments)
//        viewPager.adapter = adapter
//
//        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//            //anonymous class and object
//            override fun onPageSelected(position: Int) {
//                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
//            }
//        })

//        bottomNav.setOnItemSelectedListener {
//            if(it.itemId == R.id.itemHome){
//                viewPager.currentItem = 0
//            }else if(it.itemId == R.id.itemLocation){
//                viewPager.currentItem = 1
//            } else if(it.itemId == R.id.itemSetting){
//                viewPager.currentItem = 2
//            }
//            true
//        }
    }
}