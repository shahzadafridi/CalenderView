package com.shahzadafridi.sample

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shahzadafridi.calendarview.CalendarView
import com.shahzadafridi.calendarview.CalendarViewDialog
import com.shahzadafridi.calendarview.CalenderViewInterface
import com.shahzadafridi.sample.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dialog: CalendarViewDialog? = null

    var months = arrayListOf<String>().apply {
        add("January")
        add("February")
        add("March")
        add("April")
        add("May")
        add("June")
        add("July")
        add("August")
        add("September")
        add("October")
        add("November")
        add("December")
    }

    var weekDays = arrayListOf<String>().apply {
        add("SUN")
        add("MON")
        add("TUE")
        add("WED")
        add("THU")
        add("FRI")
        add("SAT")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //@isXMLConfiguration true because values setup in XML
        setUpCalendarView(binding.calendarView,true)
    }

    fun showDialogCalendar(view: View) {
        dialog = CalendarViewDialog(this)
        dialog!!.setCancelable(false)
        dialog!!.show()
        setUpCalendarView(dialog!!.getCalendarView(),false)
    }

    private fun setUpCalendarView(calendarView: CalendarView, isXMLConfiguration: Boolean) {
        //Fake Events
        val events = HashSet<Calendar>()
        val c1 = Calendar.getInstance()
        for (i in 15..20){
            c1.set(Calendar.DAY_OF_MONTH,i)
            events.add(Calendar.getInstance().apply {
                this.time = c1.time
            })
        }

        /*
         * Setup CalendarView.
         * @isXMLConfiguration true If values set in xml file
         * @isXMLConfiguration false If values set through kotlin code.
         */

        if (isXMLConfiguration) {
            calendarView.builder()
                .withEvents(
                    events = events,
                    eventDotColor = R.color.green
                )
                .buildCalendar()
        }else{
            calendarView.builder()
                .withYearPanel(
                    dateFormat = "yyyy",
                    textColor = R.color.greyed_out,
                    textSize = 42f,
                    font = R.font.titillium_web_semibold
                )
                .withBackButton(
                    isShow = true,
                    background = R.drawable.ic_up_round
                )
                .withMonthPanel(
                    font = R.font.titillium_web_semibold,
                    textSize = 20f,
                    selectedTextColor = R.color.black,
                    unSelectedTextColor = R.color.greyed_out,
                    background = R.color.white,
                    months = months
                )
                .withWeekPanel(
                    font = R.font.titillium_web_semibold,
                    textColor = R.color.black,
                    textSize = 14f,
                    background = R.color.white,
                    weekDays = weekDays
                )
                .withDayPanel(
                    font = R.font.titillium_web_semibold,
                    textColor = R.color.black,
                    textSize = 16f,
                    selectedTextColor = R.color.white,
                    selectedBackground = R.drawable.ic_green_oval,
                    background = R.color.white
                )
                .withCalenderViewBg(
                    background = R.drawable.rect_lr_wround_bg
                )

                .withEvents(
                    events = events,
                    eventDotColor = R.color.green
                )
                .buildCalendar()
        }

        //CalendarView event handler
        calendarView.setEventHandler(object : CalenderViewInterface.EventHandler {

            override fun onDayClick(view: View?, date: Date, position: Int) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onDayClick")
            }

            override fun onDayLongClick(view: View?, date: Date, position: Int) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onDayLongClick")
            }

            override fun onBackClick(view: View?) {
                Log.e("TEST", "onBackClick")
                dialog?.dismiss()
            }

            override fun onMonthClick(view: View?, month: String, position: Int) {
                Toast.makeText(this@MainActivity, month, Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onMonthClick")
            }
        })
    }

}