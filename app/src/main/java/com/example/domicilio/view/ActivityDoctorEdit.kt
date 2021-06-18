package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.domicilio.R
import com.example.domicilio.control.AddressRepository
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.fragments.DialogFragmentAddress
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_account_config.*
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_appointment.addAddress
import kotlinx.android.synthetic.main.activity_appointment.address
import kotlinx.android.synthetic.main.activity_doctor_edit.*
import kotlinx.android.synthetic.main.activity_register_doctor.*

class ActivityDoctorEdit : AppCompatActivity(), View.OnClickListener {

    private val mDoctorRepository = DoctorRepository()
    private val mUserRepository = UserRepository()
    private val mAddressRepository = AddressRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_edit)

        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        populateSpinner()
        loadData()
    }

    private fun loadData(){
        name.setText(mSecurityPreferences.get("name"))
        val token = mSecurityPreferences.get("token")
        mUserRepository.loadData("Bearer $token", mSecurityPreferences.get("user"),
            object : APIListener<UserModel>{
                override fun onSuccess(result: UserModel) {
                    cell.setText(result.cell)
                }

                override fun onFailure(str: String) {
                    Toast.makeText(this@ActivityDoctorEdit, str, Toast.LENGTH_SHORT).show()
                }

            }
        )
        //mDoctorRepository.loadProfile(token, )
    }

    private fun setListeners(){
        addAddress.setOnClickListener(this)
        editBasic.setOnClickListener(this)
        editProfile.setOnClickListener(this)
    }

    private fun populateSpinner(){
        val Address: MutableList<AddressModel> = arrayListOf()
        val token = mSecurityPreferences.get("token")
        mAddressRepository.listByUser("Bearer $token", mSecurityPreferences.get("user"),object :
            APIListener<ObjectModel> {
            override fun onSuccess(result: ObjectModel) {
                val list = result.dados as ArrayList<LinkedTreeMap<String, AddressModel>>

                list.forEach {
                    Address.add(AddressModel(it["street"].toString(), it["idAddress"].toString().split('.')[0].toInt()))
                }
                val adapterAdress =
                    ArrayAdapter(this@ActivityDoctorEdit, android.R.layout.simple_spinner_dropdown_item, Address)
                address.adapter = adapterAdress
            }

            override fun onFailure(str: String) {
                Toast.makeText(this@ActivityDoctorEdit, str, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(v: View) {
        if(v.id == R.id.addAddress){
            val dialog = DialogFragmentAddress()
            dialog.show(supportFragmentManager, "Endereço")
        }
        if(v.id == R.id.editBasic){
            mUserRepository.editData(
                mSecurityPreferences.get("token"), mSecurityPreferences.get("user"),
                name.text.toString(), cell.text.toString(),
                object : APIListener<MessageModel>{
                    override fun onSuccess(result: MessageModel) {
                        Toast.makeText(this@ActivityDoctorEdit, result.msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(this@ActivityDoctorEdit, str, Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }
        if(v.id == R.id.editProfile){
            mDoctorRepository.addProfile(
                mSecurityPreferences.get("token"),
                description.text.toString(), mSecurityPreferences.get("user"),
                priceConsult.text.toString().toFloat(), object : APIListener<MessageModel>{
                    override fun onSuccess(result: MessageModel) {
                        Toast.makeText(this@ActivityDoctorEdit, result.msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(this@ActivityDoctorEdit, str, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}