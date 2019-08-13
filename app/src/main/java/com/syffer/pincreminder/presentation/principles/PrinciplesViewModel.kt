package com.syffer.pincreminder.presentation.principles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syffer.pincreminder.data.entities.Principle
import com.syffer.pincreminder.data.repository.PrincipleRepository
import com.syffer.pincreminder.data.Result
import kotlinx.coroutines.launch

class PrinciplesViewModel(
    private val principleRepository : PrincipleRepository
) : ViewModel() {

    val principles: MutableLiveData<List<Principle>> = MutableLiveData<List<Principle>>().apply {
        emptyList<Principle>()
    }

    init {

        viewModelScope.launch {
            val result = principleRepository.getPrinciples()
            if (result is Result.Success) {
                principles.value = result.data
            }
        }

    }

    fun onPrincipleClick() {

    }
}