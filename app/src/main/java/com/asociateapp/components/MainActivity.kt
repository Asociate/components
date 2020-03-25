package com.asociateapp.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_go_next.setOnClickListener {
            asociate_steps_progress.goToNextStep()
        }

        btn_return_previous.setOnClickListener {
            asociate_steps_progress.returnToPreviousStep()
        }
    }
}
