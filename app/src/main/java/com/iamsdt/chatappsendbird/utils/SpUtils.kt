/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:48 PM.
 */

package com.iamsdt.chatappsendbird.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpUtils(val context: Context){

    fun isAppRunForFirstTime()
            = sp.getBoolean(ConstantUtils.APP_RUN_FIRST_TIME, false)


    fun setAppRunFirstTime(){
        sp.edit {
            putBoolean(ConstantUtils.APP_RUN_FIRST_TIME,true)
        }
    }

    private val sp:SharedPreferences =
            context.getSharedPreferences(ConstantUtils.APP_UTILS_SP,
                    Context.MODE_PRIVATE)

}