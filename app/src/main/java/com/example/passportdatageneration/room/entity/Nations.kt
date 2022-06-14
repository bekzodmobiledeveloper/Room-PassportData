package com.example.passportdatageneration.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Nations:Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "nation_name")
    var name: String? = null

    @ColumnInfo(name = "nation_lastname")
    var lastName: String? = null

    @ColumnInfo(name = "nation_fathername")
    var fatherName: String? = null
    var region: String? = null
    var country: String? = null
    var addressHouse: String? = null
    var getTimePassport: String? = null
    var passportTime: String? = null
    var gender: String? = null
    var image: String? = null


}