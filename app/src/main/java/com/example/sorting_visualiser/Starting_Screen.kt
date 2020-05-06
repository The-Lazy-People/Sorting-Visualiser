package com.example.sorting_visualiser

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_starting__screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.itangqi.waveloadingview.WaveLoadingView

class Starting_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting__screen)

        waveLoadingView.progressValue=0
        var job=GlobalScope.launch(Dispatchers.Main) {
            for (i in 0..100) {
                waveLoadingView.progressValue = i
                delay(50)
                waveLoadingView.centerTitle = "LOADING "+i+"%"
            }
            delay(1000)
            val intent = Intent(this@Starting_Screen,MainActivity::class.java)
            startActivity(intent)
        }


    }
}
