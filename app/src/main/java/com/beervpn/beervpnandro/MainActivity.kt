package com.beervpn.beervpnandro

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.CompoundButton

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout

    fun replaceFragment(comp: BeerCompanion): Boolean {
        drawerLayout.closeDrawers()
        val num = (supportFragmentManager.fragments[0] as BeerFragment).getNum()
        if(num == comp.num) return true
        supportFragmentManager.beginTransaction().apply {
            if(comp.num > num)
                setCustomAnimations(
                    R.anim.enter_top,
                    R.anim.exit_bottom
                )
            else
                setCustomAnimations(
                    R.anim.enter_bottom,
                    R.anim.exit_top
                )
        }
                .replace(R.id.frag, comp.instance, comp.tag)
                .commit()
        return false
    }

    override fun onBackPressed() {
        if(replaceFragment(MainFragment)) super.onBackPressed()
    }

    fun toggleSwitch(v: CompoundButton, checked: Boolean) {
        TODO("toggle ads")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_feedback ->
                replaceFragment(FeedbackFragment)
            R.id.item_settings -> {
                TODO("replaceFragment(SettingsFragment)")
            }
            R.id.item_ads ->
                item.getSwitch().toggle()
            R.id.item_about -> {
                TODO("replaceFragment(AboutFragment)")
            }
            R.id.item_ownvpn -> {
                TODO("replaceFragment(NewVPNFragment)")
            }
            R.id.item_faq -> {
                TODO("replaceFragment(FAQFragment)")
            }
            R.id.item_privacy -> {
                TODO("replaceFragment(PrivacyInfoFragment)")
            }
            R.id.item_report -> {
                TODO("replaceFragment(ReportFragment)")
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

    fun MenuItem.getSwitch(): SwitchCompat {
        return (actionView as ViewGroup).getChildAt(0) as SwitchCompat
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
            it.menu.findItem(R.id.item_ads).getSwitch()
                .setOnCheckedChangeListener(::toggleSwitch)
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.frag, MainFragment())
                .commit()
    }
}
