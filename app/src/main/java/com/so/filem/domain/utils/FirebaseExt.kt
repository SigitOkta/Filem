package com.so.filem.domain.utils

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun DatabaseReference.setValueAppendId(mapData: (id: String) -> Any): Boolean {
    return suspendCancellableCoroutine { cont ->
        setValue(mapData(key.toString()))
            .addOnCompleteListener {
                cont.resume(true, onCancellation = null)

            }
            .addOnCanceledListener {
                cont.resume(false, onCancellation = null)

            }
            .addOnFailureListener {
                cont.resumeWithException(it)
            }
    }
}