package com.example.passportdatageneration.room.dao

import androidx.room.*
import com.example.passportdatageneration.room.entity.Nations

@Dao
interface NationsDao {

    @Query("select*from nations")
    fun getAllNations(): List<Nations>

    @Insert
    fun addNations(nations: Nations)

    @Delete
    fun deleteNations(nations: Nations)

    @Update
    fun updateNations(nations: Nations)

    @Insert
    fun addAllNations(vararg nations: Nations)

}