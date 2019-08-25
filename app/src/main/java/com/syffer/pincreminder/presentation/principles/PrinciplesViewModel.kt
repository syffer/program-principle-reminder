package com.syffer.pincreminder.presentation.principles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.data.repository.PrincipleRepository
import com.syffer.pincreminder.data.Result
import com.syffer.pincreminder.presentation.Event

import kotlinx.coroutines.launch

class PrinciplesViewModel(
    private val principleRepository : PrincipleRepository
) : ViewModel() {

    private val _eventAddPrinciple = MutableLiveData<Event<Unit>>()
    val eventAddPrinciple : LiveData<Event<Unit>> = _eventAddPrinciple

    val principles = MutableLiveData<List<Principle>>()

    init {
        updateData()
    }

    fun updateData() {
        viewModelScope.launch {
            val result = principleRepository.getPrinciples()
            if (result is Result.Success) {
                principles.value = result.data
            }
        }
    }

    fun addPrinciple() {
        _eventAddPrinciple.value = Event(Unit)
    }
}