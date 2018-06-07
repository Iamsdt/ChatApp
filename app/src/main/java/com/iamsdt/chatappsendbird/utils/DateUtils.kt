/*
 * Developed by Shudipto Trafder
 * at  6/7/18 4:36 PM.
 */

package com.iamsdt.chatappsendbird.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils{

    companion object {
        fun getTodayDate():String{
            //look like wed, may 7
            val sp = SimpleDateFormat("EEE, MMM d",Locale.getDefault())
            sp.format(Date())
            return sp.format(Date())
        }

        fun getCurrentTime():String{
            //look like 12:30 pm
            val sp = SimpleDateFormat("h:mm a",Locale.getDefault())
            sp.format(Date())
            return sp.format(Date())
        }
    }

}