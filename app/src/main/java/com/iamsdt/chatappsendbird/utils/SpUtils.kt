/*
 * Developed by Shudipto Trafder
 * at  5/26/18 4:48 PM.
 */

package com.iamsdt.chatappsendbird.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.APP_RUN_FIRST_TIME
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.APP_UTILS_SP
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.EMAIL
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.KEY
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.NAME
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.USER_SP

class SpUtils(val context: Context){

    fun isAppRunForFirstTime()
            = sp.getBoolean(APP_RUN_FIRST_TIME, false)


    fun setAppRunFirstTime(){
        sp.edit {
            putBoolean(APP_RUN_FIRST_TIME,true)
        }
    }

    fun saveDisplayName(name:CharSequence){
        userSp.edit {
            putString(NAME,name.toString())
        }
    }

    fun getName() = userSp.getString(NAME,"")

    fun saveLogin(email: String, pass: String){
        userSp.edit {
            putString(EMAIL,email)
            putString(KEY,pass)
        }
    }


    fun checkLogin():Boolean = userSp.getString(EMAIL,"").isNotEmpty()

    fun getUser():Pair<String,String>{
        return Pair(userSp.getString(EMAIL,""),
                userSp.getString(KEY,""))
    }

    private val sp:SharedPreferences =
            context.getSharedPreferences(APP_UTILS_SP,
                    Context.MODE_PRIVATE)

    private val userSp:SharedPreferences =
            context.getSharedPreferences(USER_SP,
                    Context.MODE_PRIVATE)

}