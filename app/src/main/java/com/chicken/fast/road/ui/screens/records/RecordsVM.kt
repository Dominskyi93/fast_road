package com.chicken.fast.road.ui.screens.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.fast.road.data.entity.Round
import com.chicken.fast.road.data.repository.RoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsVM @Inject constructor(
    private val repository: RoundRepository
) : ViewModel() {
    private val _records = MutableStateFlow<List<Round>>(emptyList())
    val records: StateFlow<List<Round>> = _records

    fun getAll() {
        viewModelScope.launch {
            _records.value = repository.getMostFive()
        }
    }

}