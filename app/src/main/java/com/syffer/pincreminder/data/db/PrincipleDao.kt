package com.syffer.pincreminder.data.db

import androidx.room.*
import com.syffer.pincreminder.data.entities.Principle;

@Dao
interface PrincipleDao {

    @Query("SELECT * FROM principle order by title")
    suspend fun getPrinciples() : List<Principle>

    @Query("SELECT * FROM principle WHERE id = :id")
    suspend fun getPrinciple(id: Int) : Principle?

    @Insert
    suspend fun save(principles: List<Principle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(principle: Principle): Long

    @Delete
    suspend fun delete(principle: Principle)

    @Query("DELETE FROM principle")
    suspend fun clear()

}
