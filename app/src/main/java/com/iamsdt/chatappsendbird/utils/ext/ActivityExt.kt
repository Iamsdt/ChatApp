package com.iamsdt.chatappsendbird.utils.ext

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass


fun AppCompatActivity.getThread(timer: Long, clazz: KClass<out AppCompatActivity>) =
        Thread({
            try {
                Thread.sleep(timer)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                toNextActivity(clazz)
                finish()
            }
        })

fun AppCompatActivity.toNextActivity(
        clazz: KClass<out AppCompatActivity>,
        extraKey: String = "",
        extra:Any ?= null){
    val intent = Intent(this,clazz.java)

    if (extraKey.isNotEmpty()){
        when(extra){
            extra is String -> intent.putExtra(extraKey,extra.toString())
            extra is Int -> intent.putExtra(extraKey, extra to Int)
            extra is Long -> intent.putExtra(extraKey,extra to Long)
        }
    }

    startActivity(intent)
}
