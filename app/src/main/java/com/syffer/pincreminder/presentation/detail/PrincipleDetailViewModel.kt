package com.syffer.pincreminder.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.data.repository.PrincipleRepository
import com.syffer.pincreminder.presentation.Event
import kotlinx.coroutines.launch
import java.util.logging.Logger

class PrincipleDetailViewModel(
    private val principleRepository: PrincipleRepository,
    private val principleId: Int
) : ViewModel() {

    private val logger  = Logger.getLogger(this::class.java.name)

    private val _eventEditPrinciple = MutableLiveData<Event<Unit>>()
    val eventEditPrinciple: LiveData<Event<Unit>> = _eventEditPrinciple

    val title = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            val result = principleRepository.getPrinciple(principleId)

            when (result) {
                is Result.Success -> {
                    val principle = result.data
                    title.value = principle.title
                }
                else -> {
                    // @WARNING result should always be Success at this point, but should handle error anyway
                    logger.info("$result")
                }
            }
        }
    }

    fun editPrinciple() {
        _eventEditPrinciple.value = Event(Unit)
    }
}