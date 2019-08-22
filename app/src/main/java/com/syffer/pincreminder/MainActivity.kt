package com.syffer.pincreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.db.PrincipleDatabase
import com.syffer.pincreminder.data.repository.PrincipleDefaultRepository
import com.syffer.pincreminder.data.repository.PrincipleLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    val logger = Logger.getLogger("MainActivity")

    val repository by lazy {
        val database = PrincipleDatabase.database.getInstance(application)
        val localDataSource = PrincipleLocalDataSource(database.principleDao())
        PrincipleDefaultRepository(localDataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.getPrinciples()

                logger.info("${result}")
                if (result is Result.Success && result.data.isNotEmpty()) {
                    val id = result.data[0].id!!
                    val res = repository.getPrinciple(id)
                    logger.info("and ${id} ${res}")
                }
            }
        }
    }

    private fun setupNavigation() {
        val host = NavHostFragment.create(R.navigation.main)
        //supportFragmentManager.beginTransaction().replace(R.id., host).setPrimaryNavigationFragment(host).commit()
    }
}
