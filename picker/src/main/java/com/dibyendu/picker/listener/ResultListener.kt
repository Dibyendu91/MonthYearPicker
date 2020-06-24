package com.dibyendu.picker.listener

import android.os.Parcel
import android.os.Parcelable

internal interface ResultListener : Parcelable {
    fun <T> onSetResult(item: T)
    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    override fun describeContents() = 0
}