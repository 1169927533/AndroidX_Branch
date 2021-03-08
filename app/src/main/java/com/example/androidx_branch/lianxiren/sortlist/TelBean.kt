package com.example.androidx_branch.lianxiren.sortlist

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author Yu
 * @Date 202/2/23 17:28
 * @Description TODO
 */
@Parcelize
class TelBean( var name: String, var pin: String,var select:Boolean) : Parcelable
