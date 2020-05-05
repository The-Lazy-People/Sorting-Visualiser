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
    val buttons: MutableList<MutableList<Button>> = ArrayList()
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
    // sort val
    var sortval=0

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
            when(sortval)
            {
                0 -> bubbleSort()
                1 -> selectionSort()
                2 -> mergeSort()
                3 -> insertionSort()
                4 -> quick_sort()

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bubble_sort -> { sortbtn.text="Bubble Sort"
                sortval=0
                return true }
            R.id.selection_sort -> { sortbtn.text="Selection Sort"
                sortval=1
                return true }
            R.id.merge_sort -> { sortbtn.text="Merge Sort"
                sortval=2
                return true }
            R.id.insertion_sort -> { sortbtn.text="Insertion Sort"
                sortval=3
                return true }
            R.id.quick_sort -> { sortbtn.text="Quick Sort"
                sortval=4
                return true }
        }
        return super.onOptionsItemSelected(item)

    }



    private fun bubbleSort(){
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
        GlobalScope.launch (Dispatchers.Main )
        {
            var n = arrayToBeSorted.size
            var temp: Int
            for (i in 0..n - 1) {
                var indexOfMin = i
                for (j in n - 1 downTo i) {
                    if (arrayToBeSorted[j] < arrayToBeSorted[indexOfMin])
                        indexOfMin = j
                }
                if (i != indexOfMin) {
                    replaceTwoColInGrid(i, indexOfMin)
                    delay(200)
                    temp = arrayToBeSorted[i]
                    arrayToBeSorted[i] = arrayToBeSorted[indexOfMin]
                    arrayToBeSorted[indexOfMin] = temp
                }
            }
        }
    }

    private fun mergeSort(){

    }

    private fun insertionSort(){

        for (i in 1..size) {
            // println(items)
            val item = arrayToBeSorted[i]
            var j = i-1
            while (j >= 0 && arrayToBeSorted[j]>item) {
                paintSingleColWhite(j+1)
                colorButton(j+1, arrayToBeSorted[j], greenColor)
                arrayToBeSorted[j+1] = arrayToBeSorted[j]
                j=j-1
            }
            paintSingleColWhite(j+1)
            colorButton(j+1,item,greenColor)
            arrayToBeSorted[j+1] = item
        }
    }

    private fun quick_sort(){

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
                buttons[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"))
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
            buttons[col][i].setBackgroundColor(Color.parseColor(color))
        }
    }
    // make a single coloumn of grid white
    private fun paintSingleColWhite(col: Int) {
        for (i in 0..size){
            buttons[col][i].setBackgroundColor(Color.parseColor("#FFFFFF"))
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

            buttons.add(buttoncol)

            mainscreen.addView(arrayLinearLayout)
        }

        paintAllButtonsWhiteAgain(size)

    }
    //delete the dynamically created mainscreen linearlayout and clear the button array
    private fun deleteMainScreen() {
        var mainscreenid = resources.getIdentifier("mainscreen", "id", packageName)
        val mainscreen=findViewById<LinearLayout>(mainscreenid)
        (mainscreen.getParent() as ViewGroup).removeView(mainscreen)
        buttons.removeAll(buttons)
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
