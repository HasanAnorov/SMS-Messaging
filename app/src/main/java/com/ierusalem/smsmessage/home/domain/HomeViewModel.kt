package com.ierusalem.smsmessage.home.domain

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ierusalem.employeemanagement.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.employeemanagement.ui.navigation.NavigationEventDelegate
import com.ierusalem.employeemanagement.ui.navigation.emitNavigation
import com.ierusalem.smsmessage.home.presentation.HomeScreenEvents
import com.ierusalem.smsmessage.home.presentation.HomeScreenNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun handleEvents(event: HomeScreenEvents) {
        when (event) {

            HomeScreenEvents.OnContactsClick -> {
                emitNavigation(HomeScreenNavigation.OpenContacts)
            }

            is HomeScreenEvents.OnAddClick -> {
                val newNumbers = state.value.numbers.toMutableList().apply {
                    add(event.number)
                }
                _state.update {
                    it.copy(
                        numbers = newNumbers
                    )
                }
            }

            is HomeScreenEvents.OnDeleteClick -> {
                val newNumbers = state.value.numbers.toMutableList().apply {
                    remove(event.number)
                }
                _state.update {
                    it.copy(
                        numbers = newNumbers
                    )
                }
            }
        }
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}

@Immutable
data class MainScreenState(
    val numbers: List<String> = listOf()
)