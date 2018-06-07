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
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.USERID
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.USER_SP

class SpUtils(val context: Context) {

    val isFirstTime get() = sp.getBoolean(APP_RUN_FIRST_TIME, true)

    fun setAppRunFirstTime() {
        sp.edit {
            putBoolean(APP_RUN_FIRST_TIME, false)
        }
    }

    fun saveDisplayName(name: CharSequence) {
        userSp.edit {
            putString(NAME, name.toString())
        }
    }

    fun saveLogin(email: String, pass: String) {
        userSp.edit {
            putString(EMAIL, email)
            putString(KEY, pass)
        }
    }

    fun saveUserID(uid: String) {
        userSp.edit {
            putString(USERID, uid)
        }
    }

    val userID: String get() = userSp.getString(USERID, "")

    val isLogin: Boolean get() = userSp.getString(EMAIL, "").isNotEmpty()

    val getUser: Pair<String, String>
        get() =
            Pair(userSp.getString(EMAIL, ""),
                    userSp.getString(KEY, ""))

    private val sp: SharedPreferences
        get() =
            context.getSharedPreferences(APP_UTILS_SP,
                    Context.MODE_PRIVATE)

    private val userSp: SharedPreferences
        get() =
            context.getSharedPreferences(USER_SP,
                    Context.MODE_PRIVATE)

}