package com.syffer.pincreminder.presentation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.data.repository.PrincipleRepository
import com.syffer.pincreminder.presentation.Event
import kotlinx.coroutines.launch
import java.util.logging.Logger

class EditPrincipleViewModel(
    private val principleRepository : PrincipleRepository,
    private val principleId : Int?
) : ViewModel() {

    val logger = Logger.getLogger(this.javaClass.name)

    private val _eventSavePrinciple = MutableLiveData<Event<Unit>>()
    val eventSavePrinciple : LiveData<Event<Unit>> = _eventSavePrinciple

    private lateinit var principle: Principle
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            principle = getPrinciple(principleId)
            title.value = principle.title
            description.value = principle.description

            logger.info("edit principle ${principle}")
        }
    }

    private suspend fun getPrinciple(principleId: Int?) : Principle {
        val UNKNOWN_PRINCIPLE_ID = -1
        val result = principleRepository.getPrinciple(principleId ?: UNKNOWN_PRINCIPLE_ID)

        return when (result) {
            is Result.Success -> result.data
            else -> Principle(title = "")
        }
    }

    fun saveModifications() {
        viewModelScope.launch {
            principle.title = title.value.orEmpty()
            principle.description = description.value.orEmpty()

            val result = principleRepository.save(principle)
            _eventSavePrinciple.value = Event(Unit)
        }
    }
}