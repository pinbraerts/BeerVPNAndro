package com.beervpn.beervpnandro

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class FeedbackActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    lateinit var summaryText: TextView
    lateinit var buttonBuy: Button
    lateinit var drawableSad: Drawable
    lateinit var drawableBeer: Drawable

    var summary = 0
        set(value) {
            field = value * 100
            summaryText.text = field.toString()
            buttonBuy.isEnabled = field != 0
        }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if(fromUser) {
            summary += progress - seekBar.progress
            if(progress == 0) seekBar.thumb = drawableSad
            else if(seekBar.progress == 0) seekBar.thumb = drawableBeer
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) { } // do not use
    override fun onStopTrackingTouch(seekBar: SeekBar) { } // do not use

    fun getDrawableOver(id: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                getDrawable(id)
            else resources.getDrawable(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        summaryText = findViewById(R.id.text_summary)

        summary = findViewById<SeekBar>(R.id.seek_bvpn).also{
            it.setOnSeekBarChangeListener(this)
        }.progress + findViewById<SeekBar>(R.id.seek_admin).also {
            it.setOnSeekBarChangeListener(this)
        }.progress

        buttonBuy = findViewById(R.id.btn_buy)

        drawableSad = getDrawableOver(R.drawable.ic_sad)
        drawableBeer = getDrawableOver(R.drawable.ic_beer)
    }
}
