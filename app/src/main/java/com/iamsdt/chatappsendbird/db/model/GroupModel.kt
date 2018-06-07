/*
 * Developed by Shudipto Trafder
 * at  6/7/18 6:10 PM.
 */

package com.iamsdt.chatappsendbird.db.model

class GroupModel(
        val name: String,
        val des: String,
        val created: String,
        val icon: String,
        val admins: Array<String>,
        val chats: Array<Chats>
){
    inner class Chats(val groupChat: GroupChat)
}