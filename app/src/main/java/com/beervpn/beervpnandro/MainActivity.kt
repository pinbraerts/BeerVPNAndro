package com.beervpn.beervpnandro

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout

    fun replaceFragment(comp: BeerCompanion): Boolean {
        drawerLayout.closeDrawers()
        return supportFragmentManager.fragments[0].tag == comp.tag || false.apply {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                            android.R.animator.fade_in,
                            android.R.animator.fade_out
                    )
                    .replace(R.id.frag, comp.instance, comp.tag)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if(replaceFragment(MainFragment)) super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_about -> {
                TODO("replace @id/frag with About fragment")
            }
            R.id.item_feedback ->
                replaceFragment(FeedbackFragment)
            R.id.item_ownvpn -> {
                TODO("replace @id/frag with New VPN fragment")
            }
            R.id.item_faq -> {
                TODO("replace @id/frag with FAQ fragment")
            }
            R.id.item_privacy -> {
                TODO("replace @id/frag with Privacy Policy fragment")
            }
            R.id.item_report -> {
                TODO("replace @id/frag with Report fragment")
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.nav_toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.nav_drawer)
        ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close).let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        findViewById<NavigationView>(R.id.nav_view).let {
            it.setNavigationItemSelectedListener(this)
            it.getHeaderView(0).setOnClickListener {
                replaceFragment(MainFragment)
            }
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.frag, MainFragment())
                .commit()
    }
}
