package com.dibyendu.picker.listener

import android.os.Parcel
import android.os.Parcelable
import java.util.*

interface PickerListener : Parcelable {
    fun onSetResult(calendar: Calendar)
    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    override fun describeContents() = 0
}