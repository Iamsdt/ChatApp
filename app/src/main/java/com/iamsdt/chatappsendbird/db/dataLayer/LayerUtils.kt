/*
 * Developed by Shudipto Trafder
 * at  6/7/18 6:56 PM.
 */

package com.iamsdt.chatappsendbird.db.dataLayer

import com.google.firebase.firestore.FirebaseFirestoreSettings

class LayerUtils{

    companion object {
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
    }
}