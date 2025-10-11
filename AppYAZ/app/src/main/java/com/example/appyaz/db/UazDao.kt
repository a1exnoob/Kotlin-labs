package com.example.appyaz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UazDao {
    @Query("SELECT * FROM uaz_models ORDER BY id ASC")
    fun observeAll(): Flow<List<UazEntity>>

    @Query("SELECT * FROM uaz_models ORDER BY id ASC")
    suspend fun getAll(): List<UazEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: UazEntity): Long

    @Update
    suspend fun update(entity: UazEntity)

    @Query("DELETE FROM uaz_models WHERE id = :id")
    suspend fun deleteById(id: Int)
}


