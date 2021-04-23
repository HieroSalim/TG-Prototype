package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.domicilio.R
import com.example.domicilio.services.model.UserChatModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.view.ui.chat.ChatFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_main.*
import kotlinx.android.synthetic.main.bar_layout.*
import kotlinx.android.synthetic.main.bar_layout.toolbar

class ChatMainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat_main)
        setSupportActionBar(toolbar)
        var mFirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        var mDatabaseReference: DatabaseReference? =
            mFirebaseUser?.let {
                FirebaseDatabase.getInstance().getReference("Users").child(
                    it.uid
                )
            }

        mDatabaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userChatModel: UserChatModel? = snapshot.getValue(UserChatModel::class.java)
                if (userChatModel != null) {
                    user_name_main.text = userChatModel.username
                }
                if (userChatModel != null) {
                    if (userChatModel.imageURL == "default") {
                        profile_image.setImageResource(R.mipmap.ic_launcher)
                    } else {

                        Glide.with(applicationContext).load(userChatModel.imageURL).into(profile_image)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)

        viewPagerAdapter.addFragment(ChatFragment(),"Chats")

        viewPager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(viewPager)
    }

    override fun onClick(v: View) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return true
        } else {
            return false
        }
    }

    class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        var fragments: ArrayList<Fragment> = ArrayList()
        var titles: ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }

}