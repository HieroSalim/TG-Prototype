package com.example.domicilio.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.AddressRepository
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.AppointmentListener
import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.adapters.MedicAdapter
import com.example.domicilio.view.fragments.DialogFragmentAddress
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_register_doctor.*
import kotlinx.android.synthetic.main.activity_register_doctor.typeProfessional
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentActivity : AppCompatActivity(), View.OnClickListener{
    private val mDoctorRepository = DoctorRepository()
    private val mAddressRepository = AddressRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: AppointmentListener
    private val mAdapter = MedicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        supportActionBar?.title = "Nova consulta"

        mSecurityPreferences = SecurityPreferences(this)

        val recycler = findViewById<RecyclerView>(R.id.medicList)
        recycler.layoutManager = LinearLayoutManager(baseContext)
        recycler.adapter = mAdapter

        mListener = object : AppointmentListener {
            override fun onOpenProfile(id: Int) {
                val intent = Intent(baseContext, ActivityProfile::class.java)
                val bundle = Bundle()
                val date = if (date_text.text != "") date_text.text.split('/') else date_text.text
                var dateFormat = if (date != "") (date as List<String>).get(2) + '-'+ (date).get(1) +'-'+(date).get(0) else ""
                val hour = hour_text.text
                val Street: AddressModel = address.selectedItem as AddressModel
                bundle.putInt("idProfile", id)
                bundle.putString("dateHour","$dateFormat $hour")
                bundle.putInt("idAddress", Street.idAddress)
                bundle.putString("description", appointment_description.text.toString())
                intent.putExtras(bundle)
                startActivityForResult(intent, 1)
            }

        }

        setListeners()
        populateSpinner()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1){
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
    }

    private fun setListeners() {
        addAddress.setOnClickListener(this)
        time_picker.setOnClickListener(this)
        date_picker.setOnClickListener(this)
        search_doctor.setOnClickListener(this)
    }

    fun populateSpinner(){
        val typeProfessionals: MutableList<String> = arrayListOf()
        typeProfessionals.add("Clínico Geral")

        val adapterProfessionals =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, typeProfessionals)
        typeProfessional.adapter = adapterProfessionals

        val Address: MutableList<AddressModel> = arrayListOf()
        val token = mSecurityPreferences.get("token")
        mAddressRepository.listByUser("Bearer $token", mSecurityPreferences.get("user"),object : APIListener<ObjectModel>{
            override fun onSuccess(result: ObjectModel) {
                val list = result.dados as ArrayList<LinkedTreeMap<String, AddressModel>>

                list.forEach {
                    Address.add(AddressModel(it["street"].toString(), it["idAddress"].toString().split('.')[0].toInt()))
                }
                val adapterAdress =
                    ArrayAdapter(this@AppointmentActivity, android.R.layout.simple_spinner_dropdown_item, Address)
                address.adapter = adapterAdress
            }

            override fun onFailure(str: String) {
                Toast.makeText(this@AppointmentActivity, str, Toast.LENGTH_SHORT).show()
            }

        })
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
            val date = if (date_text.text != "") date_text.text.split('/') else date_text.text
            var dateFormat = if (date != "") (date as List<String>).get(2) + '-'+ (date).get(1) +'-'+(date).get(0) else ""
            val hour = hour_text.text
            mDoctorRepository.searchDoctorsOn(mSecurityPreferences.get("token"),typeProfessional.selectedItem.toString(), "$dateFormat $hour" ,object :
                APIListener<ObjectModel> {
                override fun onSuccess(result: ObjectModel) {
                    val medicos: ArrayList<DoctorModel> = result.dados as ArrayList<DoctorModel>
                    mAdapter.updateMedics(medicos)
                }

                override fun onFailure(msg: String) {
                    Toast.makeText(this@AppointmentActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })
        }
        if(v.id == R.id.addAddress){
            val dialog = DialogFragmentAddress()
            dialog.show(supportFragmentManager, "Endereço")
        }
    }

}