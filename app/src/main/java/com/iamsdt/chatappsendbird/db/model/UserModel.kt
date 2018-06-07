/*
 * Developed by Shudipto Trafder
 * at  6/7/18 5:56 PM.
 */

package com.iamsdt.chatappsendbird.db.model

class UserModel(
        val id:String,
        val name:String,
        val des:String,
        val gender:String,
        val dob:String,
        val profilePic:String,

        val connected:Array<String>,
        val groupIds:Array<String>,
        val chatsIds:Array<String>
)
