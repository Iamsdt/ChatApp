/*
 * Developed by Shudipto Trafder
 * at  6/7/18 5:18 PM.
 */

package com.iamsdt.chatappsendbird.utils

class DBUtils{

    object Group{

        val collection = "Group"

        const val Name = "name"
        const val CreatedOn = "created"
        const val Description = "des"
        const val IOCN = "icon"
        const val ADMINS = "admins"
        const val Chats = "chats"
    }

    object Chats{

        val collection = "chats"

        fun getName(user1:String,user2:String) = "user1:user2"

        val model = "chat"
    }


    object User{

        val collection = "chats"

        const val Name = "name"
        const val Description = "des"
        const val ProfilePic= "profilePic"
        const val Gender = "gender"
        const val DOB = "dob"

        const val connected = "connected"
        const val chatsID = "chatsIds" //write chats ids
        const val groupIDS = "groupIds"
    }




}