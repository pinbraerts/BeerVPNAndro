package com.beervpn.beervpnandro

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class FeedbackFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    companion object: BeerCompanion(1, "BEER", ::FeedbackFragment)

    lateinit var summaryText: TextView
    lateinit var buttonBuy: Button

    var summary = 0
        set(value) {
            field = value
            summaryText.text = (field * 100).toString()
            buttonBuy.isEnabled = field != 0
        }

    var previousProgress1 = 0
    var previousProgress2 = 0

    fun SeekBar.getPreviousProgress(): Int {
        return when(id) {
            R.id.seek_bvpn -> previousProgress1
            R.id.seek_admin -> previousProgress2
            else -> throw IllegalArgumentException("Waits for seek_bvpn or seek_admin")
        }
    }
    fun SeekBar.setPreviousProgress() {
        when(id) {
            R.id.seek_bvpn -> previousProgress1 = progress
            R.id.seek_admin -> previousProgress2 = progress
            else -> throw IllegalArgumentException("Waits for seek_bvpn or seek_admin")
        }
    }

    fun getDrawableOver(id: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            context!!.getDrawable(id)
        else resources.getDrawable(id)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if(fromUser) {
            seekBar.getPreviousProgress().let {
                summary += progress - it
                if(progress == 0) seekBar.thumb = getDrawableOver(R.drawable.ic_sad)
                else if(it == 0) seekBar.thumb = getDrawableOver(R.drawable.ic_beer)
            }
            seekBar.setPreviousProgress()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) { } // do not use
    override fun onStopTrackingTouch(seekBar: SeekBar) { } // do not use

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false).also {
            summaryText = it.findViewById(R.id.text_summary)
            buttonBuy = it.findViewById(R.id.btn_buy)

            summary = arrayOf<SeekBar>(it.findViewById(R.id.seek_bvpn),
                    it.findViewById(R.id.seek_admin)).sumBy { sb ->
                sb.setOnSeekBarChangeListener(this)
                sb.setPreviousProgress()
                sb.progress
            }
        }
    }
}
