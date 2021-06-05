package com.example.domicilio.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_profile.*

class ActivityProfile : AppCompatActivity(), View.OnClickListener {

    private val mDoctorRepository = DoctorRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var idProfile = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Perfil do profissional"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSecurityPreferences = SecurityPreferences(this)

        loadDataFromActivity()
        setListeners()
    }

    private fun setListeners(){
        setAppointment.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadDataFromActivity(){
        val bundle = intent.extras
        if(bundle != null){
            val idProfile = bundle.getInt("idProfile")
            mDoctorRepository.loadProfile(mSecurityPreferences.get("token"), idProfile, object : APIListener<DoctorModel>{
                override fun onSuccess(result: DoctorModel) {
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

    fun edit(){

    }
    fun save(){

    }
    fun addPicture(){

    }
    fun attEvalue(){

    }

    override fun onClick(v: View) {
        if(v.id == R.id.setAppointment){
            Toast.makeText(baseContext, "Criar Agendamento", Toast.LENGTH_LONG).show()
        }
    }
}