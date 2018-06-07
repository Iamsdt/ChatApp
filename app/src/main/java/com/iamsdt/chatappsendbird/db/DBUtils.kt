/*
 * Developed by Shudipto Trafder
 * at  6/7/18 6:30 PM.
 */

/*
 * Developed by Shudipto Trafder
 * at  6/7/18 5:18 PM.
 */

package com.iamsdt.chatappsendbird.db

class DBUtils{

    object GroupObj{

        val collection = "Group"

        const val Name = "name"
        const val CreatedOn = "created"
        const val Description = "des"
        const val IOCN = "icon"
        const val ADMINS = "admins"
        const val Chats = "chats"
    }

    object ChatsObj{

        val collection = "chats"

        fun getName(user1:String,user2:String) = "user1:user2"

        val model = "chat"
    }

    object UserObj{

        val COLLECTION = "chats"

        const val NAME = "name"
        const val DES = "des"
        const val PROFILE_PIC= "profilePic"
        const val GENDER = "gender"
        const val DOB = "dob"

        const val CONNECTED = "connected"
        const val CHATIDS = "chatsIds" //write chats ids
        const val GROUPIDS = "groupIds"
    }
}