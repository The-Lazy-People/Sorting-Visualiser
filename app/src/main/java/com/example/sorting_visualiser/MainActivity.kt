package com.example.sorting_visualiser

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    //array to store all button 2d array
    val button: MutableList<MutableList<Button>> = ArrayList()
    //size of grid
    var size = 5
    //array to store numbers in array to be sorted
    var arrayToBeSorted:MutableList<Int> = ArrayList()
    //white color
    val whiteColor:String="#FFFFFF"
    //red color
    val redColor:String="#FF0000"
    //green color
    val greenColor:String="#228B22"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Toolbar added
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //seekbar added
        arraySizeSeekBar.setOnSeekBarChangeListener(this)


        //grid created for first time
        createButtonGrid(size)
        sortbtn.text = "Bubble Sort"
        //button[0][0].setBackgroundColor(Color.parseColor("#FF0000"))



        //randomize button listener
        randamizebtn.setOnClickListener {
            paintAllButtonsWhiteAgain(size)
            randamize(size)

        }
        sortbtn.setOnClickListener {
            bubbleSort()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bubble_sort -> { bubbleSort()
                return true }
            R.id.selection_sort -> { selectionSort()
                return true }
            R.id.merge_sort -> { mergeSort()
                return true }
            R.id.insertion_sort -> { insertionSort()
                return true }
            R.id.quick_sort -> { quick_sort()
                return true }
        }
        return super.onOptionsItemSelected(item)

    }



    private fun bubbleSort(){
        sortbtn.text = "Bubble Sort"
        GlobalScope.launch (Dispatchers.Main )
        {
            var swap = true
            while (swap) {
                swap = false
                for (i in 0 until arrayToBeSorted.size - 1) {
                    if (arrayToBeSorted[i] > arrayToBeSorted[i + 1]) {
                        //delay(100)
                        replaceTwoColInGrid(i, i + 1)
                        delay(200)
                        val temp = arrayToBeSorted[i]
                        arrayToBeSorted[i] = arrayToBeSorted[i + 1]
                        arrayToBeSorted[i + 1] = temp
                        swap = true
                    }
                }
            }
        }
    }

    private fun selectionSort(){
        sortbtn.text = "Selection Sort"
    }

    private fun mergeSort(){
        sortbtn.text = "Merge Sort"
    }

    private fun insertionSort(){
        sortbtn.text = "Insertion Sort"
    }

    private fun quick_sort(){
        sortbtn.text = "Quick Sort"
    }

    private fun replaceTwoColInGrid(a: Int, b: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            colorButton(a, arrayToBeSorted[a], redColor)
            colorButton(b, arrayToBeSorted[b], redColor)
            delay(100)
            paintSingleColWhite(a)
            paintSingleColWhite(b)
            colorButton(a, arrayToBeSorted[b], greenColor)
            colorButton(b, arrayToBeSorted[a], greenColor)
        }
    }

    //make all the buttons white color
    private fun paintAllButtonsWhiteAgain(size: Int) {
        for (i in 0..size){
            for (j in 0..size){
                button[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
    // fill the array with random numbers
    private fun randamize(size: Int) {
        arrayToBeSorted.removeAll(arrayToBeSorted)
        for(col in 0..size)
        {
            val row = (0..size).random()
            arrayToBeSorted.add(row)
            colorButton(col,row,greenColor)
        }
    }
    // color a coloumn of grid till a specific row
    private fun colorButton(col: Int, row: Int,color:String) {
        for (i in 0..row){
            button[col][i].setBackgroundColor(Color.parseColor(color))
        }
    }
    // make a single coloumn of grid white
    private fun paintSingleColWhite(col: Int) {
        for (i in 0..size){
            button[col][i].setBackgroundColor(Color.parseColor("#FFFFFF"))
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

        paintAllButtonsWhiteAgain(size)

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
        paintAllButtonsWhiteAgain(size)
    }

    //seekbar function
    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.sorting_name, menu)
        return true
    }


}
