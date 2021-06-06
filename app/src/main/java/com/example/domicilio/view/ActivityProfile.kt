package com.example.domicilio.view

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.control.AppointmentRepository
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ProfileModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_profile.*

class ActivityProfile : AppCompatActivity(), View.OnClickListener {

    private val mDoctorRepository = DoctorRepository()
    private val mAppointmentRepository = AppointmentRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var idProfile = 0
    private var idMedic = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Perfil do profissional"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSecurityPreferences = SecurityPreferences(this)

        loadDataFromActivity()
        setListeners()
    }

    private fun setListeners() {
        setAppointment.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            idProfile = bundle.getInt("idProfile")
            mDoctorRepository.loadProfile(
                mSecurityPreferences.get("token"), idProfile,
                object : APIListener<ProfileModel> {
                    override fun onSuccess(result: ProfileModel) {
                        idMedic = result.idDoctor
                        doctor_name.text = result.name
                        doctor_function.text = result.funcao
                        description.text = result.description
                        price.text = "R$ ${result.price}"
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(baseContext, str, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    fun edit() {

    }

    fun save() {

    }

    fun addPicture() {

    }

    fun attEvalue() {

    }

    override fun onClick(v: View) {
        if (v.id == R.id.setAppointment) {
            val bundle = intent.extras
            val dateHour = bundle?.getString("dateHour")
            val description = bundle?.getString("description")
            val idAddress = bundle?.getInt("idAddress")
            val token = mSecurityPreferences.get("token")
            mAppointmentRepository.create(
                "Bearer $token", mSecurityPreferences.get("user"),
                description.toString(), dateHour.toString(), idMedic.toString(), 0,
                idAddress.toString().toInt(), object : APIListener<MessageModel> {
                    override fun onSuccess(result: MessageModel) {
                        finishActivity(1)
                        finish()
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(this@ActivityProfile, str, Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }
    }
}