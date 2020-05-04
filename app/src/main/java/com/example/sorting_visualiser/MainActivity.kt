package com.example.sorting_visualiser

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val button: MutableList<MutableList<Button>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val size = 9
        createButtonGrid(size)
        //button[0][0].setBackgroundColor(Color.parseColor("#FF0000"))
        randamizebtn.setOnClickListener {
            paintButtonWhiteAgain(size)
            randamize(size)
        }
    }

    private fun paintButtonWhiteAgain(size: Int) {
        for (i in 0..size){
            for (j in 0..size){
                button[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private fun randamize(size: Int) {
        for(col in 0..size)
        {
            val row = (0..size).random()
            colorButton(col,row)
        }
    }

    private fun colorButton(col: Int, row: Int) {
        for (i in 0..row){
            button[col][i].setBackgroundColor(Color.parseColor("#FF0000"))
        }
    }

    private fun createButtonGrid(size: Int) {

        for (i in 0..size) {
            var llid = "c" + i
            var resID = resources.getIdentifier(llid, "id", packageName)
            var linearLayout = findViewById<LinearLayout>(resID)
            val buttoncol: MutableList<Button> = ArrayList()
            for (j in 0..size) {
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                buttoncol.add(button)
                linearLayout.addView(button)
            }
            button.add(buttoncol)
        }
        paintButtonWhiteAgain(size)
    }
}
