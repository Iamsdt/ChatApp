package com.iamsdt.chatappsendbird.utils.ext

import android.content.Intent
import android.support.v7.app.AppCompatActivity
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