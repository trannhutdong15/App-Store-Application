package com.example.app_store_application.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var gameName: String,
    var imageGame: ByteArray,
    var gameUrl: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.createByteArray()!!,
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(gameName)
        parcel.writeByteArray(imageGame)
        parcel.writeString(gameUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameEntity> {
        override fun createFromParcel(parcel: Parcel): GameEntity {
            return GameEntity(parcel)
        }

        override fun newArray(size: Int): Array<GameEntity?> {
            return arrayOfNulls(size)
        }
    }
}
