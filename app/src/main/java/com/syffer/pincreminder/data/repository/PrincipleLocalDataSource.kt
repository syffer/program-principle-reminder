package com.syffer.pincreminder.data.repository

import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.db.PrincipleDao
import com.syffer.pincreminder.data.entities.Principle
import java.io.IOException

class PrincipleLocalDataSource(
    private val principleDao: PrincipleDao
) : PrincipleDataSource {

    override suspend fun getPrinciples(): Result<List<Principle>> {
        try {
            return Result.Success(principleDao.getPrinciples())
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getPrinciple(id: Int): Result<Principle> {
        try {
            val principle = principleDao.getPrinciple(id)

            return when (principle) {
                is Principle -> Result.Success(principle)
                else -> Result.Error(IOException("no found, ${principle}"))
            }
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun save(principle: Principle): Int {
        return principleDao.save(principle).toInt()
    }

    override suspend fun delete(principle: Principle) {
        return principleDao.delete(principle)
    }

}
