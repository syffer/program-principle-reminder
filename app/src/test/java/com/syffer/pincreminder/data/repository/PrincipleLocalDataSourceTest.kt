package com.syffer.pincreminder.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.db.PrincipleDatabase
import com.syffer.pincreminder.data.entities.Principle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@SmallTest
class PrincipleDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private val context = InstrumentationRegistry.getInstrumentation().context

    private val database = Room.inMemoryDatabaseBuilder(context, PrincipleDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    private val datasource = PrincipleLocalDataSource(database.principleDao())

    @Before
    fun clearDatabase() {
        database.clearAllTables()
    }

    @Test
    fun `no principle when database is empty`() = runBlockingTest {
        val result = datasource.getPrinciples()

        Assert.assertThat(result, instanceOf(Result.Success::class.java))
        result as Result.Success
        Assert.assertEquals(result.data.size, 0)
    }

    @Test
    fun `save principle when not existing`() = runBlockingTest {
        val newPrinciple = Principle(title = "title")

        val saveResult = datasource.save(principle = newPrinciple)

        Assert.assertThat(saveResult, instanceOf(Result.Success::class.java))
        saveResult as Result.Success
        val result = datasource.getPrinciple(saveResult.data)
        result as Result.Success
        val principle = result.data
        Assert.assertEquals(principle.id, saveResult.data)
        Assert.assertEquals(principle.title, newPrinciple.title)
    }

    @Test
    fun `save principle when already existing`() = runBlockingTest {
        val existingPrinciple = Principle(title = "existing principle")
        val existingPrincipleId = database.principleDao().save(existingPrinciple).toInt()

        val result = datasource.getPrinciple(existingPrincipleId)
        result as Result.Success
        result.data.title = "existing principle modified"
        val resultForModification = datasource.save(result.data)

        Assert.assertThat(resultForModification, instanceOf(Result.Success::class.java))
        resultForModification as Result.Success
        val resultForGet = datasource.getPrinciple(resultForModification.data)
        resultForGet as Result.Success
        Assert.assertEquals(resultForGet.data.id, existingPrincipleId)
        Assert.assertEquals(resultForGet.data.id, resultForModification.data)
        Assert.assertEquals(resultForGet.data.title, "existing principle modified")
    }

    @Test
    fun `get principle when existing in database`() = runBlockingTest {
        val existingPrinciple = Principle(title = "existing principle")
        val existingPrincipleId = database.principleDao().save(existingPrinciple).toInt()

        val result = datasource.getPrinciple(existingPrincipleId)

        result as Result.Success
        val principle = result.data
        Assert.assertEquals(principle.id, existingPrincipleId)
        Assert.assertEquals(principle.title, existingPrinciple.title)
    }

    @Test
    fun `get result error when principle id not in database`() = runBlockingTest {
        val unexistingPrincipleId = -1

        val result = datasource.getPrinciple(unexistingPrincipleId)

        Assert.assertThat(result, instanceOf(Result.Error::class.java))
    }
}