package com.example.domicilio.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.AppointmentRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.adapters.Appointment_Adapter
import com.example.domicilio.view.adapters.Solicitation_Adapter

class SolicitationFragment : Fragment() {

    private val mAppointmentRepository = AppointmentRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mAdapter = Solicitation_Adapter()
    private val mAdapterConsult = Appointment_Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_solicitation, container, false)

        mSecurityPreferences = SecurityPreferences(requireContext())
        val recycler = root.findViewById<RecyclerView>(R.id.solicitations)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        val recyclerConsult = root.findViewById<RecyclerView>(R.id.consults)
        recyclerConsult.layoutManager = LinearLayoutManager(context)
        recyclerConsult.adapter = mAdapterConsult

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load(false)
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    private fun load(notify: Boolean = true){
        val token = mSecurityPreferences.get("token")
        val user = mSecurityPreferences.get("user")
        if(user != ""){
            mAppointmentRepository.loadWaitMedic("Bearer $token", user, object : APIListener<ObjectModel>{
                override fun onSuccess(result: ObjectModel) {
                    val appointments: ArrayList<AppointmentModel> = result.dados as ArrayList<AppointmentModel>
                    mAdapter.updateAppointment(appointments)
                    mAdapter.notifyDataSetChanged()
                }

                override fun onFailure(str: String) {
                    if (notify) Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
                }

            })

            mAppointmentRepository.loadConsults("Bearer $token", user, object : APIListener<ObjectModel>{
                override fun onSuccess(result: ObjectModel) {
                    val appointments: ArrayList<AppointmentModel> = result.dados as ArrayList<AppointmentModel>
                    mAdapterConsult.updateAppointment(appointments)
                    mAdapterConsult.notifyDataSetChanged()
                }

                override fun onFailure(str: String) {
                    if (notify) Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}