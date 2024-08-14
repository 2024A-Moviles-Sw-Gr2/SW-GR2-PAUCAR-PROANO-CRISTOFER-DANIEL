package com.example.a04_kotlinapp

import android.os.Parcel
import android.os.Parcelable

class DepartmentEntity(
    var id: Int,
    var name: String,
    var description: String,
    var company_id: Int
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
        parcel.writeString(description)
        parcel.writeInt(company_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DepartmentEntity> {
        override fun createFromParcel(parcel: Parcel): DepartmentEntity {
            return DepartmentEntity(parcel)
        }

        override fun newArray(size: Int): Array<DepartmentEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $name"
    }

}