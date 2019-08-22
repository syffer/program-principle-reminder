package com.syffer.pincreminder.data.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.syffer.pincreminder.data.entities.Principle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.platform.commons.logging.LoggerFactory
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@RunWith(AndroidJUnit4::class)
@RunWith(RobolectricTestRunner::class)
@SmallTest
class PrincipleDaoTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private val context = InstrumentationRegistry.getInstrumentation().context

    private val database = Room.inMemoryDatabaseBuilder(context, PrincipleDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    private val principleDao = database.principleDao()

    //@AfterAll
    fun closeDatabase() {
        database.close()
    }

    //@BeforeEach
    @Before
    fun clearDatabase() {
        database.clearAllTables()
    }

    @Test
    fun `no principle when database is empty`() = runBlockingTest {
        // given
        database.clearAllTables()

        // when
        val principles = database.principleDao().getPrinciples()

        // then
        Assert.assertEquals(principles.size, 0)
    }

    @Test
    fun `save principle when not existing`() = runBlockingTest {
        val title = "principle title"
        val principle = Principle(title = title)

        val result = database.principleDao().save(principle)

        val principles = principleDao.getPrinciples()
        Assert.assertEquals(principles.size, 1)
        Assert.assertEquals(principles[0].title, title)
    }

    @Test
    fun `save principle when already existing`() = runBlockingTest {
        val title = "principle title"
        val principle = Principle(title = title)
        val principleId = principleDao.save(principle).toInt()

        val modifiedTitle = "modified title"
        val principleFromDatabase = principleDao.getPrinciple(principleId)
        principleFromDatabase?.title = modifiedTitle
        val newPrincipleId = if (principleFromDatabase != null) principleDao.save(principleFromDatabase).toInt() else -1
        val savedPrinciple = principleDao.getPrinciple(principleId)
        val principles = principleDao.getPrinciples()

        Assert.assertEquals(principles.size, 1)
        Assert.assertNotNull(savedPrinciple)
        Assert.assertEquals(principleId, newPrincipleId)
        Assert.assertEquals(savedPrinciple?.title, modifiedTitle)
        Assert.assertEquals(savedPrinciple?.id, principleId)
    }

    @Test
    fun `get principle when existing in database`() = runBlockingTest {
        val title = "principle title"
        val principle = Principle(title = title)

        val principleId = principleDao.save(principle).toInt()
        val principleFromDatabase = principleDao.getPrinciple(principleId)

        Assert.assertNotNull(principleFromDatabase)
        Assert.assertEquals(principleFromDatabase?.id, principleId)
        Assert.assertEquals(principleFromDatabase?.title, principle.title)
    }

    @Test
    fun `get null when principle id not in database`() = runBlockingTest {
        val unknownPrincipleId = -1

        val principle = principleDao.getPrinciple(unknownPrincipleId)

        Assert.assertNull(principle)
    }
}