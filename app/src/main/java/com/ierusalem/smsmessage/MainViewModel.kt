package com.ierusalem.smsmessage

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ierusalem.employeemanagement.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.employeemanagement.ui.navigation.NavigationEventDelegate
import com.ierusalem.employeemanagement.ui.navigation.emitNavigation
import com.ierusalem.smsmessage.contacts.presentation.ContactItemModel
import com.ierusalem.smsmessage.home.presentation.HomeScreenEvents
import com.ierusalem.smsmessage.home.presentation.HomeScreenNavigation
import com.ierusalem.smsmessage.utils.Categories
import com.ierusalem.smsmessage.utils.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()


    fun loadCategories(categories: Categories){
        _state.update {
            it.copy(
                categories = categories
            )
        }
    }


    fun addNumberFromContacts(contacts: List<ContactItemModel>) {
        val new = mutableListOf<PhoneNumber>()
        contacts.forEach {
            new.add(
                PhoneNumber(
                    name = it.contactName,
                    number = it.phoneNumber
                )
            )
        }
        val newNumbers = state.value.numbers.toMutableList().apply {
            addAll(new)
        }
        _state.update {
            it.copy(
                numbers = newNumbers
            )
        }
    }

    fun handleEvents(event: HomeScreenEvents) {
        when (event) {

            is HomeScreenEvents.OnMessageChanged ->{
                _state.update {
                    it.copy(
                        message = event.message
                    )
                }
            }


            HomeScreenEvents.OnCategoriesClick -> {
                emitNavigation(HomeScreenNavigation.OpenCategories)
            }

            is HomeScreenEvents.OnAddCategory -> {
                emitNavigation(
                    HomeScreenNavigation.AddCategory(
                        category = Category(
                            name = event.name,
                            contacts = state.value.numbers
                        )
                    )
                )
            }

            HomeScreenEvents.OnContactsClick -> {
                emitNavigation(HomeScreenNavigation.OpenContacts)
            }

            is HomeScreenEvents.OnAddClick -> {
                val newNumbers = state.value.numbers.toMutableList().apply {
                    add(event.phoneNumber)
                }
                _state.update {
                    it.copy(
                        numbers = newNumbers
                    )
                }
            }

            is HomeScreenEvents.OnDeleteClick -> {
                val newNumbers = state.value.numbers.toMutableList().apply {
                    remove(event.phoneNumber)
                }
                _state.update {
                    it.copy(
                        numbers = newNumbers
                    )
                }
            }
        }
    }

    fun loadContacts(contacts: List<ContactItemModel>) {
        _state.update {
            it.copy(
                contacts = contacts
            )
        }
    }

    fun onPermissionChanged(isGranted: Boolean) {
        _state.update {
            it.copy(
                isReadContactsGranted = isGranted
            )
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
    val message: String = "",
    val isReadContactsGranted: Boolean = false,
    val contacts: List<ContactItemModel> = listOf(),
    val numbers: List<PhoneNumber> = listOf(),
    val categories: Categories = Categories(listOf())
)

data class PhoneNumber(
    val name: String,
    val number: String
)