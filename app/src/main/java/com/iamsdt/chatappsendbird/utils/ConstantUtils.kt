package com.iamsdt.chatappsendbird.utils

class ConstantUtils{

    //make this into object

    companion object {
        const val APP_UTILS_SP = "APP_UTILS_SP"
        const val APP_RUN_FIRST_TIME = "APP_RUN_FIRST_TIME"

        //user
        const val USER_SP = "UserSp"
        const val NAME = "name"
        const val EMAIL = "email"
        const val KEY = "key"
        const val PHONE = "phone"
        const val USERID = "userId"



        //event bus
        const val eventSignupSuccessful = "eventSignupSuccessful"
        const val eventSignupError = "eventSignupError"

        const val eventLoginSuccessful = "eventLoginSuccessful"
        const val eventLoginError= "eventLoginError"

        const val eventConfirmEmailSend = "eventConfirmEmailSend"

        //network
        const val internet = "internet"
        const val connected = "connected"
        const val noInternet = "noInternet"


    }
}