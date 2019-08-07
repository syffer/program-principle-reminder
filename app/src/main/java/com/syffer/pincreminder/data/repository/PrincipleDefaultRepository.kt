package com.syffer.pincreminder.data.repository

import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.db.PrincipleDao
import com.syffer.pincreminder.data.entities.Principle
import java.lang.RuntimeException

class PrincipleDefaultRepository(
    private val principleDao: PrincipleDao
) : PrincipleRepository {

    override suspend fun getPrinciples(): Result<List<Principle>> {
        val principles = principleDao.getPrinciples()
        return Result.Success(principles)
    }

    override suspend fun getPrinciple(id: Int): Result<Principle> {
        val principle = principleDao.getPrinciple(id)
        return when (principle) {
            is Principle -> Result.Success(principle)
            else -> Result.Error(RuntimeException("not found"))
        }
    }

    override suspend fun save(principle: Principle) {
        return principleDao.save(principle)
    }

    override suspend fun delete(principle: Principle) {
        return principleDao.delete(principle)
    }

}