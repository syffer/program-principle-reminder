package com.syffer.pincreminder.data.repository

import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.entities.Principle
import java.lang.RuntimeException

class PrincipleDefaultRepository(
    internal val local: PrincipleLocalDataSource
) : PrincipleRepository {

    override suspend fun getPrinciples(): Result<List<Principle>> {
        return local.getPrinciples()
    }

    override suspend fun getPrinciple(id: Int): Result<Principle> {
        val principle = local.getPrinciple(id)
        return principle
    }

    override suspend fun save(principle: Principle): Result<Int> {
        return local.save(principle)
    }

    override suspend fun delete(principle: Principle) {
        return local.delete(principle)
    }

}