/*
 * Developed by Shudipto Trafder
 * at  6/7/18 6:21 PM.
 */

package com.iamsdt.chatappsendbird.db.dataLayer

import android.content.Context
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.iamsdt.chatappsendbird.db.DBUtils.UserObj.CHATIDS
import com.iamsdt.chatappsendbird.db.DBUtils.UserObj.COLLECTION
import com.iamsdt.chatappsendbird.db.DBUtils.UserObj.CONNECTED
import com.iamsdt.chatappsendbird.db.DBUtils.UserObj.GROUPIDS
import com.iamsdt.chatappsendbird.db.model.UserModel
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.USERID
import com.iamsdt.chatappsendbird.utils.ConstantUtils.Companion.USER_SP
import java.util.*

class UserLayer(db:FirebaseFirestore,val context: Context) {

    private var dr: DocumentReference

    init {

        val userID = getUserId()

        if (userID.isEmpty()) {
            throw Exception("User id can not null")
        }
        dr = db.collection(COLLECTION).document(userID)
    }

    fun saveUserInfo(userModel: UserModel) {
        dr.set(userModel)
    }

    fun updateUserInfo() {
        dr.get()
    }

    fun addGroup(groupIds: Array<String>) {
        val map: MutableMap<String, Any> = HashMap()
        map[GROUPIDS] = groupIds
        dr.update(map)
    }

    fun addCol(connected: Array<String>) {
        val map: MutableMap<String, Any> = HashMap()
        map[CONNECTED] = connected
        dr.update(map)
    }

    fun addChats(chatsIds: Array<String>) {
        val map: MutableMap<String, Any> = HashMap()
        map[CHATIDS] = chatsIds
        dr.update(map)
    }

    private fun getUserId(): String {
        return context.getSharedPreferences(USER_SP, Context.MODE_PRIVATE)
                .getString(USERID, "")
    }


    /*
    to get offline or online status
    You can also add server timestamps to specific fields in your documents,
     to track when an update was received by the server:
     DocumentReference docRef = db.collection("objects").document("some-id");

// Update the timestamp field with the value from the server
Map<String,Object> updates = new HashMap<>();
updates.put("timestamp", FieldValue.serverTimestamp());

docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
     */

}