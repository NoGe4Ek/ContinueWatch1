package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    var active: Boolean = true
    private lateinit var sharedPref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (active) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "${secondsElapsed++}"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.timer)
        sharedPref = getSharedPreferences(SECONDS, Context.MODE_PRIVATE)
        backgroundThread.start()
    }

    override fun onStart() {
        Log.d("MainActivity", "onStart()")
        active = true
        secondsElapsed = sharedPref.getInt(SECONDS, 0)
        Log.d("MainActivity", "Seconds = $secondsElapsed")
        Log.d("MainActivity", "active == true")
        super.onStart()
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop()")
        active = false
        val editor = sharedPref.edit()
        editor.putInt(SECONDS, secondsElapsed)
        editor.apply()
        Log.d("MainActivity", "Seconds = $secondsElapsed")
        Log.d("MainActivity", "active == false")
        super.onStop()
    }

    override fun onResume() {
        Log.d("MainActivity", "onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause()")
        super.onPause()
    }

    companion object { const val SECONDS = "Seconds" }
}
