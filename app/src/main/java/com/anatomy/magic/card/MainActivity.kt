package com.anatomy.magic.card

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_ACTIVITY_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.test_menu)

        val bottomNavigationItemView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationItemView.setOnNavigationItemSelectedListener {  item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomepageFragment())
                    true
                }

                R.id.cam -> {
                    openCameraActivity()
                    true
                }

                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigationItemView.selectedItemId = R.id.home
        }

    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    fun openCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_ACTIVITY_REQUEST_CODE) {
            // Set item navigasi bawah menjadi home
            val bottomNavigationItemView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
            bottomNavigationItemView.selectedItemId = R.id.home
        }
    }

}

