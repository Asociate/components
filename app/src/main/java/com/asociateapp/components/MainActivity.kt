package com.asociateapp.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asociateapp.components.progress.OnIndicatorClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnIndicatorClickListener {

    override fun onPositionReselected(position: Int) {
        Toast.makeText(this, "Reselected: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onPositionUnselected(position: Int) {
        Toast.makeText(this, "Unselected: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onNewPositionClick(position: Int) {
        Toast.makeText(this, "NewPositionClick: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_go_next.setOnClickListener {
            asociate_steps_progress.goToNext()
        }

        btn_return_previous.setOnClickListener {
            asociate_steps_progress.returnToPrevious()
        }

        steps_indicator.setIndicatorClickListener(this)
    }
}
