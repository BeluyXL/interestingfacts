package com.example.interestingfactsaboutnumbers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FactDAO {
    @Query("SELECT * FROM fact")
    fun getAll(): List<Fact>

    @Insert
    fun insert(fact: Fact)

    @Delete
    fun delete(fact: Fact)
}