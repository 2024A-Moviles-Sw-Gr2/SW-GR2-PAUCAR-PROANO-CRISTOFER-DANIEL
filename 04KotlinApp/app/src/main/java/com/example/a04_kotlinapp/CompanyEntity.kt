package com.example.a04_kotlinapp

import android.os.Parcel
import android.os.Parcelable

class CompanyEntity(
    var id: Int,
    var name: String,
    var industry: String,
    var foundedYear: Int
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(industry)
        parcel.writeInt(foundedYear)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompanyEntity> {
        override fun createFromParcel(parcel: Parcel): CompanyEntity {
            return CompanyEntity(parcel)
        }

        override fun newArray(size: Int): Array<CompanyEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $name"
    }

}