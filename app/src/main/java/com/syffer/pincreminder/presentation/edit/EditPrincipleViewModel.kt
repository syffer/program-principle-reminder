package com.syffer.pincreminder.presentation.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.data.repository.PrincipleRepository
import kotlinx.coroutines.launch
import java.util.logging.Logger

class EditPrincipleViewModel(
    private val principleRepository : PrincipleRepository,
    private val principleId : Int?
) : ViewModel() {

    val logger = Logger.getLogger(this.javaClass.name)

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    init {
        title.observeForever {value ->
            logger.info("new title value is ${value}")
        }

        viewModelScope.launch {
            val principle = getPrinciple(principleId)
            title.value = principle.title
            description.value = principle.description

            logger.info("edit principle ${principle}")
        }
    }

    private suspend fun getPrinciple(principleId: Int?) : Principle {
        val UNKNOWN_PRINCIPLE_ID = -1
        val result = principleRepository.getPrinciple(principleId ?: UNKNOWN_PRINCIPLE_ID)

        logger.info("for ${principleId} got ${result}")

        return when (result) {
            is Result.Success -> result.data
            else -> Principle(title = "")
        }
    }

}