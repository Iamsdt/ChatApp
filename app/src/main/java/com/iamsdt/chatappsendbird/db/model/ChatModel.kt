/*
 * Developed by Shudipto Trafder
 * at  6/7/18 6:16 PM.
 */

package com.iamsdt.chatappsendbird.db.model

class ChatModel(
        val chatName:String,

        val requested:Array<Any>,
        val chats:Array<Chats>
){
    inner class Chats(val singleChats: SingleChats)
}