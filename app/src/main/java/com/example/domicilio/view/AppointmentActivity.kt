package com.example.domicilio.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.domicilio.R
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListenerDoctor
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_register_doctor.*
import kotlinx.android.synthetic.main.activity_register_doctor.typeProfessional
import java.text.SimpleDateFormat
import java.util.*

class AppointmentActivity : AppCompatActivity(), View.OnClickListener{
    private val mDoctorRepository = DoctorRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        supportActionBar?.title = "Nova consulta"

        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        populateSpinner()
    }

    private fun setListeners() {
        time_picker.setOnClickListener(this)
        date_picker.setOnClickListener(this)
        search_doctor.setOnClickListener(this)
    }

    fun populateSpinner(){
        val typeProfessionals: MutableList<String> = arrayListOf()
        typeProfessionals.add("Clínico Geral")
        typeProfessionals.add("Ortopedista")

        val adapterProfessionals =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, typeProfessionals)
        typeProfessional.adapter = adapterProfessionals
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
        if(v.id == R.id.search_doctor){
            mDoctorRepository.searchDoctorsOn(mSecurityPreferences.get("token"),typeProfessional.selectedItem.toString() ,object : APIListenerDoctor{
                override fun onSuccess(model: DoctorModel) {

                }
                
                override fun onFailure(msg: String) {
                    Toast.makeText(this@AppointmentActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}