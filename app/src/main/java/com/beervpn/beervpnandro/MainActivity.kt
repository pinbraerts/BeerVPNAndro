package com.beervpn.beervpnandro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
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
                MainActivity.State.Connected -> {
                    txt.setText(R.string.connected)
                    btn.setText(R.string.disconnect)
                    prb.visibility = View.GONE
                }
                MainActivity.State.Connecting -> {
                    txt.setText(R.string.connecting)
                    btn.setText(R.string.disconnect)
                    prb.visibility = View.VISIBLE
                }
                MainActivity.State.Disconnected -> {
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

    override fun onClick(v: View) {
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

    private fun startConnecting() {
        connectThread = Thread(connectThreadRunnable)
        connectThread.start()
        TODO("start VPN service and call finishConnecting when connected")
    }
    public fun finishConnecting() {
        runOnUiThread {
            state = State.Connected
            Toast.makeText(this, "connected!", Toast.LENGTH_SHORT).show()
        }
        TODO()
    }
    private fun disconnect() {
        runOnUiThread {
            Toast.makeText(this, "disconnected!", Toast.LENGTH_SHORT).show()
        }
        connectThread.interrupt()
        TODO("stop service if running")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_connect)
        btn.setOnClickListener(this)

        txt = findViewById(R.id.lbl_connection)
        prb = findViewById(R.id.prb_connection)
    }
}
