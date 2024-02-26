package com.remees.findmyip.feature_findip.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remees.findmyip.feature_findip.core.util.Resource
import com.remees.findmyip.feature_findip.domain.GetIpInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindMyIpViewModel @Inject constructor(
    private val getIpInfo: GetIpInfo
) : ViewModel() {

    private val _state = mutableStateOf(FindMyIpState())
    val state: State<FindMyIpState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {

        viewModelScope.launch{
            getIpInfo.getIpInfo().onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            ipInfoItems = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            ipInfoItems = result.data,
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            ipInfoItems = result.data,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
            }

        }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}