package com.syffer.pincreminder.data.repository

import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.entities.Principle

interface PrincipleRepository {

    suspend fun getPrinciples(): Result<List<Principle>>

    suspend fun getPrinciple(id: Int): Result<Principle>

    suspend fun save(principle: Principle): Int

    suspend fun delete(principle: Principle)

}