package com.example.simpione

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleGet.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein_frombottom_300))
        subtitleGet.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein_frombottom_800))
        btn_getstarted.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein_frombottom_1600))
        picget.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein_1900))
    }

    fun onClick(v: View?) {
        Toast.makeText(applicationContext,"test", Toast.LENGTH_SHORT).show()
    }
}
