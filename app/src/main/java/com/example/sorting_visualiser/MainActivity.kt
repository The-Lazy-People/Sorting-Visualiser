package com.example.sorting_visualiser

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    //array to store all button 2d array
    val button: MutableList<MutableList<Button>> = ArrayList()
    //size of grid
    var size = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //seekbar added
        arraySizeSeekBar.setOnSeekBarChangeListener(this)


        //grid created for first time
        createButtonGrid(size)
        //button[0][0].setBackgroundColor(Color.parseColor("#FF0000"))



        //randomize button listener
        randamizebtn.setOnClickListener {
            paintButtonWhiteAgain(size)
            randamize(size)

        }
    }
    //make all the buttons white color
    private fun paintButtonWhiteAgain(size: Int) {
        for (i in 0..size){
            for (j in 0..size){
                button[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
    // fill the array with random numbers
    private fun randamize(size: Int) {
        for(col in 0..size)
        {
            val row = (0..size).random()
            colorButton(col,row)
        }
    }
    // color a coloumn of grid till a specific row
    private fun colorButton(col: Int, row: Int) {
        for (i in 0..row){
            button[col][i].setBackgroundColor(Color.parseColor("#FF0000"))
        }
    }
    // create grid of size - parameter size
    private fun createButtonGrid(size: Int) {
        // xml declared LIinear layout
        var screenid = resources.getIdentifier("screen", "id", packageName)
        val screen=findViewById<LinearLayout>(screenid)


        // new dynamically declared linear layout inside screen linearlayout so grid can be deleted at any time
        val mainscreen = LinearLayout(this)
        mainscreen.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        mainscreen.orientation = LinearLayout.HORIZONTAL
        var mainscreenid = resources.getIdentifier("mainscreen", "id", packageName)
        mainscreen.id=mainscreenid
        screen.addView(mainscreen)

        for (i in 0..size) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.VERTICAL
            arrayLinearLayout.setPadding(2,2,2,2)

            val buttoncol: MutableList<Button> = ArrayList()
            for (j in 0..size) {
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                buttoncol.add(button)
                arrayLinearLayout.addView(button)
            }

            button.add(buttoncol)

            mainscreen.addView(arrayLinearLayout)
        }

        paintButtonWhiteAgain(size)

    }
    //delete the dynamically created mainscreen linearlayout and clear the button array
    private fun deleteMainScreen() {
        var mainscreenid = resources.getIdentifier("mainscreen", "id", packageName)
        val mainscreen=findViewById<LinearLayout>(mainscreenid)
        (mainscreen.getParent() as ViewGroup).removeView(mainscreen)
        button.removeAll(button)
    }
    //seekbar function
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        size=(progress/4)+5
        deleteMainScreen()
        createButtonGrid(size)
        randamize(size)
    }
    //seekbar function
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        paintButtonWhiteAgain(size)
    }
    //seekbar function
    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


}
