package com.syffer.pincreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.syffer.pincreminder.data.db.PrincipleDatabase
import com.syffer.pincreminder.data.repository.PrincipleDefaultRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    val logger = Logger.getLogger("MainActivity")

    val repository by lazy {
        val database = PrincipleDatabase.database.getInstance(application)
        PrincipleDefaultRepository(database.principleDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setupNavigation()

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            withContext(Dispatchers.IO) {
                val principles = repository.getPrinciples()

                logger.info("${principles}")
            }
        }

    }

    private fun setupNavigation() {
        val host = NavHostFragment.create(R.navigation.main)
        //supportFragmentManager.beginTransaction().replace(R.id., host).setPrimaryNavigationFragment(host).commit()
    }
}
