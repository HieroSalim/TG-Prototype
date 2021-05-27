package com.example.domicilio.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.domicilio.R
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_register_doctor.*
import kotlinx.android.synthetic.main.activity_register_doctor.typeProfessional
import java.text.SimpleDateFormat
import java.util.*

class AppointmentActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        supportActionBar?.title = "Nova consulta"
        setListeners()
    }

    private fun setListeners() {
        time_picker.setOnClickListener(this)
        date_picker.setOnClickListener(this)
    }

    fun populateSpinner(){
        val list: MutableList<String> = arrayListOf()
        list.add("CRM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        typeProfessional.adapter = adapter
    }

    override fun onClick(v: View) {
        val cal  = Calendar.getInstance()
        if(v.id == R.id.time_picker){
            val timeSetListener = TimePickerDialog.OnTimeSetListener{view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                hour_text.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        if(v.id == R.id.date_picker){
            val dateSetListener = DatePickerDialog.OnDateSetListener{view: DatePicker?, year: Int, month: Int, day: Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                date_text.text = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

}