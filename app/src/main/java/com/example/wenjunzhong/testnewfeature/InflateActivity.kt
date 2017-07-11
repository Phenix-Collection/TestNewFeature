package com.example.wenjunzhong.testnewfeature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.wenjunzhong.testnewfeature.views.TestInflatLayout

class InflateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflate)
        val testInflateLayout = findViewById(R.id.test_inflate) as TestInflatLayout

        val button1: Button = findViewById(R.id.test_inflate_button1) as Button
        button1.setOnClickListener { testInflateLayout.initInflate() }

        val button2: Button = findViewById(R.id.test_inflate_button2) as Button
        button2.setOnClickListener { testInflateLayout.changeTextView2() }
    }


}
