package com.example.domicilio.view

import TermsDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.domicilio.R
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.fragments.*
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mUserRepository : UserRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)

        val vpAdapter = VPAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        val viewPager : ViewPager = findViewById(R.id.viewpager)

        mSecurityPreferences = SecurityPreferences(this)
        vpAdapter.addFragment(PendingFragment(), "EM ESPERA")
        vpAdapter.addFragment(InProgressFragment(), "AGENDADAS")
        vpAdapter.addFragment(CompletedFragment(), "REALIZADAS")
        if(mSecurityPreferences.get("typeUser") == "Médico") vpAdapter.addFragment(SolicitationFragment(), "MÉDICO")

        viewPager.adapter = vpAdapter
        tab_layout.setupWithViewPager(viewPager)

        //Inicializando variaveis
        loadUser(mSecurityPreferences.get("token"))

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, AppointmentActivity::class.java))
        }
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }


    private fun loadUser(token: String){
        mUserRepository.loadSession(token, object : APIListener<UserModel> {
            override fun onSuccess(model: UserModel) {
                mSecurityPreferences.store("user",model.user)
                mSecurityPreferences.store("typeUser",model.typeUser)
                mSecurityPreferences.store("email",model.email)
                mSecurityPreferences.store("name",model.name)
                nav_view.name.text = model.user
                nav_view.email.text = model.email
                if(mSecurityPreferences.get("auth").toInt() != 1){
                    openDialog()
                }
            }

            override fun onFailure(str: String) {
                if(str == "Falha na autenticação"){
                    mSecurityPreferences.remove("token")
                    mSecurityPreferences.remove("user")
                    mSecurityPreferences.remove("typeUser")
                    mSecurityPreferences.remove("email")
                    mSecurityPreferences.remove("name")
                    mSecurityPreferences.remove("auth")
                    startActivity(Intent(this@MainActivity, ActivityLogin::class.java))
                    finish()
                }else{
                    Toast.makeText(this@MainActivity,str,Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.account_settings){
            if(mSecurityPreferences.get("typeUser") == "Médico"){
                startActivity(Intent(this, ActivityDoctorEdit::class.java))
            }else{
                startActivity(Intent(this, ActivityAccountConfig::class.java))
            }
        }
        if (item.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            mSecurityPreferences.remove("token")
            mSecurityPreferences.remove("user")
            mSecurityPreferences.remove("typeUser")
            mSecurityPreferences.remove("email")
            mSecurityPreferences.remove("name")
            mSecurityPreferences.remove("auth")
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
        if(item.itemId == android.R.id.home){
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.nav_chat){
            startActivity(Intent(this, ChatMainActivity::class.java))
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(Gravity.LEFT)
        }
        if(item.itemId == R.id.nav_appointments){
            startActivity(Intent(this, AppointmentActivity::class.java ))
        }
        return true
    }

    private fun openDialog(){
        var termsDialog = TermsDialog()
        termsDialog.show(supportFragmentManager, "Termos")
    }

}