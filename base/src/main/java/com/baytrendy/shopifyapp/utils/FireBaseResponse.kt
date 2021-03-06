package com.baytrendy.shopifyapp.utils

import com.google.firebase.database.DataSnapshot

import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

import com.baytrendy.shopifyapp.utils.Status.ERROR
import com.baytrendy.shopifyapp.utils.Status.SUCCESS

class FireBaseResponse private constructor(val status: Status, @param:Nullable @field:Nullable
val data: DataSnapshot?, @param:Nullable @field:Nullable
                                           val error: Throwable?) {
    companion object {

        fun success(@NonNull data: DataSnapshot): FireBaseResponse {
            return FireBaseResponse(SUCCESS, data, null)
        }

        fun error(@NonNull error: Throwable): FireBaseResponse {
            return FireBaseResponse(ERROR, null, error)
        }
    }
}
