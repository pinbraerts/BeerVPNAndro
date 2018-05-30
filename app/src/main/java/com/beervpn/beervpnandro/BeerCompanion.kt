package com.beervpn.beervpnandro

import android.support.v4.app.Fragment

open class BeerCompanion(val num: Int,
                         val tag: String,
                         constructor: () -> Fragment) {
    val instance: Fragment by lazy(constructor)
}

