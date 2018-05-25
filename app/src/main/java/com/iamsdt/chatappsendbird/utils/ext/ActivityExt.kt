package com.iamsdt.chatappsendbird.utils.ext

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import androidx.core.content.edit
import com.iamsdt.chatappsendbird.utils.ConstantUtils
import kotlin.reflect.KClass


fun AppCompatActivity
        .getThread(timer: Long,clazz: KClass<out AppCompatActivity>) =
        Thread({
            try {
                Thread.sleep(timer)
            } catch (e:Exception){
                e.printStackTrace()
            } finally {
                startActivity(Intent(this, clazz.java))
                finish()
            }
        })

//sp
fun isAppRunForFirstTime(context: Context)
        = getSp(context).getBoolean(ConstantUtils.APP_RUN_FIRST_TIME, false)


fun setAppRunFirstTime(context: Context){
    getSp(context).edit {
        putBoolean(ConstantUtils.APP_RUN_FIRST_TIME,true)
    }
}

private fun getSp(context: Context) =
        context.getSharedPreferences(ConstantUtils.APP_UTILS_SP, Context.MODE_PRIVATE)
