package com.example.okhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.okhttpdemo.fragments.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            add<MainFragment>(R.id.fragment_container_view)
        }
    }
}