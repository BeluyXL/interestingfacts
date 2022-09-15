package com.example.interestingfactsaboutnumbers

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Fact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FactDAO
}