package eu.tutorials.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
   private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //refer to your button as a variable "btnDatePicker"
        val btnDatePicker :Button = findViewById(R.id.btnDatePicker)
        //make the button do something with the onClicklistener
        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }
        //refereing to the 2 textviews
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
    }
    private fun clickDatePicker(){
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        val dpd =         DatePickerDialog(this,
            //once i use the ondatesetlistener this lambda expression appear with its 4 parameters
            //the "view" which is the first parameter here is never used so we can replaced it by an underscore
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, SelectedDayOfMonth ->
                //the context here is "this" which means the main activity in which i want the toast to appear
                val selectedDate = "$SelectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text=selectedDate
                //make a date format and make it simple
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                //now make your date formated by the pattern you made above
                val theDate = sdf.parse(selectedDate)
                //the following code means only if the date isn't empty do this
                //lw heya msh null let this happen
                //this following code makes our code not crash
                theDate?.let {
                    //this will give us the current time between the selected date and 1970 1 jan
                    val selectedDateInMinutes = theDate.time / 60000
                    // this gave us the time which passed between 1970 and now
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        //to format the number into 55,555
                        val dec = DecimalFormat("#,###")
                        tvAgeInMinutes?.text = dec.format(differenceInMinutes).toString()
                    }

                }

            },
            //you put your variables in the third parameter
            year,
            month,
            day
            //then you show the datePickerDialog with the .show() function
        )
        //set a max date for the calender as you cant be born in the future
        //86400000 millisecnds in the day
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()





    }
}