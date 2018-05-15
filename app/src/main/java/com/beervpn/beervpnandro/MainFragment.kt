package com.beervpn.beervpnandro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainFragment: Fragment() {
    companion object : BeerCompanion(0, "MAIN", ::MainFragment)

    enum class State {
        Connected,
        Connecting,
        Disconnected
    }

    private var state = State.Disconnected
        set(s) {
            if(field == s) return
            field = s
            when(field) {
                State.Connected -> {
                    txt.setText(R.string.connected)
                    btn.setText(R.string.disconnect)
                    prb.visibility = View.GONE
                }
                State.Connecting -> {
                    txt.setText(R.string.connecting)
                    btn.setText(android.R.string.cancel)
                    prb.visibility = View.VISIBLE
                }
                State.Disconnected -> {
                    txt.setText(R.string.disconnected)
                    btn.setText(R.string.connect)
                    prb.visibility = View.GONE
                }
            }
        }

    private lateinit var btn: Button
    private lateinit var txt: TextView
    private lateinit var prb: ProgressBar
    private val connectThreadRunnable = Runnable {
        try {
            Thread.sleep(1000)
            finishConnecting()
        } catch (e: InterruptedException) {}
    }
    private var connectThread = Thread(connectThreadRunnable)

    private fun startConnecting() {
        connectThread = Thread(connectThreadRunnable)
        connectThread.start()
        TODO("start VPN service and call finishConnecting when connected")
    }
    public fun finishConnecting() {
        (context as MainActivity).runOnUiThread {
            state = State.Connected
        }
        TODO()
    }
    private fun disconnect() {
        connectThread.interrupt()
        TODO("stop service if running")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            btn = findViewById(R.id.btn_connect)
            btn.setOnClickListener {
                state = when(state) {
                    State.Connected, State.Connecting -> {
                        disconnect()
                        State.Disconnected
                    }
                    State.Disconnected -> {
                        startConnecting()
                        State.Connecting
                    }
                }
            }

            txt = findViewById(R.id.lbl_connection)
            prb = findViewById(R.id.prb_connection)

            findViewById<Button>(R.id.btn_feedback).setOnClickListener {
                (context as MainActivity).replaceFragment(FeedbackFragment)
            }
        }
    }
}
