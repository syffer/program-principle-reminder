package com.syffer.pincreminder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.syffer.pincreminder.SingletonHolder
import com.syffer.pincreminder.data.entities.DATAFIXTURES
import com.syffer.pincreminder.data.entities.Principle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Principle::class], version = 1, exportSchema = false)
abstract class PrincipleDatabase : RoomDatabase() {

    abstract fun principleDao(): PrincipleDao

    companion object {

        val database = SingletonHolder<PrincipleDatabase, Context> { context ->
            Room.databaseBuilder(context, PrincipleDatabase::class.java, "principles.db")
                .fallbackToDestructiveMigration()
                // pre-populate database
                // @see https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1
                .addCallback(object : RoomDatabase.Callback() {

                    private val scope = CoroutineScope(Dispatchers.IO)

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        scope.launch {
                            val principleDAO = get(context).principleDao().apply {
                                clear()
                                save(DATAFIXTURES)
                            }
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)

                        scope.launch {
                            val principleDAO = get(context).principleDao().apply {
                                clear()
                                save(DATAFIXTURES)
                            }
                        }
                    }
                })
                .build()
        }

        private fun get(context: Context): PrincipleDatabase {
            return database.getInstance(context)
        }
    }
}